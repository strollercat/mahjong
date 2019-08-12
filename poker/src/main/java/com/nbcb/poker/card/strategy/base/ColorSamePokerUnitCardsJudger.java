package com.nbcb.poker.card.strategy.base;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;

public class ColorSamePokerUnitCardsJudger implements PokerUnitCardsJudger {

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		if (pokerCards == null || pokerCards.size() == 0
				|| pokerCards.size() == 1) {
			return null;
		}
		PokerCard pc = (PokerCard) pokerCards.getCard(0);

		for (int i = 1; i < pokerCards.size(); i++) {
			PokerCard tmpPc = (PokerCard) pokerCards.getCard(i);
			if (tmpPc.getPokerColor() != pc.getPokerColor()) {
				return null;
			}
		}
		return new ColorSamePokerUnitCards(pokerCards, pc.getPokerColor());
	}


	public static void main(String[] args) {
		ColorSamePokerUnitCardsJudger j = new ColorSamePokerUnitCardsJudger();

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.CAOHUA, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		// System.out.println(pcs.chooseAllNotSamePokerNumberCardsBySize(2));
		System.out.println(j.judge(pcs));

	}

}
