package com.nbcb.web.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.io.ClientPusher;
import com.nbcb.web.service.OnlineUserRegistry;

public class WebsocketClientPusher implements ClientPusher {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketClientPusher.class);

	private OnlineUserRegistry onlineUserRegistry;

	public void setOnlineUserRegistry(OnlineUserRegistry onlineUserRegistry) {
		this.onlineUserRegistry = onlineUserRegistry;
	}

	@Override
	public void push(String account, Map info) {
		// TODO Auto-generated method stub
		try {
			String strPush = JsonUtil.encode(info);
			logger.info("######  push result account[" + account + "]info[" + strPush + "]");

			WebSocketSession session = onlineUserRegistry.get(account);
			if (session == null) {
				return;
			}
			TextMessage message = new TextMessage(strPush.getBytes("utf-8"));
			session.sendMessage(message);
		} catch (Exception e) {
			logger.error("push message to account[" + account + "] error", e);
		}
	}

}
