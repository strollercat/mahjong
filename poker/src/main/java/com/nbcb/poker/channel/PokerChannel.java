package com.nbcb.poker.channel;

import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.GameInfo;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.rule.Rule;
import com.nbcb.core.server.AbstractChannel;
import com.nbcb.core.user.Player;
import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.game.PokerGame;
import com.nbcb.poker.rule.PokerRule;
import com.nbcb.poker.user.PokerPlayer;

public class PokerChannel extends AbstractChannel {

	private PokerRule pokerRule;

	public void setPokerRule(PokerRule pokerRule) {
		this.pokerRule = pokerRule;
	}

	@Override
	public Player createPlayer(String account) {
		return new PokerPlayer(account);
	}

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame, Room room) {
		// TODO Auto-generated method stub
		GameInfo gameInfo = new GameInfo();
		gameInfo.setPlayerNum(roomInfo.getPlayerNum());
		gameInfo.setTimeOut(-1);
		return new PokerGame(gameInfo);
	}

	@Override
	public Rule getRule(RoomInfo roomInfo) {
		return this.pokerRule;
	}

	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();
		return pokerAllCards;
	}

}
