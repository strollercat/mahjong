package com.nbcb.weixinapi.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import com.nbcb.weixinapi.WeixinApiBase;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.dao.WeixinAccountDao;
import com.nbcb.weixinapi.entity.WeixinAccount;
import com.nbcb.weixinapi.entity.WeixinReplyAccessToken;
import com.nbcb.weixinapi.entity.WeixinReplyTicket;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinCachedApiBaseImpl implements WeixinCachedApiBase {

	private static final Logger logger = LoggerFactory.getLogger(WeixinCachedApiBaseImpl.class);

	private WeixinAccountDao weixinAccountDao;

	private TransactionTemplate transactionTemplate;

	private WeixinApiBase weixinApiBase;

	private List<WeixinAccount> weixinAccounts = null;

	private Map<String, WeixinAccount> appidWeixinAccounts = new HashMap();

	private Map<String, WeixinAccount> nameWeixinAccounts = new HashMap();

	private Map<String, WeixinAccount> accountWeixinAccounts = new HashMap();

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		// TODO Auto-generated method stub
		this.transactionTemplate = transactionTemplate;
	}

	public void setWeixinAccountDao(WeixinAccountDao weixinAccountDao) {
		this.weixinAccountDao = weixinAccountDao;
	}

	public void setWeixinApiBase(WeixinApiBase weixinApiBase) {
		this.weixinApiBase = weixinApiBase;
	}

	public void start() {
		weixinAccounts = weixinAccountDao.findAllWeixinAccount();
		if (weixinAccounts == null) {
			return;
		}
		for (WeixinAccount wa : weixinAccounts) {
			String account = wa.getAccount();
			String appid = wa.getAppid();
			String name = wa.getName();
			appidWeixinAccounts.put(appid, wa);
			nameWeixinAccounts.put(name, wa);
			accountWeixinAccounts.put(account, wa);
		}
	}

	/**
	 * 从内存获取accessToken 如果失效，再从数据库获取accessToken,如果再失效，从腾讯获取accessToken 做了一个二级缓存
	 * 这样大部分时间都是从内存取token,提高性能 集群环境下有待进一步验证其可靠性
	 * 
	 * @author zhengbinhui
	 */
	@Override
	public String getCachedAccessToken(final String appid) throws WeixinException {
		// TODO Auto-generated method stub
		final WeixinAccount weixinAccount = this.getWeixinAccountByAppid(appid);
		if (weixinAccount == null) {
			return null;
		}
		String accessToken = weixinAccount.getAccess_token();
		Date tokenExpireTime = weixinAccount.getToken_expire_time();
		if (!StringUtils.hasText(accessToken) || tokenExpireTime == null
				|| tokenExpireTime.getTime() < System.currentTimeMillis()) {
			logger.debug("### 内存中的accessToken失效");
			// 这里需要加事务管理，保证函数结束后一定commit
			return (String) transactionTemplate.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus arg0) {
					try {
						WeixinAccount dbWeixinAccount = WeixinCachedApiBaseImpl.this.weixinAccountDao
								.selectWeixinAccountByAppidForUpdate(appid);
						logger.debug("### 从数据库中检索出的weixinAccount appid " + dbWeixinAccount + " " + appid);
						String dbAccessToken = dbWeixinAccount.getAccess_token();
						Date dbTokenExpireTime = dbWeixinAccount.getToken_expire_time();
						if (!StringUtils.hasText(dbAccessToken) || dbTokenExpireTime == null
								|| dbTokenExpireTime.getTime() < System.currentTimeMillis()) {
							logger.debug("### 数据库中accessToken失效");
							return getAndUpdateAccessToken(weixinAccount);
						}
						logger.debug("### 数据库中的accessToken已经被另外的一台机器更新,更新内存accessToken");
						weixinAccount.setAccess_token(dbAccessToken);
						weixinAccount.setToken_expire_time(dbTokenExpireTime);
						return dbAccessToken;
					} catch (Exception e) {
						arg0.setRollbackOnly();
						return null;
					}
				}
			});
		}
		logger.debug("### 内存中的accessToken未失效，直接返回");
		return weixinAccount.getAccess_token();
	}

	private String getAndUpdateAccessToken(WeixinAccount weixinAccount) throws WeixinException {

		WeixinReplyAccessToken wra = weixinApiBase.getAccessToken(weixinAccount.getAppid(), weixinAccount.getSecret());
		int expire = wra.getExpires_in();
//		long expireTime = System.currentTimeMillis() + (expire - 300) * 1000;
		//试试看
		long expireTime = System.currentTimeMillis() + expire * 1000;
		WeixinAccount wa = new WeixinAccount();
		Date dateExpireTime = new Date(expireTime);
		wa.setAppid(weixinAccount.getAppid());
		wa.setAccess_token(wra.getAccess_token());
		wa.setToken_expire_time(dateExpireTime);
		// 更新数据库
		weixinAccountDao.updateAccessTokenByAppid(wa);
		// 更新内存
		weixinAccount.setAccess_token(wra.getAccess_token());
		weixinAccount.setToken_expire_time(dateExpireTime);
		return wra.getAccess_token();
	}

	/**
	 * see getCachedToken 与getCachedToken思想一致
	 * 
	 * @author zhengbinhui
	 */
	@Override
	public String getCachedTicket(final String appid) throws WeixinException {
		// TODO Auto-generated method stub
		final WeixinAccount weixinAccount = this.getWeixinAccountByAppid(appid);
		if (weixinAccount == null) {
			return null;
		}
		String ticket = weixinAccount.getTicket();
		Date ticketExpireTime = weixinAccount.getTicket_expire_time();
		if (!StringUtils.hasText(ticket) || ticketExpireTime == null
				|| ticketExpireTime.getTime() < System.currentTimeMillis()) {
			logger.debug("### 内存中ticket失效，从数据库读取");
			return (String) transactionTemplate.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus arg0) {
					try {
						WeixinAccount dbWeixinAccount = WeixinCachedApiBaseImpl.this.weixinAccountDao
								.selectWeixinAccountByAppidForUpdate(appid);
						logger.debug("### 从数据库中检索出的weixinAccount appid " + dbWeixinAccount + " " + appid);
						String dbTicket = dbWeixinAccount.getTicket();
						Date dbTicketExpireTime = dbWeixinAccount.getTicket_expire_time();
						if (!StringUtils.hasText(dbTicket) || dbTicketExpireTime == null
								|| dbTicketExpireTime.getTime() < System.currentTimeMillis()) {
							logger.debug("### 数据库中ticket失效，开始从腾讯获取ticket");
							return getAndUpdateTicket(weixinAccount);
						}
						logger.debug("### 数据库中ticket已经被另外一个实例更新，返回数据库中ticket");
						weixinAccount.setTicket(dbTicket);
						weixinAccount.setTicket_expire_time(dbTicketExpireTime);
						return dbTicket;
					} catch (Exception e) {
						arg0.setRollbackOnly();
						return null;
					}
				}
			});
		}
		logger.debug("### 内存中ticket未失效，直接返回");
		return weixinAccount.getTicket();
	}

	private String getAndUpdateTicket(WeixinAccount weixinAccount) throws WeixinException {
		String accessToken = this.getCachedAccessToken(weixinAccount.getAppid());
		WeixinReplyTicket wrt = weixinApiBase.getTicket(accessToken);
		String ticket = wrt.getTicket();
		int ticketExpireTime = wrt.getExpires_in();
		WeixinAccount wa = new WeixinAccount();
		Date dateTicketExpireTime = new Date(System.currentTimeMillis() + (ticketExpireTime - 600) * 1000);
		wa.setAppid(weixinAccount.getAppid());
		wa.setTicket(ticket);
		wa.setTicket_expire_time(dateTicketExpireTime);
		weixinAccountDao.updateTicketByAppid(wa);
		weixinAccount.setTicket(ticket);
		weixinAccount.setTicket_expire_time(dateTicketExpireTime);
		return ticket;
	}

	@Override
	public WeixinAccount getWeixinAccountByAppid(String appid) {
		// TODO Auto-generated method stub
		return this.appidWeixinAccounts.get(appid);
	}

	@Override
	public List<WeixinAccount> getWeixinAccounts() {
		// TODO Auto-generated method stub
		return this.weixinAccounts;
	}

	@Override
	public WeixinAccount getWeixinAccountByName(String name) {
		// TODO Auto-generated method stub
		return this.nameWeixinAccounts.get(name);
	}

	@Override
	public WeixinAccount getWeixinAccountByAccount(String account) {
		// TODO Auto-generated method stub
		return this.accountWeixinAccounts.get(account);
	}

}
