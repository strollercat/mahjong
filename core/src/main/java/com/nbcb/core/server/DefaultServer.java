package com.nbcb.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomAction;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.room.RoomListener;
import com.nbcb.core.user.Player;

public class DefaultServer implements Server {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultServer.class);

	private Map<String/* channelId */, Channel> mapChannel = new HashMap<String, Channel>();

	private Map<String/* roomId */, Room> mapRoom = new ConcurrentHashMap<String, Room>();

	private Map<String/* account */, Player> mapPlayer = new ConcurrentHashMap<String, Player>();

	private Map<String/* account */, Room> mapPlayerRoom = new ConcurrentHashMap<String, Room>();

	private List<RoomListener> roomListeners;

	public void setRoomListeners(List<RoomListener> roomListeners) {
		this.roomListeners = roomListeners;
	}

	public void setMapChannel(Map<String, Channel> mapChannel) {
		this.mapChannel = mapChannel;
	}

	private void notify(Player player, Room room, RoomAction roomAction) {
		logger.info("###  roomListener "
				+ (player == null ? null : player.getAccount()) + " "
				+ roomAction);
		for (RoomListener roomListener : roomListeners) {
			roomListener.listen(player, room, roomAction);
		}
	}

	@Override
	public Room getRoom(String id) {
		return mapRoom.get(id);
	}

	@Override
	public Player getPlayer(String account) {
		return mapPlayer.get(account);
	}

	@Override
	public Channel getChannel(String name) {
		// TODO Auto-generated method stub
		return mapChannel.get(name);
	}

	@Override
	public String generateId() {
		// TODO Auto-generated method stub
		while (true) {
			Random r = new Random();
			String str = String.valueOf(r.nextInt(999999));
			if (str.length() < 6) {
				continue;
			}
			if (mapRoom.get(str) == null) {
				return str;
			}
		}
	}

	@Override
	public Room createRoom(String account, RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		Channel channel = this.getChannel(roomInfo.getName());
		Room room = channel.createRoom(account, roomInfo);
		if (room != null) {
			mapRoom.put(room.getId(), room);
			room.setCreatePlayer(account);
			room.setCreateTime(System.currentTimeMillis());
			Player player = room.addPlayer(account);
			mapPlayer.put(account, player);
			mapPlayerRoom.put(account, room);
			this.notify(player, room, RoomAction.CREATE);
		}
		return room;
	}

	@Override
	public Player enterRoom(String account, String roomId) {
		// TODO Auto-generated method stub
		Room room = mapRoom.get(roomId);
		if (room == null) {
			return null;
		}
		Player player = room.addPlayer(account);
		if (player != null) {
			mapPlayer.put(player.getAccount(), player);
			mapPlayerRoom.put(player.getAccount(), room);
			this.notify(player, room, RoomAction.ENTER);

			if (room.getActivePlayers() == room.getRoomInfo().getPlayerNum()
					&& room.allPlayerReady()) {
				room.gameStart();
			}
			return player;
		}
		return null;
	}

	@Override
	public Player leaveRoom(String account, String roomId) {
		// TODO Auto-generated method stub
		Room room = mapRoom.get(roomId);
		if (room == null) {
			logger.info("### leave Room ,room is null roomId [" + roomId + "] ");
			return null;
		}
		Player player = room.leavePlayer(account);
		if (player != null) {
			mapPlayer.remove(player.getAccount());
			mapPlayerRoom.remove(account);
			this.notify(player, room, RoomAction.LEAVE);
			return player;
		}
		logger.info("### leave Room player is null  ");
		return null;
	}

	@Override
	public void playerReady(String account, String roomId) {
		// TODO Auto-generated method stub
		Room room = mapRoom.get(roomId);
		if (room == null) {
			return;
		}
		if (room.playerReady(account) != null) {
			this.notify(room.getPlayerByAccount(account), room,
					RoomAction.READY);
		}

		if (room.getActivePlayers() == room.getRoomInfo().getPlayerNum()
				&& room.allPlayerReady()) {
			room.gameStart();
		}
	}

	@Override
	public void playerUnready(String account, String roomId) {
		// TODO Auto-generated method stub
		Room room = mapRoom.get(roomId);
		if (room == null) {
			return;
		}
		if (room.playerUnReady(account) != null) {
			this.notify(room.getPlayerByAccount(account), room,
					RoomAction.UNREADY);
		}
	}

	public Room getRoomByAccount(String account) {
		return this.mapPlayerRoom.get(account);
	}

	@Override
	public void destroyRoom(final String roomId, String destroyReason) {
		// TODO Auto-generated method stub
		Room room = this.mapRoom.get(roomId);
		if (room != null) {
			int size = room.getActivePlayers();
			if (size != 0) {
				for (int i = 0; i < size; i++) {
					Player player = room.getPlayerByIndex(i);
					this.mapPlayerRoom.remove(player.getAccount());
					this.mapPlayer.remove(player.getAccount());
				}
			}
			this.mapRoom.remove(roomId);
			room.setEndReason(destroyReason);
			this.notify(null, room, RoomAction.DESTROY);
		}
	}

	@Override
	public String toString() {
		String str = "### " + this.mapRoom.toString();
		str += "### " + mapPlayer.keySet();
		return str;
	}

	@Override
	public Set<String> roomIdSet() {
		// TODO Auto-generated method stub
		return mapRoom.keySet();
	}

	@Override
	public List<Room> getAllRooms() {
		Set<String> keySet = this.mapRoom.keySet();
		if (keySet == null || keySet.size() == 0) {
			return null;
		}
		List<Room> list = new ArrayList<Room>();
		for (String key : keySet) {
			list.add(this.mapRoom.get(key));
		}
		return list;
	}

	private boolean containsDismiss(List<String> listDismiss, String account) {
		if (StringUtils.isEmpty(account)) {
			return false;
		}
		for (String dismiss : listDismiss) {
			if (account.equals(dismiss)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void requestDismissRoom(String account, boolean dismiss,
			String roomId) {
		Room room = this.getRoom(roomId);
		if (room == null) {
			return;
		}
		Player player = room.getPlayerByAccount(account);
		if (player == null) {
			return;
		}
		List<String> listDismiss = room.getDismissPlayers();
		if (dismiss) {
			if (this.containsDismiss(listDismiss, account)) {// 本来就已经申请解散了，不要去处理
				return;
			}
			int nowDismissSize = room.requestDismiss(account, true);

			if (room.getRoomInfo().getPlayerNum() == 4) {
				if (nowDismissSize == 1) { // 如果是第一个人申请的，通知一下
					this.notify(player, room, RoomAction.REQUESTDISMISS);
				} else if (nowDismissSize == 2) { // 是第二个人申请的,因为之前已经发送了通知,不需要发送了

				} else if (nowDismissSize == 3) { // 是第三个人同意的
					this.destroyRoom(roomId, "三个玩家同意解散房间,房间解散");
				}
			} else {
				if (nowDismissSize == 1) { // 如果是第一个人申请的，通知一下
					this.notify(player, room, RoomAction.REQUESTDISMISS);
				} else if (nowDismissSize == 2) { // 是第二个人申请的,因为之前已经发送了通知,不需要发送了
					this.destroyRoom(roomId, "两个玩家同意解散房间,房间解散");
				}
			}
		} else {
			if (listDismiss.size() == 0) { // 没有人申请，不要去处理
				return;
			}
			if (this.containsDismiss(listDismiss, account)) { // 这个人之前已经同意过了,不要去鸟他
				return;
			}
			room.requestDismiss(account, false);
			this.notify(player, room, RoomAction.DENYDISMISS);
		}
	}

}
