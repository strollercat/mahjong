package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterTiezhiPokerUnitCards extends PokerUnitCards {

	private PokerCards fourPcs;
	private PokerCard pc;

	public ThreeWaterTiezhiPokerUnitCards(PokerCards pcs, PokerCards fourPcs,
			PokerCard pc) {
		super(pcs);
		this.fourPcs = fourPcs;
		this.pc = pc;
	}

	public PokerCards getFourPcs() {
		return fourPcs;
	}

	public PokerCard getPc() {
		return pc;
	}

	@Override
	public String toString() {
		return "fourPcs[" + fourPcs + "]pc[" + pc + "]" + super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "铁支";
	}

}
