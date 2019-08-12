package com.nbcb.web.service.impl;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.MoneyStrategyService;
import com.nbcb.web.service.OnlineUserRegistry;
import com.nbcb.web.service.WeixinLoginService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.WeixinApiOAuth2;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyOAuth2Token;
import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinSendOAuth2Code;
import com.nbcb.weixinapi.entity.WeixinSendUser;
import com.nbcb.weixinapi.entity.WeixinUser;
import com.nbcb.weixinapi.exception.WeixinException;

public class WeixinLoginServiceImpl implements WeixinLoginService {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinLoginServiceImpl.class);

	protected String appid;

	private WeixinCachedApiBase weixinCachedApiBase;

	private WeixinApiOAuth2 weixinApiOAuth2;

	private WeixinUserDao weixinUserDao;

	protected OnlineUserRegistry onlineUserRegistry;

	protected MoneyStrategyService moneyStrategyService;

	private GameUserDao gameUserDao;

	protected WeixinUserService weixinUserService;

	public void setWeixinUserService(WeixinUserService weixinUserService) {
		this.weixinUserService = weixinUserService;
	}

	public void setMoneyStrategyService(
			MoneyStrategyService moneyStrategyService) {
		this.moneyStrategyService = moneyStrategyService;
	}

	public void setGameUserDao(GameUserDao gameUserDao) {
		this.gameUserDao = gameUserDao;
	}

	public void setOnlineUserRegistry(OnlineUserRegistry onlineUserRegistry) {
		this.onlineUserRegistry = onlineUserRegistry;
	}

	public void setWeixinUserDao(WeixinUserDao weixinUserDao) {
		this.weixinUserDao = weixinUserDao;
	}

	public void setWeixinApiOAuth2(WeixinApiOAuth2 weixinApiOAuth2) {
		this.weixinApiOAuth2 = weixinApiOAuth2;
	}

	public void setWeixinCachedApiBase(WeixinCachedApiBase weixinCachedApiBase) {
		this.weixinCachedApiBase = weixinCachedApiBase;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	protected void checkWeixinUser(WeixinReplyUser wru) {
		if (null != weixinUserDao.selectUserByOpenid(wru)) {
			logger.info("### there is record in database wru " + wru);
			weixinUserDao.updateUserByOpenid(wru);
		} else {
			logger.info("### insert record into database wru " + wru);
			weixinUserDao.insertUser(wru);
		}

	}

	protected void checkGameUser(String openid) {
		GameUser gu = this.gameUserDao.selectGameUserByAccount(openid);
		if (gu != null) {
			return;
		}
		gu = new GameUser();
		gu.setLose(0);
		gu.setMoney(moneyStrategyService.newUserMoney());
		gu.setOpenid(openid);
		gu.setPing(0);
		gu.setRec_money(0);
		gu.setRec_room(0);
		gu.setRecommend(false);
		gu.setScore(0);
		gu.setWin(0);
		gameUserDao.insertGameUser(gu);
	}

	@Override
	public WeixinUser login(String code, HttpSession hs) {
		// TODO Auto-generated method stub
		WeixinUser wu = (WeixinUser) hs
				.getAttribute(WeixinLoginService.WEIXINUSERKEY);
		if (wu != null && !StringUtils.isEmpty(wu.getOpenid())) {
			return wu;
		}
		if (StringUtils.isEmpty(code)) {
			throw new RuntimeException("### code is null");
		}

		WeixinReplyUser wru = this.getUserByCode(code);
		wru.setAppid(appid);
		logger.info("### get current user is wru [" + wru + " ] ");

		this.checkWeixinUser(wru);
		this.checkGameUser(wru.getOpenid());

		wu = new WeixinUser();
		wu.setOpenid(wru.getOpenid());
		wu.setAppid(wru.getAppid());
		wu.setHeadUrl(wru.getHeadimgurl());
		wu.setName(wru.getNickname());
		logger.info("### 成功获取到Weixinuser " + wu);
		hs.setAttribute(WeixinLoginService.WEIXINUSERKEY, wu);
		
		
		weixinUserService.putWeixinUser(wru);

		return wu;

	}

	protected WeixinReplyUser getUserByCode(String code) {
		WeixinSendOAuth2Code weixinSendOAuth2Code = new WeixinSendOAuth2Code();
		weixinSendOAuth2Code.setAppid(appid);
		weixinSendOAuth2Code.setSecret(weixinCachedApiBase
				.getWeixinAccountByAppid(appid).getSecret());
		weixinSendOAuth2Code.setCode(code);
		WeixinReplyUser weixinReplyUser = null;

		try {
			WeixinReplyOAuth2Token weixinReplyOAuth2Token = weixinReplyOAuth2Token = weixinApiOAuth2
					.getOAuth2TokenByCode(weixinSendOAuth2Code);
			WeixinSendUser weixinSendUser = new WeixinSendUser();
			weixinSendUser.setAccessToken(weixinReplyOAuth2Token
					.getAccess_token());
			weixinSendUser.setOpenId(weixinReplyOAuth2Token.getOpenid());
			weixinSendUser.setLang(WeixinSendUser.ZH_LANG);
			weixinReplyUser = weixinApiOAuth2.getOAuth2User(weixinSendUser);
			return weixinReplyUser;
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			logger.error("### OAUTH2 接口调用失败!!", e);
			throw new RuntimeException("### ", e);
		}

	}

	@Override
	public WeixinUser isLogin(HttpSession hs) {
		// TODO Auto-generated method stub
		if(hs == null){
			return null;
		}
		WeixinUser wu = (WeixinUser) hs
				.getAttribute(WeixinLoginService.WEIXINUSERKEY);
		if (wu == null || StringUtils.isEmpty(wu.getOpenid())) {
			return null;
		}
		return wu;
	}

}
