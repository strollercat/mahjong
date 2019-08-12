package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterLiangduiPokerUnitCards extends PokerUnitCards {

	private PokerCards pcs1;
	private PokerCards pcs2;
	private PokerCard pc;

	public ThreeWaterLiangduiPokerUnitCards(PokerCards pcs, PokerCards pcs1,
			PokerCards pcs2, PokerCard pc) {
		super(pcs);
		this.pcs1 = pcs1;
		this.pcs2 = pcs2;
		this.pc = pc;
	}

	public PokerCards getPcs1() {
		return pcs1;
	}

	public PokerCards getPcs2() {
		return pcs2;
	}

	public PokerCard getPc() {
		return pc;
	}

	@Override
	public String toString() {
		return "pcs1[" + pcs1 + "]pcs2[" + pcs2 + "]pc[" + pc + "]"
				+ super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "两对";
	}

}
