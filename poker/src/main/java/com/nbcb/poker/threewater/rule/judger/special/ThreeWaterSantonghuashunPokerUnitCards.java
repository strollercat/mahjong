package com.nbcb.poker.threewater.rule.judger.special;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCards;

public class ThreeWaterSantonghuashunPokerUnitCards extends
		ThreeWaterSanshunziPokerUnitCards {

	public ThreeWaterSantonghuashunPokerUnitCards(PokerCards pcs,
			List<SequencePokerUnitCards> listCards) {
		super(pcs, listCards);
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "三同花顺";
	}
	
	

}
