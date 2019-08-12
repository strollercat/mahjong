package com.nbcb.weixinapi.entity;

import java.util.ArrayList;
import java.util.List;

public class WeixinSendUploadMpnews {
	private String accessToken;

	private List<WeixinSendMpnew> articles = new ArrayList();

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<WeixinSendMpnew> getArticles() {
		return articles;
	}

	public void addWeixinSendMpnew(WeixinSendMpnew weixinSendMpnew) {
		articles.add(weixinSendMpnew);
	}
}
