package com.nbcb.poker.threewater.rule.judger.special;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCards;

public class ThreeWaterSanshunziPokerUnitCards extends PokerUnitCards {

	private List<SequencePokerUnitCards> listCards;

	public List<SequencePokerUnitCards> getListCards() {
		return listCards;
	}

	public ThreeWaterSanshunziPokerUnitCards(PokerCards pcs,
			List<SequencePokerUnitCards> listCards) {
		super(pcs);
		this.listCards = listCards;
	}

	@Override
	public String toString() {
		return "listCards[" + this.listCards + "]"+ super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "三顺子";
	}

}
