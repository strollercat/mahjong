package com.nbcb.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.io.UserOutputApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomAction;
import com.nbcb.core.room.RoomListener;
import com.nbcb.core.user.Player;

public class WebSocketNotifyRoomListener implements RoomListener {

	private static final Logger logger = LoggerFactory
			.getLogger(WebSocketNotifyRoomListener.class);
	
	
	private UserOutputApi userOutputApi;

	public void setUserOutputApi(UserOutputApi userOutputApi) {
		this.userOutputApi = userOutputApi;
	}

	@Override
	public void listen(Player player, Room room, RoomAction roomAction) {
		logger.info("### WebSocketNotifyRoomListener roomListener " + (player == null?null:player.getAccount())+" "+roomAction);
		userOutputApi.listen(player, room, roomAction);
	}
}
