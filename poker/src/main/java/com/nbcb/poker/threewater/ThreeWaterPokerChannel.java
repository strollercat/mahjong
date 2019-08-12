package com.nbcb.poker.threewater;

import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.GameInfo;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.user.Player;
import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.channel.PokerChannel;

public class ThreeWaterPokerChannel extends PokerChannel {
	
	
	
	@Override
	public Player createPlayer(String account) {
		return new ThreeWaterPokerPlayer(account);
	}
	
	
	
	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame, Room room) {
		// TODO Auto-generated method stub
		GameInfo gameInfo = new GameInfo();
		gameInfo.setPlayerNum(roomInfo.getPlayerNum());
		gameInfo.setTimeOut(-1);
		return new ThreeWaterPokerGame(gameInfo);
	}


	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();
		pokerAllCards.removeTailCard();
		pokerAllCards.removeTailCard();
		return pokerAllCards;
	}
}
