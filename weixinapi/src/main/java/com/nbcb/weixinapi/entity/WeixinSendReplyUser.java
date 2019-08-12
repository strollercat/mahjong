package com.nbcb.weixinapi.entity;

import java.io.IOException;
import java.util.List;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendReplyUser {
	private String accessToken;
	private String openId;
	private String lang;

	public static String ZH_LANG = "zh_CN";

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	static class ListTest {
		private List<String> list;

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}
	}
}
