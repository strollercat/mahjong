package com.nbcb.weixinapi.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinReplyAccessToken extends WeixinReplyCommon {
	private String access_token;
	private int expires_in;

	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	
}
