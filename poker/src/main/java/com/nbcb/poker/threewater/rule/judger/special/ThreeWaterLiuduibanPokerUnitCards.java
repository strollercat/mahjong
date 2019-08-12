package com.nbcb.poker.threewater.rule.judger.special;

import java.util.List;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterLiuduibanPokerUnitCards extends PokerUnitCards {

	private List<PokerCards> listCards;
	private PokerCard pc;

	public PokerCard getPc() {
		return pc;
	}

	public ThreeWaterLiuduibanPokerUnitCards(PokerCards pcs,
			List<PokerCards> listCards, PokerCard pc) {
		super(pcs);
		this.listCards = listCards;
		this.pc = pc;
	}

	public List<PokerCards> getListCards() {
		return listCards;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "六对";
	}

	@Override
	public String toString() {
		return "listCards[" + listCards + "]pc["+this.pc+"]" + super.toString();
	}

}
