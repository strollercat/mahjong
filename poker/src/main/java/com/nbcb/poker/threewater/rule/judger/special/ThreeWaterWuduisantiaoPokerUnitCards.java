package com.nbcb.poker.threewater.rule.judger.special;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterWuduisantiaoPokerUnitCards extends PokerUnitCards {

	private List<PokerCards> listTwo;
	private List<PokerCards> listThree;

	public List<PokerCards> getListTwo() {
		return listTwo;
	}

	public List<PokerCards> getListThree() {
		return listThree;
	}

	public ThreeWaterWuduisantiaoPokerUnitCards(PokerCards pcs,
			List<PokerCards> listTwo, List<PokerCards> listThree) {
		super(pcs);
		this.listTwo = listTwo;
		this.listThree = listThree;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "五对三条";
	}

	@Override
	public String toString() {
		return "listTwo[" + listTwo + "]listThree[" + listThree + "]"
				+ super.toString();
	}
}
