package com.nbcb.poker.card.strategy.base;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;

public class ListPokerUnitCardsJudger implements PokerUnitCardsJudger {
	
	private List<PokerUnitCardsJudger> listPokerUnitCardsJudger;
	
	
	public void setListPokerUnitCardsJudger(
			List<PokerUnitCardsJudger> listPokerUnitCardsJudger) {
		this.listPokerUnitCardsJudger = listPokerUnitCardsJudger;
	}
	
	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		for(PokerUnitCardsJudger judger:listPokerUnitCardsJudger){
			PokerUnitCards pcs = judger.judge(pokerCards);
			if(pcs!=null)
				return pcs;
		}
		return null;
	}

}
