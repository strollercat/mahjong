package com.nbcb.weixinapi.transport;

public class HttpClientResponse {
	private int state;
	private byte[] body;

	public HttpClientResponse(int state, byte[] body) {
		this.state = state;
		this.body = body;
	}

	public int getState() {
		return state;
	}

	public byte[] getBody() {
		return body;
	}

}
