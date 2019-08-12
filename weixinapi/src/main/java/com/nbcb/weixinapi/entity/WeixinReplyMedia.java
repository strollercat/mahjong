package com.nbcb.weixinapi.entity;


/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinReplyMedia extends WeixinReplyCommon {
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	private String type;
	private String media_id;
	long created_at;
}
