package com.nbcb.web.websocket;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.nbcb.common.helper.Response;
import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.io.UserOutputApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.Player;
import com.nbcb.web.service.OnlineUserRegistry;
import com.nbcb.web.service.RequestHandler;
import com.nbcb.web.util.Request;

public class MajiangWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangWebSocketHandler.class);

	private OnlineUserRegistry onlineUserRegistry;

	private PlayerStatQuery playerStatQuery;

	private Server server;

	private UserActionApi userActionApi;

	public void setUserActionApi(UserActionApi userActionApi) {
		this.userActionApi = userActionApi;
	}

	public void setPlayerStatQuery(PlayerStatQuery playerStatQuery) {
		this.playerStatQuery = playerStatQuery;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setOnlineUserRegistry(OnlineUserRegistry onlineUserRegistry) {
		this.onlineUserRegistry = onlineUserRegistry;
	}

	private RequestHandler requestHandler;

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	private UserOutputApi userOutputApi;

	public void setUserOutputApi(UserOutputApi userOutputApi) {
		this.userOutputApi = userOutputApi;
	}

	private void pushMessage(WebSocketSession session, Map info) {
		try {
			String strPush = JsonUtil.encode(info);
			TextMessage message = new TextMessage(strPush.getBytes("utf-8"));
			session.sendMessage(message);
		} catch (Exception e) {
			logger.error("### push message error", e);
		}
	}

	private void pushDestroyMessage(WebSocketSession session, String reason) {
		Map mapRet = new HashMap();
		mapRet.put("code", "destroy");
		mapRet.put("reason", reason);
		this.pushMessage(session, mapRet);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		String account = (String) session.getAttributes().get("account");
		String originIp = (String) session.getAttributes().get("originIp");
		String roomId = (String) session.getAttributes().get("roomId");
		logger.info("### connect to the websocket success......,account["
				+ account + "]" + "   originIp[" + originIp + "]roomId["
				+ roomId + "]");

		if (StringUtils.isEmpty(account)) {
			this.pushDestroyMessage(session, "您还未登陆");
			if (session.isOpen()) {
				session.close();
			}
			return;
		}
		if (StringUtils.isEmpty(roomId)) {
			this.pushDestroyMessage(session, "房间不存在");
			if (session.isOpen()) {
				session.close();
			}
			return;
		}

		Room room = server.getRoom(roomId);
		if (room == null) {
			this.pushDestroyMessage(session, "进入房间失败");
			if (session.isOpen()) {
				session.close();
			}
			return;
		}

		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.DOOR) {
			Response roomResponse = userActionApi.enterRoom(account, roomId);
			if (!roomResponse.getErrCode().equals("000000")) {
				this.pushDestroyMessage(session, "进入房间失败");
				if (session.isOpen()) {
					session.close();
				}
				return;
			}
		} else {
			Room playerRoom = server.getRoomByAccount(account);
			if (playerRoom == null) {
				this.pushDestroyMessage(session, "进入房间失败");
				if (session.isOpen()) {
					session.close();
				}
				return;
			}

			if (!playerRoom.getId().equals(roomId)) {
				this.pushDestroyMessage(session, "进入房间失败");
				if (session.isOpen()) {
					session.close();
				}
				return;
			}
		}

		Player player = server.getPlayer(account);
		if (player != null) {
			player.setOriginIp(originIp);
		}
		onlineUserRegistry.registry(account, session);
		userOutputApi.connect(account);
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		logger.info("### handleTransportError");
		if (session.isOpen()) {
			session.close();
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		String account = (String) session.getAttributes().get("account");
		logger.info("### websocket connection closed......" + account);
		onlineUserRegistry.remove(account);
		userOutputApi.disconnect(account);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		try {
			String content = message.getPayload();
			Map map = (Map) JsonUtil.decode(content, HashMap.class);
			String account = (String) session.getAttributes().get("account");
			Request request = new Request(account, (String) map.get("code"),
					map);
			logger.info("### receive client request [" + request + "]");
			requestHandler.handleRequest(request);
		} catch (Exception e) {
			logger.error("### handleTextMessage  error!!!!", e);
		}

	}
}
