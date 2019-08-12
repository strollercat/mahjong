package com.nbcb.majiang.timer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.service.TimerService;
import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.io.ClientPusher;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class PlayerManagedStrategyService implements TimerService {

	private static final Logger logger = LoggerFactory
			.getLogger(PlayerManagedStrategyService.class);

	private long timeOut;

	private Server server;

	private ClientPusher clientPusher;

	private UserActionApi userActionApi;

	public void setUserActionApi(UserActionApi userActionApi) {
		this.userActionApi = userActionApi;
	}

	public void setClientPusher(ClientPusher clientPusher) {
		this.clientPusher = clientPusher;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	private Map codeManagerd(Player player) {
		Map map = new HashMap();
		map.put("code", "managed");
		map.put("dir", player.getPlayerOrder());
		map.put("managed", true);
		return map;
	}

	private void autoAction(MajiangGame mjGame, Player player) {

		Actions legalActions = mjGame.getLegalActions();
		if (legalActions == null || legalActions.size() == 0) {
			return;
		}
		Action[] playerActions = legalActions.getActionsByPlayer(player
				.getAccount());
		if (playerActions == null || playerActions.length == 0) {
			return;
		}
		boolean hasDa = false;
		for (Action tmpAction : playerActions) {
			if (!tmpAction.isUserAction()) {
				continue;
			}
			if (tmpAction.getPlayer() == null) {
				continue;
			}
			if (tmpAction.getType() == MajiangAction.DANONHUA) {
				hasDa = true;
				break;
			}
		}
		MajiangPlayer mjPlayer = (MajiangPlayer) player;
		logger.info("### account["+mjPlayer.getAccount()+"] autoda hasDa["+hasDa+"]");
		if (hasDa) {
			int card0 = mjPlayer.getMajiangInnerCards().getTailCard()
					.getNumber();
			userActionApi.mjAction(mjPlayer.getAccount(), MajiangAction.USERDA,
					card0, -1);
		} else {
			userActionApi.mjAction(mjPlayer.getAccount(), MajiangAction.NO, -1,
					-1);
		}
	}

	private Set<Player> findManagedPlayer(MajiangGame mjGame, Actions actions) {
		Actions legalActions = mjGame.getLegalActions();
		if (legalActions == null || legalActions.size() == 0) {
			return null;
		}

		Set<Player> set = new HashSet<Player>();
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			Player tmpPlayer = mjGame.getPlayerByIndex(i);
			if (mjGame.isPlayerManagerd(tmpPlayer.getAccount())) { // 已经是托管状态了
				continue;
			}
			long lastTime = mjGame.getLastWaitUserActionTimeByAccount(tmpPlayer
					.getAccount());
			if (System.currentTimeMillis() - lastTime > this.timeOut) {
				set.add(tmpPlayer);
			}
		}
		return set;

	}

	private void judgeLegalAction(MajiangGame mjGame, Actions actions) {

		Set<Player> set = this.findManagedPlayer(mjGame, actions);

		if (set == null || set.size() == 0) {
			return;
		}
		// logger.info("### found manager player!! "
		// + set.iterator().next().getAccount());
		for (Player player : set) {
			mjGame.putPlayerManage(player.getAccount(), true);
			Map map = this.codeManagerd(player);
			for (int i = 0; i < mjGame.getActivePlayers(); i++) {
				this.clientPusher.push(mjGame.getPlayerByIndex(i).getAccount(),
						map);
			}
		}

	}

	@Override
	public void service() {
		// logger.info("### start to find managerd player!!!!");
		List<Room> listRoom = server.getAllRooms();
		if (listRoom == null || listRoom.size() == 0) {
			return;
		}
		for (Room room : listRoom) {
			if (room.getOrder() == 0) { // 如果游戏都还未开始
				continue;
			}
			if (!room.isGaming()) {
				continue;
			}
			if (!(room.getRoomInfo().getName().equals("hongzhongMajiang") || room
					.getRoomInfo().getName().equals("guangdongMajiang"))) {
				continue;
			}
			MajiangGame mjGame = (MajiangGame) room.currentGame();

			this.judgeLegalAction(mjGame, mjGame.getLegalActions());

			for (int i = 0; i < mjGame.getActivePlayers(); i++) {
				Player tmpPlayer = mjGame.getPlayerByIndex(i);
				if (mjGame.isPlayerManagerd(tmpPlayer.getAccount())) {
					this.autoAction(mjGame, tmpPlayer);
				}
			}

		}

	}

}
