package com.nbcb.core.server;

import com.nbcb.core.action.Actions;
import com.nbcb.core.action.DefaultActions;
import com.nbcb.core.room.DefaultRoom;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.user.DefaultPlayers;
import com.nbcb.core.user.Players;

public abstract class AbstractChannel implements Channel {

	protected Server server;

	public void setServer(Server server) {
		this.server = server;
	}

	@Override
	public Server getServer() {
		return this.server;
	}
	
	@Override
	public Room createRoom(String account, RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		String id = server.generateId();
		Room room = new DefaultRoom(roomInfo, id);
		room.setChannel(this);
		return room;
	}
	
	@Override
	public Players createPlayers(int size) {
		// TODO Auto-generated method stub
		return new DefaultPlayers(size);
	}
	
	
	@Override
	public Actions createActions() {
		// TODO Auto-generated method stub
		return new DefaultActions();
	}

}
