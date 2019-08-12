package com.nbcb.weixinapi.entity;


/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendOAuth2Refresh {
	String appid;
	String grant_type = "refresh_token";
	String refresh_token;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
