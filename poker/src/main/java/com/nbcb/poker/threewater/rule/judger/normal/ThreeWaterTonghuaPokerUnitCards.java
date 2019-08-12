package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterTonghuaPokerUnitCards extends PokerUnitCards {

	private PokerCard.Color color;

	public ThreeWaterTonghuaPokerUnitCards(PokerCards pcs, PokerCard.Color color) {
		super(pcs);
		this.color = color;
	}

	public PokerCard.Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "color[" + this.color + "]" + super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "同花";
	}

}
