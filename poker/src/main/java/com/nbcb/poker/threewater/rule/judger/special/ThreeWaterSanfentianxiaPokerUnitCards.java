package com.nbcb.poker.threewater.rule.judger.special;

import java.util.List;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterSanfentianxiaPokerUnitCards extends PokerUnitCards {

	private List<PokerCards> listCards;
	private PokerCard pc;

	public List<PokerCards> getListCards() {
		return listCards;
	}

	public PokerCard getPc() {
		return pc;
	}

	public ThreeWaterSanfentianxiaPokerUnitCards(PokerCards pcs,
			List<PokerCards> listCards, PokerCard pc) {
		super(pcs);
		this.listCards = listCards;
		this.pc = pc;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "三分天下";
	}

	@Override
	public String toString() {
		return "listCards[" + listCards + "]pc[" + pc + "]" + super.toString();
	}

}
