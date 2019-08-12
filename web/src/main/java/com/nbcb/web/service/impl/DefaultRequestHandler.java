package com.nbcb.web.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.io.UserActionApi;
import com.nbcb.web.service.RequestHandler;
import com.nbcb.web.util.Request;

public class DefaultRequestHandler implements RequestHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultRequestHandler.class);

	private UserActionApi userActionApi;

	public void setUserActionApi(UserActionApi userActionApi) {
		this.userActionApi = userActionApi;
	}

	@Override
	public void handleRequest(Request request) {
		// TODO Auto-generated method stub
		String code = request.getCode();
		String account = request.getAccount();
		Map map = request.getBody();
		if (code.equals("createRoom")) {
		} else if (code.equals("enterRoom")) {
		} else if (code.equals("leaveRoom")) {
		} else if (code.equals("ready")) {
			userActionApi.ready(account);
		} else if (code.equals("unReady")) {
		} else if (code.equals("mjAction")) {
			int action = (Integer) map.get("action");
			int card0 = (Integer) map.get("card0");
			int card1 = (Integer) map.get("card1");
			userActionApi.mjAction(account, action, card0, card1);
		} else if (code.equals("chatText")) {
			String text = (String) map.get("text");
			userActionApi.chatText(account, text);
		} else if (code.equals("chatVoice")) {
			String serverId = (String) map.get("serverId");
			String localId = (String) map.get("localId");
			userActionApi.chatVoice(account, serverId, localId);
		} else if (code.equals("bullet")) {
			int authorDir = (Integer) map.get("authorDir");
			int dir = (Integer) map.get("dir");
			int index = (Integer) map.get("index");
			userActionApi.bullet(account, authorDir, dir, index);
		} else if (code.equals("dismiss")) {
			boolean dismiss = (Boolean) map.get("dismiss");
			logger.info("### receivedismissrequest,account[" + account
					+ "]dismiss[" + dismiss + "]");
			userActionApi.requestDismissRoom(account, dismiss);
		} else if (code.equals("managed")) {
			userActionApi.cancalManaged(account);
		} else if (code.equals("threeWaterShoot")) {
			userActionApi.threeWaterShoot(account, (List) map.get("cns"));
		}
	}
}
