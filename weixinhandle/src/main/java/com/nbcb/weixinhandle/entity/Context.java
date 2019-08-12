package com.nbcb.weixinhandle.entity;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class Context extends HashMap {
	private HttpServletRequest request;

	public Context(HttpServletRequest request) {
		this.request = request;
	}

	public String getString(String key) {
		return (String) super.get(key);
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

}
