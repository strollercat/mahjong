package com.nbcb.poker.card.strategy.base;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class NumberSamePokerUnitCards extends PokerUnitCards {

	private int pokerNumber;

	public int getPokerNumber() {
		return pokerNumber;
	}

	public NumberSamePokerUnitCards(PokerCards pokerCards,
			int pokerNumber) {
		super(pokerCards);
		this.pokerNumber = pokerNumber;
	}

	@Override
	public String toString() {
		return "pokerNumber[" + pokerNumber + "]" + super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "numberSamePucs";
	}

}
