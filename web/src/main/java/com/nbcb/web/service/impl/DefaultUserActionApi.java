package com.nbcb.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.io.ClientPusher;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;
import com.nbcb.web.service.OnlineUserRegistry;

public class DefaultUserActionApi implements UserActionApi {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultUserActionApi.class);

	private static final String LASTMJACTION = "LASTMJACTION";

	private ClientPusher clientPusher;

	private PlayerStatQuery playerStatQuery;

	private OnlineUserRegistry onlineUserRegistry;

	public void setOnlineUserRegistry(OnlineUserRegistry onlineUserRegistry) {
		this.onlineUserRegistry = onlineUserRegistry;
	}

	public void setClientPusher(ClientPusher clientPusher) {
		this.clientPusher = clientPusher;
	}

	private Server server;

	public void setServer(Server server) {
		this.server = server;
	}

	public void setPlayerStatQuery(PlayerStatQuery playerStatQuery) {
		this.playerStatQuery = playerStatQuery;
	}

	@Override
	public Response ready(String account) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (!(ps == PlayerStat.ROOM)) {
			return ResponseFactory.newResponse("000001", "您当前未在房间中,无法准备");
		}
		Room room = server.getRoomByAccount(account);
		synchronized (room) {
			server.playerReady(account, room.getId());
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response unReady(String account) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (!(ps == PlayerStat.ROOM)) {
			return ResponseFactory.newResponse("000001", "您当前未在房间中,无法准备");
		}
		Room room = server.getRoomByAccount(account);
		server.playerUnready(account, room.getId());
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response enterRoom(String account, String roomId) {
		// TODO Auto-generated method stub

		Room room = server.getRoom(roomId);
		if (room == null) {
			return ResponseFactory.newResponse("000002", "房间不存在");
		}

		if (null != room.getPlayerByAccount(account)) { // 已经在这个房间里了
			return ResponseFactory.newResponse("000000", "成功");
		}
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		logger.info("### enterRoom ps[" + ps + "]account[" + account
				+ "]roomId[" + roomId + "]");

		if (ps == PlayerStat.GAME) {
			return ResponseFactory.newResponse("000004", "您还有一局游戏未结束,无法加入其它房间");
		}
		if (room.getActivePlayers() == room.getRoomInfo().getPlayerNum()) {
			return ResponseFactory.newResponse("000001", "房间已满");
		}

		if (ps == PlayerStat.ROOM
				&& server.getRoomByAccount(account).getOrder() > 0) {
			return ResponseFactory.newResponse("000004", "您还有一局游戏未结束,无法加入其它房间");
		}

		if (ps == PlayerStat.ROOM) { // 离开原来的房间
			this.leaveRoom(account);
		}

		synchronized (room) {
			if (room.getActivePlayers() == room.getRoomInfo().getPlayerNum()) {
				return ResponseFactory.newResponse("000001", "房间已满");
			}
			server.enterRoom(account, roomId);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response leaveRoom(String account) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);

		if (ps == PlayerStat.DOOR) {
			return ResponseFactory.newResponse("000001", "您当前未在房间中，无法离开房间");
		}

		if (ps == PlayerStat.ROOM
				&& server.getRoomByAccount(account).getOrder() > 0) {
			return ResponseFactory.newResponse("000002", "游戏开始了，无法离开房间");
		}
		if (ps == PlayerStat.GAME) {
			return ResponseFactory.newResponse("000002", "游戏开始了，无法离开房间");
		}
		server.leaveRoom(account, server.getRoomByAccount(account).getId());
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response mjAction(String account, int action, int card0, int card1) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (!(ps == PlayerStat.GAME)) {
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}
		Room room = server.getRoomByAccount(account);
		String roomId = room.getId();
		Game game = server.getRoom(roomId).currentGame();
		MajiangCards mcs = new MajiangCards();
		if (action == MajiangAction.USERDA || action == MajiangAction.USERGANG) {
			mcs.addTailCard(game.getAllCards().findCardByNumber(card0));
		} else if (action == MajiangAction.USERCHI) {
			if (card0 == card1) {
				return ResponseFactory.newResponse("000001", "参数非法!!!");
			}
			mcs.addTailCard(game.getAllCards().findCardByNumber(card0));
			mcs.addTailCard(game.getAllCards().findCardByNumber(card1));
		} else {
			mcs = null;
		}

		Player player = room.getPlayerByAccount(account);

		MajiangAction ma = new MajiangAction(player, action, mcs, true);
		synchronized (game) {
			nextGame(game, ma);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	private void nextGame(Game game, Action ma) {
		String account = ma.getPlayer().getAccount();
		WebSocketSession wss = onlineUserRegistry.get(account);
		if (wss == null) {
			logger.info("### WebSocketSession is null");
			game.nextAction(ma);
			return;
		}

		Long lastActionTime = (Long) wss.getAttributes().get(LASTMJACTION);

		if (lastActionTime == null) {
			logger.info("### 用户第一次发送指令,指令有效majiangAction[" + ma + "]");
			game.nextAction(ma);
			wss.getAttributes().put(LASTMJACTION, System.currentTimeMillis());
			return;
		}

		// 如果两次间隔小于50 ms,判断为重复指令,指令无效!!!
		if (Math.abs(System.currentTimeMillis() - lastActionTime) < 500) {
			logger.error("###################  用户重复发送了指令 majiangAction[" + ma
					+ "]");
			return;
		}
		// 指令有效，记录时间
		game.nextAction(ma);
		wss.getAttributes().put(LASTMJACTION, System.currentTimeMillis());
		return;
	}

	@Override
	public Response chatText(String account, String text) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.DOOR) {
			logger.info("### user[" + account
					+ "] is in door state,not allowed to chat");
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}
		Room room = server.getRoomByAccount(account);
		Player player = room.getPlayerByAccount(account);
		for (int i = 0; i < room.getActivePlayers(); i++) {
			String pushAccount = room.getPlayerByIndex(i).getAccount();
			Map info = new HashMap();
			info.put("code", "chatText");
			info.put("text", text);
			info.put("dir", player.getPlayerOrder());
			clientPusher.push(pushAccount, info);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response chatVoice(String account, String serverId, String localId) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.DOOR) {
			logger.info("### user[" + account
					+ "] is in door state,not allowed to chat");
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}
		Room room = server.getRoomByAccount(account);
		Player player = room.getPlayerByAccount(account);
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Map info = new HashMap();
			info.put("code", "chatVoice");
			info.put("serverId", serverId);
			info.put("localId", localId);
			info.put("dir", player.getPlayerOrder());
			clientPusher.push(room.getPlayerByIndex(i).getAccount(), info);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response bullet(String account, int authorDir, int dir, int index) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.DOOR) {
			logger.info("### user[" + account
					+ "] is in door state,not allowed to bullet");
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}
		Room room = server.getRoomByAccount(account);
		Player player = room.getPlayerByAccount(account);
		if (player.getPlayerOrder() != authorDir) {
			logger.info("### user[" + account
					+ "] is  not equal the player order");
			return ResponseFactory.newResponse("000001",
					"not equal the player order");
		}
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Map info = new HashMap();
			info.put("code", "bullet");
			info.put("authorDir", authorDir);
			info.put("dir", dir);
			info.put("index", index);
			clientPusher.push(room.getPlayerByIndex(i).getAccount(), info);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response requestDismissRoom(String account, boolean dismiss) {
		// TODO Auto-generated method stub
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.DOOR) {
			logger.info("### user[" + account
					+ "] is in door state,not allowed to requestDismissRoom");
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}

		if (ps == PlayerStat.GAME) {
			Room room = server.getRoomByAccount(account);
			if (room != null
					&& room.getRoomInfo().getName().equals("hongzhongMajiang")) {
				logger.info("### user["
						+ account
						+ "] is in game state,not allowed to requestDismissRoom");
				return ResponseFactory.newResponse("000001", "游戏已开始，不允许解散！！！");
			}
		}
		Room room = server.getRoomByAccount(account);
		server.requestDismissRoom(account, dismiss, room.getId());
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response cancalManaged(String account) {
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps != PlayerStat.GAME) {
			logger.info("### user[" + account
					+ "] is not in game state,not allowed to cancalManaged");
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}
		Room room = server.getRoomByAccount(account);
		Player player = room.getPlayerByAccount(account);

		MajiangGame mjGame = (MajiangGame) room.currentGame();
		mjGame.putPlayerManage(player.getAccount(), false);
		mjGame.putLastWaitUserActionTime(account, System.currentTimeMillis()
				+ 2 * 60 * 60 * 1000);
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Map map = new HashMap();
			map.put("code", "managed");
			map.put("dir", player.getPlayerOrder());
			map.put("managed", false);
			clientPusher.push(room.getPlayerByIndex(i).getAccount(), map);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public Response threeWaterShoot(String account, List cns) {
		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (!(ps == PlayerStat.GAME)) {
			return ResponseFactory.newResponse("000001", "您当前未在游戏中！");
		}
		Room room = server.getRoomByAccount(account);
		String roomId = room.getId();
		Game game = server.getRoom(roomId).currentGame();

		PokerCards pcs = new PokerCards();
		for (Object o : cns) {
			int cn = (Integer) o;
			pcs.addTailCard(game.getAllCards().findCardByNumber(cn));
		}

		Player player = room.getPlayerByAccount(account);

		ThreeWaterPokerAction a = new ThreeWaterPokerAction(player,
				ThreeWaterPokerAction.SHOOT, pcs, true);

		synchronized (game) {
			nextGame(game, a);
		}
		return ResponseFactory.newResponse("000000", "成功");
	}
}
