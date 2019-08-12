package com.nbcb.web.service;

import org.springframework.web.socket.WebSocketSession;

public interface OnlineUserRegistry {

	public void registry(String account, WebSocketSession session);

	public void remove(String account);

	public WebSocketSession get(String account);
	
	
	
}
