package com.nbcb.weixinapi.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendPushText {

	public String getAccessToken() {
		return accessToken;
	}

	private String accessToken;

	private String touser;

	private String msgtype = "text";

	private Map text = new HashMap();

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTouser() {
		return touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public Map getText() {
		return text;
	}

	public void setContent(String content) {
		text.put("content", content);
	}

}
