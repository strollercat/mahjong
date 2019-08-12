package com.nbcb.web.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

import com.nbcb.web.service.OnlineUserRegistry;

public class DefaultOnlineUserRegistry implements OnlineUserRegistry {

	private Map<String, WebSocketSession> mapUsers = new ConcurrentHashMap<String, WebSocketSession>();

	@Override
	public void registry(String account, WebSocketSession session) {
		// TODO Auto-generated method stub
		mapUsers.put(account, session);
	}

	@Override
	public void remove(String account) {
		// TODO Auto-generated method stub
		mapUsers.remove(account);
	}

	@Override
	public WebSocketSession get(String account) {
		// TODO Auto-generated method stub
		return mapUsers.get(account);
	}

}
