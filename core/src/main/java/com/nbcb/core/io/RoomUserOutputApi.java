package com.nbcb.core.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomAction;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.LocationService;
import com.nbcb.core.user.Player;

public abstract class RoomUserOutputApi implements UserOutputApi {
	private static final Logger logger = LoggerFactory
			.getLogger(RoomUserOutputApi.class);

	protected PlayerStatQuery playerStatQuery;

	protected Server server;

	protected ClientPusher clientPusher;

	protected ResponseFilter responseFilter;

	protected MessageListener messageListener;

	protected LocationService locationService;

	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void setClientPusher(ClientPusher clientPusher) {
		this.clientPusher = clientPusher;
	}

	public void setResponseFilter(ResponseFilter responseFilter) {
		this.responseFilter = responseFilter;
	}

	public void setPlayerStatQuery(PlayerStatQuery playerStatQuery) {
		this.playerStatQuery = playerStatQuery;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	protected Map codeRoomNotice(Room room) {
		Map mapRet = room.format();
		mapRet.put("code", "roomNotice");
		return mapRet;
	}

	protected Map codeRoomStart(Room room) {
		Map mapRet = new HashMap();
		List listPlayers = new ArrayList();
		mapRet.put("users", listPlayers);
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Player player = room.getPlayerByIndex(i);
			String account = player.getAccount();
			Map mapPlayer = new HashMap();
			mapPlayer.put("dir", player.getPlayerOrder());
			mapPlayer.put("ready", player.isReady());
			mapPlayer.put("score",
					room.getPlayerScoreByAccount(player.getAccount()));
			listPlayers.add(mapPlayer);
		}
		mapRet.put("code", "roomStart");
		return mapRet;
	}

	protected Map codeEnterRoom(Player player, Room room) {
		String account = player.getAccount();
		Map mapRet = new HashMap();
		mapRet.put("code", "enterRoom");
		mapRet.put("dir", player.getPlayerOrder());
		mapRet.put("name", player.getAccount());
		mapRet.put("score", room.getPlayerScoreByAccount(account));
		return mapRet;
	}

	protected Map codeLeaveRoom(Player player, Room room) {
		// TODO Auto-generated method stub
		Map mapRet = new HashMap();
		mapRet.put("code", "leaveRoom");
		mapRet.put("dir", player.getPlayerOrder());
		return mapRet;
	}

	protected Map codeUserReady(Player player, Room room) {
		Map mapRet = new HashMap();
		mapRet.put("code", "userReady");
		mapRet.put("dir", player.getPlayerOrder());
		mapRet.put("ready", true);
		return mapRet;
	}

	protected Map codeRoomDestroy(Room room) {
		Map mapRet = new HashMap();
		mapRet.put("code", "destroy");
		mapRet.put("reason", room.getEndReason());
		return mapRet;
	}

	protected Map codeOffLine(Player player) {
		Map mapRet = new HashMap();
		mapRet.put("code", "offline");
		mapRet.put("dir", player.getPlayerOrder());
		return mapRet;
	}

	protected Map codeOnline(Player player) {
		Map mapRet = new HashMap();
		mapRet.put("code", "online");
		mapRet.put("dir", player.getPlayerOrder());
		return mapRet;
	}

	protected Map codeDismiss(Player player,boolean dismiss) {
		Map mapRet = new HashMap();
		mapRet.put("code", "dismiss");
		mapRet.put("dismiss",dismiss);
		mapRet.put("dir", player.getPlayerOrder());
		return mapRet;
	}

	protected Map codeMessageBox(String[] message) {
		Map mapRet = new HashMap();
		mapRet.put("code", "messagebox");
		mapRet.put("message", message);
		return mapRet;
	}

