package com.nbcb.weixinapi.entity;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendReplyText {
	
	private String content;
	
	private String fromUserName;
	
	private String toUserName;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	
	
}
