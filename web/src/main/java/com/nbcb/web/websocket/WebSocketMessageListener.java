package com.nbcb.web.websocket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.core.io.ClientPusher;
import com.nbcb.core.io.MessageListener;
import com.nbcb.core.room.Room;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

public class WebSocketMessageListener implements MessageListener {

	private static final Logger logger = LoggerFactory
			.getLogger(WebSocketMessageListener.class);

	private ClientPusher clientPusher;

	private WeixinUserService weixinUserService;

	public void setClientPusher(ClientPusher clientPusher) {
		this.clientPusher = clientPusher;
	}

	public void setWeixinUserService(WeixinUserService weixinUserService) {
		this.weixinUserService = weixinUserService;
	}

	@Override
	public void listen(String account, String message) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(message)) {
			return;
		}
		if (StringUtils.isEmpty(account)) {
			return;
		}
		try {
			Map map = new HashMap();
			map.put("code", "tint");
//			logger.info("### account["+account+"]message["+message+"]");
			String translateMessage = this.translateMessage(account, message);
//			logger.info("### translateMessage["+translateMessage+"]");
//			logger.info("### account["+account+"]message["+message+"]translateMessage["+translateMessage+"]");
			WeixinReplyUser wru = weixinUserService
					.getWeixinUserByOpenid(account);
			if (wru == null) {
				return;
			}
			translateMessage = wru.getNickname() + "请注意: " + translateMessage;
			map.put("tint", translateMessage);
			clientPusher.push(account, map);
		} catch (Exception e) {
			logger.error("### message lisntener listen error", e);
		}
	}

	@Override
	public void listen(Room room, String message) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(message)) {
			return;
		}
		for (int i = 0; i < room.getActivePlayers(); i++) {
			this.listen(room.getPlayerByIndex(i).getAccount(), message);
		}
	}

	@Override
	public String getPlaceHolderAccount(String account) {
		// TODO Auto-generated method stub
		return "[" + account + "]";
	}

	@Override
	public String translateMessage(String account, String message) {
		// TODO Auto-generated method stub
		if (!message.contains("[") && !message.contains("]")) {
			return message;
		}
		String translateMessage = "";
		int length = message.length();
		char ch;
		int prefix = -1;
		int suffix = -1;
		int normal = 0;
		String innerAccount;
		for (int i = 0; i < length; i++) {
			ch = message.charAt(i);
			if (ch == '[') {
				prefix = i;
				if (i - normal > 0) {
					translateMessage += message.substring(normal, i);
				}
			} else if (ch == ']') {
				suffix = i;
				if (suffix - prefix > 0) {
					innerAccount = message.substring(prefix + 1, suffix);
					logger.info("### innerAccount["+innerAccount+"]");
					if (innerAccount.equals(account)) {
						translateMessage += "您";
					} else {
						translateMessage += weixinUserService
								.getWeixinUserByOpenid(innerAccount)
								.getNickname();
					}
				}
				normal = i + 1;
				prefix = -1;
				suffix = -1;
			} else if (i == length - 1) {
				if (prefix == -1 && suffix == -1) {
					translateMessage += message.substring(normal, length);
				}
			}
		}
		return translateMessage;
	}

}
