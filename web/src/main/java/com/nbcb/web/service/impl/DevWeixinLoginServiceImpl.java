package com.nbcb.web.service.impl;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinUser;

public class DevWeixinLoginServiceImpl extends WeixinLoginServiceImpl {
	private static final Logger logger = LoggerFactory
			.getLogger(DevWeixinLoginServiceImpl.class);

	@Override
	public WeixinUser login(String code, HttpSession hs) {
		// TODO Auto-generated method stub
		logger.info("### " + code + " start to login!!!");
		WeixinReplyUser wru = this.getUserByCode(code);
		wru.setAppid(appid);

		this.checkWeixinUser(wru);
		this.checkGameUser(wru.getOpenid());

		WeixinUser wu = new WeixinUser();
		wu.setOpenid(wru.getOpenid());
		wu.setAppid(wru.getAppid());
		wu.setHeadUrl(wru.getHeadimgurl());
		wu.setName(wru.getNickname());
		
		weixinUserService.putWeixinUser(wru);
		return wu;

	}

	@Override
	protected WeixinReplyUser getUserByCode(String code) {
		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setAppid(appid);
		wru.setOpenid(code);
		wru.setNickname(code + "名字");
		wru.setHeadimgurl("http://wx.qlogo.cn/mmopen/ajNVdqHZLLCVquUSK6R0JrKIy9KX6ckuYRVWnkcL3iciaUEVZhrwXicxaUIic15yvleO1rcLtnvlZ2L0X1uoBXN0lw/0");
		return wru;
	}

}
