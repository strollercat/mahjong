package com.nbcb.web.service;

import javax.servlet.http.HttpServletRequest;

import com.nbcb.weixinapi.entity.WeixinReplyUser;

public interface WeixinUserService {
	
	public WeixinReplyUser getWeixinUserByOpenid(String openid);
	
	public void putWeixinUser(WeixinReplyUser weixinReplyUser);
	
	public String getOriginIp(HttpServletRequest request);
}
