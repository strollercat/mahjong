package com.nbcb.core.game;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.core.room.Room;
import com.nbcb.core.rule.Rule;
import com.nbcb.core.user.Player;
import com.nbcb.core.user.Players;

public abstract class AbstractGame implements Game {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractGame.class);

	private Room room;

	private int uniqueId;

	@Override
	public void setUniqueId(int id) {
		this.uniqueId = id;
	}

	@Override
	public int getUniqueId() {
		return this.uniqueId;
	}

	@Override
	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public Room getRoom() {
		return this.room;
	}

	protected Cards allCards;

	protected Players players;

	protected Rule rule;

	protected Actions actions;

	protected Actions legalActions;

	protected GameInfo gameInfo;

	protected PlayerScores playerScores;

	@Override
	public void setPlayerScores(PlayerScores playerScores) {
		this.playerScores = playerScores;
	}

	@Override
	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public AbstractGame(GameInfo gameInfo) {
		logger.debug("start to create AbstractGame");
		this.gameInfo = gameInfo;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getActivePlayers() {
		// TODO Auto-generated method stub
		return players.getActivePlayers();
	}

	@Override
	public Player addPlayer(Player player) {
		return players.addPlayer(player);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public GameInfo getGameInfo() {
		return this.gameInfo;
	}

	@Override
	public Player getPlayerByIndex(int i) {
		return players.getPlayerByIndex(i);
	}

	@Override
	public Player getPlayerByOrder(int order) {
		return players.getPlayerByOrder(order);
	}

	@Override
	public String toString() {
		String str = "";
		str += getGameInfo();
		str += "\r\n";
		str += players;
		str += "\r\n";
		return str;
	}

	@Override
	public Player nextPlayer(Player player) {
		// TODO Auto-generated method stub
		return players.nextPlayer(player);
	}

	@Override
	public void nextAction(Action action) {
		rule.next(this, action);
	}

	@Override
	public void setLegalActions(Actions actions) {
		this.legalActions = actions;
	}

	@Override
	public Actions getLegalActions() {
		return this.legalActions;
	}

	@Override
	public void addAction(Action action) {
		actions.addAction(action);
	}

	@Override
	public Action getLastAction() {
		return actions.getTailAction();
	}

	@Override
	public void setPlayers(Players players) {
		this.players = players;
	}

	@Override
	public void setActions(Actions actions) {
		this.actions = actions;
	}

	@Override
	public void setAllCards(Cards allCards) {
		this.allCards = allCards;
	}

	@Override
	public Player leavePlayer(String account) {
		return this.players.leavePlayer(account);
	}

	public Player getPlayerByAccount(String account) {
		return this.players.getPlayerByAccount(account);
	}

	@Override
	public Map format() {
		return null;
	}

	@Override
	public Cards getAllCards() {
		return this.allCards;
	}

	@Override
	public Actions getHistoryActions() {
		return this.actions;
	}

	@Override
	public PlayerScores getPlayerScores() {
		return this.playerScores;
	}

	@Override
	public String[] playerAccounts() {
		// TODO Auto-generated method stub
		return this.players.playerAccounts();
	}

	@Override
	public void ajustPlayerIndex() {
		// TODO Auto-generated method stub
		this.players.ajustPlayerIndex();
	}

}
