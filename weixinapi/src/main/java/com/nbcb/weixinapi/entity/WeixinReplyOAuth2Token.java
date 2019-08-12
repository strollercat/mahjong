package com.nbcb.weixinapi.entity;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinReplyOAuth2Token extends WeixinReplyCommon {
	String access_token;
	String expires_in;
	String refresh_token;
	String openid;
	String scope;
	String unionid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String toString() {
		return "WeixinReplyOAuth2Token:" + "[access_token]" + access_token
				+ "[expires_in]" + expires_in + "[refresh_token]"
				+ refresh_token + "[openid]" + openid + "[scope]" + scope
				+ "[unionid]" + unionid;
	}
}
