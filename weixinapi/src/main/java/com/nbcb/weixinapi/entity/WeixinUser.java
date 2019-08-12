package com.nbcb.weixinapi.entity;

public class WeixinUser {

	private String appid;
	private String openid;
	private String name;
	private String headUrl;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return this.appid + " " + this.openid + " " + this.name + " "
				+ this.headUrl;
	}

}
