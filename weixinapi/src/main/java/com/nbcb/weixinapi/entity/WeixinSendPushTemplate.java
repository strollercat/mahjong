package com.nbcb.weixinapi.entity;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendPushTemplate {
	private String accessToken;
	private String touser;
	private String url;
	private String topcolor;
	private Map <String,WeixinSendPushTemplateItem>data = new HashMap();
	private String template_id;

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public void addItem(String key, WeixinSendPushTemplateItem item) {
		data.put(key, item);
	}

	public Map getData() {
		return data;
	}

	public static class WeixinSendPushTemplateItem {
		private String value;
		private String color;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String toString(){
			return value+" "+color;
		}
	}
}
