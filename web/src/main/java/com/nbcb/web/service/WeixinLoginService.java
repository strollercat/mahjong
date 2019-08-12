package com.nbcb.web.service;

import javax.servlet.http.HttpSession;

import com.nbcb.weixinapi.entity.WeixinUser;

public interface WeixinLoginService {
	public static final String WEIXINUSERKEY = "__WEIXINUSERKEY";

	public WeixinUser login(String code, HttpSession hs);

	public WeixinUser isLogin(HttpSession hs);
	

}
