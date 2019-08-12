package com.nbcb.weixinapi.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;




/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendPushVoice {
	private String accessToken;
	private String touser;
	private String msgtype = "voice";
	private Map voice = new HashMap();
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	
	public Map getVoice() {
		return voice;
	}
	public void setMediaId(String mediaId) {
		if (voice == null) {
			voice = new HashMap();
		}
		voice.put("media_id", mediaId);
	}
	
	
	
	
}