	/**
	 * 
	 * @param room
	 * @param player
	 * @return true ip相同 false ip不相同
	 */
	private boolean judgeIp(Room room, Player player) {

		if (StringUtils.isEmpty(player.getOriginIp())) {
			return false;
		}
		String message = null;
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Player tmpPlayer = room.getPlayerByIndex(i);
			if (tmpPlayer == player) {
				continue;
			}
			if (StringUtils.isEmpty(tmpPlayer.getOriginIp())) {
				continue;
			}
			if (tmpPlayer.getOriginIp().equals(player.getOriginIp())) {
				message = messageListener.getPlaceHolderAccount(tmpPlayer
						.getAccount())
						+ "和,,,"
						+ messageListener.getPlaceHolderAccount(player
								.getAccount()) + ",,,IP相同";
				break;
			}
		}
		if (message != null) {
			for (int i = 0; i < room.getActivePlayers(); i++) {
				Player tmpPlayer = room.getPlayerByIndex(i);
				String translateMessage = messageListener.translateMessage(
						tmpPlayer.getAccount(), message);

				this.sendMessageToPlayer(tmpPlayer,
						this.codeMessageBox(translateMessage.split(",,,")),
						room);
			}
			return true;
		}
		return false;
	}

	protected void sendMessageToPlayer(Player toPlayer, Map message, Room room) {
		Map mapInner = new HashMap();
		mapInner.putAll(message);
		mapInner = this.responseFilter.filter(toPlayer, mapInner, room);
		this.clientPusher.push(toPlayer.getAccount(), mapInner);
	}

	abstract protected void gameConnect(String account);

	/**
	 * 有没有dismiss的记录
	 */
	private void sendDismiss(Room room) {
		List<String> listDismiss = room.getDismissPlayers();
		if (listDismiss != null && listDismiss.size() > 0) {
			Player dismissPlayer = room.getPlayerByAccount(listDismiss.get(0));
			if (dismissPlayer != null) {
				for (int i = 0; i < room.getActivePlayers(); i++) {
					Player tmpPlayer = room.getPlayerByIndex(i);
					if (tmpPlayer == dismissPlayer) {
						continue;
					}
					Map oriMap = this.codeDismiss(dismissPlayer,true);
					Map pushMap = responseFilter
							.filter(tmpPlayer, oriMap, room);
					this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
				}
			}
		}
	}

	@Override
	public void connect(String account) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		Room room = server.getRoomByAccount(account);
		Player player = server.getPlayer(account);
		if (ps == PlayerStat.ROOM) {
			Map mapRet = this.codeRoomNotice(room);
			mapRet = this.responseFilter.filter(player, mapRet, room);
			this.clientPusher.push(account, mapRet);

			try {
				this.judgeIp(room, player); // 判断一下IP和位置信息
			} catch (Exception e) {
				logger.error("### ip position error,", e);
			}

			/**
			 * 如果游戏已经开始了，在房间里，发送online信号
			 */
			if (room.getOrder() != 0) {
				mapRet = this.codeOnline(player);

				for (int i = 0; i < room.getActivePlayers(); i++) {
					if (room.getPlayerByIndex(i).getAccount()
							.equals(player.getAccount())) {
						continue;
					}
					Map mapInner = new HashMap();
					mapInner.putAll(mapRet);
					mapInner = this.responseFilter.filter(
							room.getPlayerByIndex(i), mapInner, room);
					this.clientPusher.push(room.getPlayerByIndex(i)
							.getAccount(), mapInner);
				}
			}

			this.sendDismiss(room);
		} else if (ps == PlayerStat.GAME) {
			this.gameConnect(account);
			this.sendDismiss(room);
		}
	}

	@Override
	public void disconnect(String account) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		Room room = server.getRoomByAccount(account);
		Player player = server.getPlayer(account);
		Map mapRet = null;
		if (ps == PlayerStat.DOOR) {
			logger.info("### 不在房间里断线，不要去管它");
			return;
		}
		if (ps == PlayerStat.ROOM && room.getOrder() == 0) {
			logger.info("### 再房间里，且游戏未开始,所以离开房间！！！");
			server.leaveRoom(account, room.getId());
			return;
		} else {
			logger.info("### 再房间里，游戏已开始,所以离线！！！");
			mapRet = this.codeOffLine(player);
		}
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Map mapInner = new HashMap();
			mapInner.putAll(mapRet);
			Player tmpPlayer = room.getPlayerByIndex(i);
			mapInner = this.responseFilter.filter(tmpPlayer, mapInner, room);
			this.clientPusher.push(tmpPlayer.getAccount(), mapInner);
		}
	}

	@Override
	public void listen(Player player, Room room, RoomAction roomAction) {
		// TODO Auto-generated method stub
		int i;
		Player tmpPlayer;
		Map oriMap;
		Map pushMap;
		if (roomAction == RoomAction.CREATE) {

		} else if (roomAction == RoomAction.ENTER) {
			for (i = 0; i < room.getActivePlayers(); i++) {
				tmpPlayer = room.getPlayerByIndex(i);
				if (tmpPlayer != player) {
					oriMap = this.codeEnterRoom(player, room);
					pushMap = responseFilter.filter(tmpPlayer, oriMap, room);
					this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
				}
			}
		} else if (roomAction == RoomAction.LEAVE) {
			for (i = 0; i < room.getActivePlayers(); i++) {
				tmpPlayer = room.getPlayerByIndex(i);
				oriMap = this.codeLeaveRoom(player, room);
				pushMap = responseFilter.filter(tmpPlayer, oriMap, room);
				this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
			}
		} else if (roomAction == RoomAction.READY) {
			for (i = 0; i < room.getActivePlayers(); i++) {
				tmpPlayer = room.getPlayerByIndex(i);
				oriMap = this.codeUserReady(player, room);
				pushMap = responseFilter.filter(tmpPlayer, oriMap, room);
				this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
			}
		} else if (roomAction == RoomAction.UNREADY) {

		} else if (roomAction == RoomAction.DESTROY) {
			for (i = 0; i < room.getActivePlayers(); i++) {
				tmpPlayer = room.getPlayerByIndex(i);
				oriMap = this.codeRoomDestroy(room);
				pushMap = responseFilter.filter(tmpPlayer, oriMap, room);
				this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
			}
		} else if (roomAction == RoomAction.REQUESTDISMISS) { // 要通知的话也是第一个人请求的
			for (i = 0; i < room.getActivePlayers(); i++) {
				tmpPlayer = room.getPlayerByIndex(i);
				if (tmpPlayer == player) {
					continue;
				}
				oriMap = this.codeDismiss(player,true);
				pushMap = responseFilter.filter(tmpPlayer, oriMap, room);
				this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
			}
		} else if (roomAction == RoomAction.DENYDISMISS) {
			for (i = 0; i < room.getActivePlayers(); i++) {
				tmpPlayer = room.getPlayerByIndex(i);
				if (tmpPlayer == player) {
					continue;
				}
				oriMap = this.codeDismiss(player,false);
				pushMap = responseFilter.filter(tmpPlayer, oriMap, room);
				this.clientPusher.push(tmpPlayer.getAccount(), pushMap);
			}
		}
	}

}
