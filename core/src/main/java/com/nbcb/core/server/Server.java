package com.nbcb.core.server;

import java.util.List;
import java.util.Set;

import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.user.Player;

public interface Server {

	public void requestDismissRoom(String account, boolean dismiss,
			String roomId);

	// create room
	public Room createRoom(String account, RoomInfo roomInfo);

	// enter room
	public Player enterRoom(String account, String roomId);

	// leave room
	public Player leaveRoom(String account, String roomId);

	// player ready
	public void playerReady(String account, String roomId);

	// player unready
	public void playerUnready(String account, String roomId);

	public Set<String> roomIdSet();

	public Channel getChannel(String name);

	public Room getRoom(String id);

	public Player getPlayer(String account);

	public Room getRoomByAccount(String account);
	
	public List<Room> getAllRooms();

	/**
	 * 销毁房间
	 * 
	 * @param roomId
	 */
	public void destroyRoom(String roomId, String destroyReason);

	public String generateId();

}
