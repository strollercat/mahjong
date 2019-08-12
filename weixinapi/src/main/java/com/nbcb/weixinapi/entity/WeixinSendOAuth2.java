package com.nbcb.weixinapi.entity;


/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendOAuth2 {
	private String appid;
	private String redirect_uri;
	private String scope;

	private String response_type = "code";
	
	public static String SNSAPI_BASE="snsapi_base";
	public static String SNSAPI_USERINFO="snsapi_userinfo";

	public String getResponse_type() {
		return response_type;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
