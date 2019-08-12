package com.nbcb.core.server;

import com.nbcb.core.room.Room;
import com.nbcb.core.user.Player;

public class DefaultPlayerStatQuery implements PlayerStatQuery {

	private Server server;

	public void setServer(Server server) {
		this.server = server;
	}

	@Override
	public PlayerStat queryPlayerStat(String account) {
		// TODO Auto-generated method stub
		Room room = server.getRoomByAccount(account);
		if (room == null) {
			return PlayerStat.DOOR;
		}
		Player player = room.getPlayerByAccount(account);
		if (room.isGaming()) {
			return PlayerStat.GAME;
		}
		return PlayerStat.ROOM;
	}

}
