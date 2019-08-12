package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterDuiziPokerUnitCards extends PokerUnitCards {

	private PokerCards duizi;
	private PokerCards wulong;

	public ThreeWaterDuiziPokerUnitCards(PokerCards pcs, PokerCards duizi,
			PokerCards wulong) {
		super(pcs);
		this.duizi = duizi;
		this.wulong = wulong;
	}

	public PokerCards getDuizi() {
		return duizi;
	}

	public PokerCards getWulong() {
		return wulong;
	}

	@Override
	public String toString() {
		return "duizi[" + duizi + "]" + "wulong[" + wulong + "]"
				+ super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "对子";
	}

}
