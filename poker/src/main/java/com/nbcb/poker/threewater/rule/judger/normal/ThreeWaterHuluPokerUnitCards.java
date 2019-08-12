package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterHuluPokerUnitCards extends PokerUnitCards {

	private PokerCards threePcs;
	private PokerCards twoPcs;

	public ThreeWaterHuluPokerUnitCards(PokerCards pcs, PokerCards threePcs,
			PokerCards twoPcs) {
		super(pcs);
		this.threePcs = threePcs;
		this.twoPcs = twoPcs;
	}

	public PokerCards getThreePcs() {
		return threePcs;
	}

	public PokerCards getTwoPcs() {
		return twoPcs;
	}

	@Override
	public String toString() {
		return "twoPcs[" + twoPcs + "]threePcs[" + threePcs + "]"
				+ super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "葫芦";
	}

}
