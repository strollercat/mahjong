package com.nbcb.weixinapi.entity;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendReplyImage {

	private String fromUserName;

	private String toUserName;

	private String mediaId;

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
