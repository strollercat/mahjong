package com.nbcb.web.util;

import java.util.Map;

public class Request {
	private String account;
	private String code;
	private Map body;

	public Request(String account, String code, Map body) {
		this.account = account;
		this.code = code;
		this.body = body;
	}

	public String getCode() {
		return code;
	}

	public Map getBody() {
		return body;
	}

	public String getAccount() {
		return account;
	}

	@Override
	public String toString() {
		return this.account + " " + this.code + " " + this.body;
	}

}
