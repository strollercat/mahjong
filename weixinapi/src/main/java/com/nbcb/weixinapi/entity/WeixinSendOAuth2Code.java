package com.nbcb.weixinapi.entity;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendOAuth2Code {
	private String appid;
	private String secret;
	private String code;
	private String grant_type = "authorization_code";

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGrant_type() {
		return grant_type;
	}



}
