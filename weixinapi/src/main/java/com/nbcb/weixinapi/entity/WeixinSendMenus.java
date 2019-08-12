package com.nbcb.weixinapi.entity;

import java.util.List;

public class WeixinSendMenus {
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private String accessToken;
	
	private List button;
	
	public List getButton() {
		return button;
	}

	public void setButton(List button) {
		this.button = button;
	}

	public static class WeixinSendSubMenu {
		
		private String name;
		private List<WeixinSendMenu> sub_button;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<WeixinSendMenu> getSub_button() {
			return sub_button;
		}
		public void setSub_button(List<WeixinSendMenu> sub_button) {
			this.sub_button = sub_button;
		}
		
	}
	
	public static class WeixinSendMenu  {
		public final static String TYPE_CLICK = "click";
		public final static String TYPE_VIEW = "view";
		private String type;
		private String key;
		private String name;
		private String url;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
