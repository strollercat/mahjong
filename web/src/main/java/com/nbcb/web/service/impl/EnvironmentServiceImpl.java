package com.nbcb.web.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.WeixinLoginService;
import com.nbcb.weixinapi.WeixinApiOAuth2;
import com.nbcb.weixinapi.entity.WeixinSendOAuth2;
import com.nbcb.weixinapi.entity.WeixinUser;

public class EnvironmentServiceImpl implements EnvironmentService,
		EnvironmentAware {

	private String appid;

	private String domain;

	private String appcontext;

	private WeixinApiOAuth2 weixinApiOAuth2;

	private Environment environment;

	private WeixinLoginService weixinLoginService;

	public void setWeixinLoginService(WeixinLoginService weixinLoginService) {
		this.weixinLoginService = weixinLoginService;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setAppcontext(String appcontext) {
		this.appcontext = appcontext;
	}

	public void setWeixinApiOAuth2(WeixinApiOAuth2 weixinApiOAuth2) {
		this.weixinApiOAuth2 = weixinApiOAuth2;
	}

	@Override
	public String getCodedUrl(String path) {
		// TODO Auto-generated method stub
		String url = this.domain + "/" + this.appcontext + "/" + path;
		WeixinSendOAuth2 weixinSendOAuth2 = new WeixinSendOAuth2();
		weixinSendOAuth2.setAppid(appid);
		weixinSendOAuth2.setScope(weixinSendOAuth2.SNSAPI_USERINFO);
		weixinSendOAuth2.setRedirect_uri(url);
		return weixinApiOAuth2.getOAuth2DoorUrl(weixinSendOAuth2);
	}

	@Override
	public String getAccount(HttpServletRequest request) {
		if (this.environment.acceptsProfiles("prod")
				|| this.environment.acceptsProfiles("test")) {
			if (request.getSession(false) == null) {
				return null;
			}
			WeixinUser wu = weixinLoginService.isLogin(request.getSession());
			if (wu == null || StringUtils.isEmpty(wu.getOpenid())) {
				return null;
			}
			return wu.getOpenid();
		} else {
			return request.getParameter("code");
		}
	}

	@Override
	public String getFullUrl(String path) {
		String url = this.domain + "/" + this.appcontext + "/" + path;
		return url;
	}

	@Override
	public String getAppid() {
		return this.appid;
	}

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		this.environment = environment;
	}

	@Override
	public String getEnvironment() {
		// TODO Auto-generated method stub
		if (this.environment.acceptsProfiles("prod")) {
			return "prod";
		} else if (this.environment.acceptsProfiles("test")) {
			return "test";
		} else {
			return "dev";
		}

	}
}
