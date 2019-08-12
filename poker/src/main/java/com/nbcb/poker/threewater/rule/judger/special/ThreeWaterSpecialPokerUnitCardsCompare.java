package com.nbcb.poker.threewater.rule.judger.special;

import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsCompare;
import com.nbcb.poker.threewater.helper.ThreeWaterPokerCardsUtil;

public class ThreeWaterSpecialPokerUnitCardsCompare implements
		PokerUnitCardsCompare {

	@Override
	public int compare(PokerUnitCards pucs1, PokerUnitCards pucs2) {
		// TODO Auto-generated method stub
		int order1 = ThreeWaterPokerCardsUtil.getSpecialOrder(pucs1.getClass());
		int order2 = ThreeWaterPokerCardsUtil.getSpecialOrder(pucs2.getClass());

		if (order1 > order2) {
			return 1;
		} else if (order1 < order2) {
			return -1;
		} else {
			return 0;
		}

	}

}
