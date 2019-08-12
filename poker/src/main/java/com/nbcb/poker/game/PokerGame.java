package com.nbcb.poker.game;

import com.nbcb.core.game.DefaultGame;
import com.nbcb.core.game.GameInfo;
import com.nbcb.poker.card.PokerBlackCards;

public class PokerGame extends DefaultGame {

	protected PokerBlackCards pokerBlackCards;
	
	public PokerBlackCards getPokerBlackCards() {
		return pokerBlackCards;
	}

	public PokerGame(GameInfo gameInfo) {
		super(gameInfo);
		// TODO Auto-generated constructor stub
	}
	
	public void stop() {
		this.getRoom().gameStop(this);
	}

}
