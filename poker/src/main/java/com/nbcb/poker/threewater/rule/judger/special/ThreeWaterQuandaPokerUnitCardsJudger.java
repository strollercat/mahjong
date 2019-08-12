package com.nbcb.poker.threewater.rule.judger.special;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;

/**
 * 
 * @author lele 全大 全部为8-A的牌，不算花色
 * 
 */
public class ThreeWaterQuandaPokerUnitCardsJudger implements PokerUnitCardsJudger {

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		for (int i = 0; i < pokerCards.size(); i++) {
			PokerCard pc = (PokerCard) pokerCards.getCard(i);
			if (pc.getPokerNumber() == 1 || pc.getPokerNumber() >= 8) {
				continue;
			}
			return null;
		}
		return new ThreeWaterQuandaPokerUnitCards(pokerCards);
	}
	
	public static void main(String [] args){
		
	}

}
