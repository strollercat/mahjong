package com.nbcb.weixinapi.entity;

import java.util.HashMap;
import java.util.Map;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendPushVideo {
	private String accessToken;
	private String touser;
	private String msgtype = "video";
	private WeixinSendVideo video;

	public WeixinSendVideo getVideo() {
		return video;
	}

	public void setVideo(WeixinSendVideo video) {
		this.video = video;
	}

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
}
