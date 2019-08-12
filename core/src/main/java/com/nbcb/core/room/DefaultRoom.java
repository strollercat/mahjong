package com.nbcb.core.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.server.Channel;
import com.nbcb.core.user.Player;
import com.nbcb.core.user.Players;

public class DefaultRoom implements Room {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultRoom.class);

	private List<String> listDismiss = new ArrayList<String>();

	private int uniqueId;

	private String createPlayerAccount;

	private long createTime;

	private List<Game> listGame = new ArrayList<Game>();

	private PlayerScores playerScores = new DefaultPlayerScores();

	private int order;

	private Channel channel;

	private String id;

	private RoomInfo roomInfo;

	private Players players = null;

	private String endReason;

	// 当前游戏
	private Game currentGame;

	// 是否正在游戏中
	private boolean gaming;

	// 初始积分
	private int initScore = 0;

	public Object destroyLock = new Object();

	public void setPlayers(Players players) {
		this.players = players;
	}

	@Override
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public DefaultRoom(RoomInfo roomInfo, String id) {
		this.id = id;
		this.roomInfo = roomInfo;
		this.order = 0;
	}

	public DefaultRoom(RoomInfo roomInfo, String id, int initScore) {
		this.id = id;
		this.roomInfo = roomInfo;
		this.order = 0;
		this.initScore = initScore;
	}

	@Override
	public boolean allPlayerReady() {
		int total = this.players.getActivePlayers();
		for (int i = 0; i < total; i++) {
			Player player = this.players.getPlayerByIndex(i);
			// logger.info("### playerAccount[" + player.getAccount() +
			// "] isReady[" + player.isReady() + "]");
			if (!player.isReady()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Player addPlayer(String account) {
		if (players == null) {
			this.players = this.channel.createPlayers(roomInfo.getPlayerNum());
		}
		Player player = this.channel.createPlayer(account);

		Player retPlayer = players.addPlayer(player);
		if (retPlayer == null) {
			return null;
		}
		playerScores.setPlayerScore(account, this.initScore);
		return retPlayer;
	}

	@Override
	public Player leavePlayer(String account) {
		Player player = this.players.leavePlayer(account);
		this.playerScores.removePlayerScoreByAccount(account);
		return player;
	}

	@Override
	public String getCreatePlayer() {
		// TODO Auto-generated method stub
		return this.createPlayerAccount;
	}

	@Override
	public void setCreatePlayer(String account) {
		this.createPlayerAccount = account;
	}

	@Override
	public void setCreateTime(long time) {
		this.createTime = time;
	}

	@Override
	public Game currentGame() {
		return this.currentGame;
	}

	@Override
	public Player playerReady(String account) {
		Player player = this.players.getPlayerByAccount(account);
		if (player != null) {
			player.ready();
		}
		return player;
	}

	@Override
	public Player playerUnReady(String account) {
		Player player = this.players.getPlayerByAccount(account);
		if (player != null) {
			player.unReady();
		}
		return player;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public boolean isGaming() {
		// TODO Auto-generated method stub
		return this.gaming;
	}

	@Override
	public Channel getChannel() {
		return this.channel;
	}

	@Override
	public RoomInfo getRoomInfo() {
		return this.roomInfo;
	}

	@Override
	public int getActivePlayers() {
		return this.players.getActivePlayers();
	}

	@Override
	public void gameStart() {
		// TODO Auto-generated method stub
		logger.info("### gameStart roomId[" + this.getId() + "]");
		if (this.order == 0) {
			this.players.ajustPlayerIndex();
		}
		Game lastGame = (listGame.size() == 0 ? null : listGame.get(listGame
				.size() - 1));
		Game game = this.channel.createGame(roomInfo, lastGame, this);
		this.currentGame = game;
		this.gaming = true;
		this.order++;
		game.setRule(this.channel.getRule(roomInfo));
		game.setPlayers(this.players);
		game.setActions(this.channel.createActions());
		game.setAllCards(this.channel.createAllCards(this.roomInfo));
		game.setRoom(this);
		game.start();

	}

	@Override
	public void gameStop(final Game game) {
		// TODO Auto-generated method stub
		logger.debug("### gameStop roomId[" + this.getId() + "]");
		for (int i = 0; i < players.getActivePlayers(); i++) {
			Player player = players.getPlayerByIndex(i);
			player.unReady();
		}
		listGame.add(game);
		playerScores.addPlayerScores(game.getPlayerScores());

		currentGame = null;
		gaming = false;
	}

	@Override
	public Player getPlayerByAccount(String account) {
		return this.players.getPlayerByAccount(account);
	}

	@Override
	public int getPlayerScoreByAccount(String account) {
		return this.playerScores.getScore(account);
	}

	@Override
	public String toString() {
		String strPlayers = "";
		for (int i = 0; i < this.players.getActivePlayers(); i++) {
			Player player = this.players.getPlayerByIndex(i);
			strPlayers += "index[" + i + "]order[" + player.getPlayerOrder()
					+ "]" + "name[" + player.getAccount() + "]" + "ready["
					+ player.isReady() + "]     ";
		}

		String str = "------------------room start------------------\r\n";
		str += ("### id[" + this.id + "]\r\n");
		str += "### roomInfo[" + this.roomInfo + "]\r\n";
		str += "### playerScores[" + this.playerScores + "]\r\n";
		str += "### createPlayer[" + this.createPlayerAccount + "]\r\n";
		str += "### gaming[" + this.gaming + "]\r\n";
		str += "### order [" + this.order + "]\r\n";
		str += "### players " + strPlayers + "\r\n";
		str += "----------------------- room end --------------------\r\n";
		return str;
	}

	@Override
	public String[] playerAccounts() {
		// TODO Auto-generated method stub
		return this.players.playerAccounts();
	}

	@Override
	public int getUniqueId() {
		return this.uniqueId;
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map mapRet = new HashMap();
		List listPlayers = new ArrayList();
		mapRet.put("order", this.order);
		mapRet.put("users", listPlayers);
		mapRet.put("roomId", this.id);
		mapRet.put("createPlayer", this.createPlayerAccount);
		mapRet.put("createTime", this.createTime);
		long leftTime = 1200000 - (System.currentTimeMillis() - this.createTime);
		mapRet.put("leftTime", leftTime);
		mapRet.put("roomInfo", this.roomInfo);
		for (int i = 0; i < this.players.getActivePlayers(); i++) {
			Player player = this.players.getPlayerByIndex(i);
			String account = player.getAccount();
			Map mapPlayer = new HashMap();
			mapPlayer.put("dir", player.getPlayerOrder());
			mapPlayer.put("ready", player.isReady());
			mapPlayer.put("name", player.getAccount());
			mapPlayer.put("score",
					this.playerScores.getScore(player.getAccount()));
			listPlayers.add(mapPlayer);
		}
		return mapRet;
	}

	@Override
	public void setUniqueId(int uniqueId) {
		// TODO Auto-generated method stub
		this.uniqueId = uniqueId;
	}

	@Override
	public long getCreateTime() {
		// TODO Auto-generated method stub
		return this.createTime;
	}

	@Override
	public Player getPlayerByIndex(int i) {
		// TODO Auto-generated method stub
		return this.players.getPlayerByIndex(i);
	}

	@Override
	public Player addPlayer(Player player) {
		// TODO Auto-generated method stub
		return this.players.addPlayer(player);
	}

	@Override
	public Player getPlayerByOrder(int order) {
		// TODO Auto-generated method stub
		return this.players.getPlayerByOrder(order);
	}

	@Override
	public Player nextPlayer(Player player) {
		// TODO Auto-generated method stub
		return this.players.nextPlayer(player);
	}

	@Override
	public void ajustPlayerIndex() {
		// TODO Auto-generated method stub
		this.players.ajustPlayerIndex();
	}

	@Override
	public String getEndReason() {
		// TODO Auto-generated method stub
		return this.endReason;
	}

	@Override
	public void setEndReason(String endReason) {
		// TODO Auto-generated method stub
		this.endReason = endReason;
	}

	@Override
	public int requestDismiss(String account, boolean dismiss) {
		if (dismiss) {
			this.listDismiss.add(account);
		} else {
			this.listDismiss.clear();
		}
		return this.listDismiss.size();
	}

	@Override
	public List<String> getDismissPlayers() {
		return this.listDismiss;
	}
}
