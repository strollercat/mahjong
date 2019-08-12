package com.nbcb.weixinapi.transport;

import java.util.HashMap;
import java.util.Map;

public class HttpClientRequest {
	private String method;
	private String url;
	private Map<String, String> headers = new HashMap<String, String>();
	private byte[] body;

	public static final String GETMETHOD = "GET";
	public static final String POSTMETHOD = "POST";

	public HttpClientRequest(String method, String url, Map<String, String> headers, byte[] body) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.body = body;
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public byte[] getBody() {
		return body;
	}

}
