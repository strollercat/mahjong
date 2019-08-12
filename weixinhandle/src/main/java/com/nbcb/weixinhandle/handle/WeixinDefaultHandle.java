package com.nbcb.weixinhandle.handle;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinSendUser;
import com.nbcb.weixinhandle.entity.Context;

public class WeixinDefaultHandle extends WeixinSimplifyApiHandle {
	private static final Logger logger = LoggerFactory
			.getLogger(WeixinDefaultHandle.class);

	private WeixinUserDao weixinUserDao;

	public void setWeixinUserDao(WeixinUserDao weixinUserDao) {
		this.weixinUserDao = weixinUserDao;
	}

	public WeixinUserDao getWeixinUserDao() {
		return weixinUserDao;
	}

	private void eventUnsubscribeHandleInner(Context context) {
		logger.info("### WeixinDefaultHandle eventUnSubscribeHandleInner");
		String openid = context.getString("FromUserName");
		String appid = this.getWeixinAccount().getAppid();
		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setAppid(appid);
		wru.setOpenid(openid);
		WeixinReplyUser userFromDb = this.getWeixinUserDao()
				.selectUserByOpenid(wru);
		if (userFromDb == null) {
			logger.info("### there is no weixinUser in db ,so do not process it");
			return;
		}
		logger.info("### there is  weixinUser in db ,so update it,openid is "
				+ userFromDb.getOpenid());
		wru.setSubscribe("0");
		wru.setSubscribe_time1(new Date());
		this.getWeixinUserDao().updateUserSubscribeByOpenid(wru);

	}

	private void eventSubscribeHandleInner(Context context) {
		logger.info("### WeixinDefaultHandle eventSubscribeHandleInner");
		try {
			WeixinSendUser wsu = new WeixinSendUser();
			String appid = this.getWeixinAccount().getAppid();
			String at = this.getWeixinCachedApiBase().getCachedAccessToken(
					appid);
			String openid = context.getString("FromUserName");
			wsu.setAccessToken(at);
			wsu.setLang(WeixinSendUser.ZH_LANG);
			wsu.setOpenId(openid);
			WeixinReplyUser weixinReplyUser = this.getWeixinApiUser()
					.getUserById(wsu);
			logger.info("### userRet" + weixinReplyUser);
			weixinReplyUser.setAppid(appid);
			WeixinReplyUser userFromDb = this.getWeixinUserDao()
					.selectUserByOpenid(weixinReplyUser);
			if (userFromDb == null) {
				logger.info("### there is no weixinUser in db ,so insert it");
				this.getWeixinUserDao().insertUser(weixinReplyUser);
				return;
			}
			logger.info("### there is  weixinUser in db ,so update it,openid is "
					+ userFromDb.getOpenid());
			this.getWeixinUserDao().updateUserByOpenid(weixinReplyUser);
			return;
		} catch (Exception e) {
			logger.error("### error ", e);
		}
	}

	@Override
	protected String eventSubscribeHandle(Context context) {
		// TODO Auto-generated method stub
		logger.info("### WeixinDefaultHandle eventSubscribeHandle");
		eventSubscribeHandleInner(context);
		return "";
	}

	@Override
	protected String eventSubscribeScanHandle(Context context) {
		// TODO Auto-generated method stub
		logger.info("### WeixinDefaultHandle eventSubscribeScanHandle");
		String eventKey = context.getString("EventKey");
		String ticket = context.getString("Ticket");
		eventSubscribeHandleInner(context);
		return "";
	}

	@Override
	protected String eventUnsubscribeHandle(Context context) {
		// TODO Auto-generated method stub
		logger.info("### WeixinDefaultHandle eventUnsubscribeHandle  ");
		this.eventUnsubscribeHandleInner(context);
		return "";
	}

	@Override
	protected String eventUnsubscribeScanHandle(Context context) {
		// TODO Auto-generated method stub
		logger.info("### WeixinDefaultHandle eventUnsubscribeHandle  ");
		String eventKey = context.getString("EventKey");
		String ticket = context.getString("Ticket");
		logger.debug("### eventUnsubscribeScanHandle eventKey ticket "
				+ eventKey + " " + ticket);
		this.eventUnsubscribeHandleInner(context);
		return "";
	}
}
