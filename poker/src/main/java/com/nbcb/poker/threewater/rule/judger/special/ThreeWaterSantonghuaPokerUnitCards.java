package com.nbcb.poker.threewater.rule.judger.special;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCards;

public class ThreeWaterSantonghuaPokerUnitCards extends PokerUnitCards {

	private List<ColorSamePokerUnitCards> listCards;

	public List<ColorSamePokerUnitCards> getListCards() {
		return listCards;
	}

	public ThreeWaterSantonghuaPokerUnitCards(PokerCards pokerCards,
			List<ColorSamePokerUnitCards> listCards) {
		super(pokerCards);
		this.listCards = listCards;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "三同花";
	}

	@Override
	public String toString() {
		return "listCards[" + listCards + "]" + super.toString();
	}

}
