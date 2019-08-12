package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterSantiaoPokerUnitCards extends PokerUnitCards {

	private PokerCards threePcs;
	private PokerCards wulong;

	public ThreeWaterSantiaoPokerUnitCards(PokerCards pcs, PokerCards threePcs,
			PokerCards wulong) {
		super(pcs);
		this.threePcs = threePcs;
		this.wulong = wulong;
	}

	public PokerCards getThreePcs() {
		return threePcs;
	}

	public PokerCards getWulong() {
		return wulong;
	}

	@Override
	public String toString() {
		return "threePcs[" + threePcs + "]wulong[" + wulong + "]"
				+ super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "三条";
	}

}
