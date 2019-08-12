package com.nbcb.poker.card.strategy.base;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ColorSamePokerUnitCards extends PokerUnitCards {

	private PokerCard.Color color;

	public PokerCard.Color getColor() {
		return color;
	}

	public ColorSamePokerUnitCards(PokerCards pokerCards,
			PokerCard.Color color) {
		super(pokerCards);
		this.color = color;
	}

	@Override
	public String toString() {
		return "color[" + this.color + "]" + super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "colorSamePucs";
	}
}
