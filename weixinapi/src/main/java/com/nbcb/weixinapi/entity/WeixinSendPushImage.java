package com.nbcb.weixinapi.entity;

import java.util.HashMap;
import java.util.Map;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendPushImage {
	

	private String accessToken;
	private String touser;
	private String msgtype = "image";
	private Map image = new HashMap();

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

	public void setMediaId(String mediaId) {
		if (image == null) {
			image = new HashMap();
		}
		image.put("media_id", mediaId);
	}
	
	public Map getImage() {
		return image;
	}

}
