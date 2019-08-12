package com.nbcb.web.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.nbcb.common.helper.Response;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.web.service.WeixinLoginService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinUser;

public class MajiangHandshakeInterceptor extends
		HttpSessionHandshakeInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangHandshakeInterceptor.class);

	private PlayerStatQuery playerStatQuery;

	private Server server;

	private UserActionApi userActionApi;

	private WeixinUserService weixinUserService;

	public void setWeixinUserService(WeixinUserService weixinUserService) {
		this.weixinUserService = weixinUserService;
	}

	public void setUserActionApi(UserActionApi userActionApi) {
		this.userActionApi = userActionApi;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setPlayerStatQuery(PlayerStatQuery playerStatQuery) {
		this.playerStatQuery = playerStatQuery;
	}

	protected WeixinUser getWeixinUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			logger.info("### session is null");
			return null;
		}

		WeixinUser wu = (WeixinUser) session
				.getAttribute(WeixinLoginService.WEIXINUSERKEY);
		if (wu == null || StringUtils.isEmpty(wu.getOpenid())) {
			logger.info("### user is not login!!");
			return null;
		}

		return wu;
	}

	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		HttpServletRequest httpRequest = servletRequest.getServletRequest();

		WeixinUser wu = this.getWeixinUser(httpRequest);
		String account = null;
		if (wu == null || StringUtils.isEmpty(wu.getOpenid())) {
			account = "";
		}else{
			account = wu.getOpenid();
		}
		attributes.put("account", account);

		String originIp = weixinUserService.getOriginIp(httpRequest);
		if (originIp == null) {
			originIp = "";
		}
		attributes.put("originIp", originIp);

		String roomId = httpRequest.getParameter("roomId");
		if (roomId == null) {
			roomId = "";
		}
		attributes.put("roomId", roomId);

		return true;

	}

}
