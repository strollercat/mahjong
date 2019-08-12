package com.nbcb.poker.card.strategy.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;

public class SequencePokerUnitCardsJudger implements PokerUnitCardsJudger {

	/**
	 * 忽略花色
	 */
	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {

		if (pokerCards == null || pokerCards.size() == 0
				|| pokerCards.size() == 1 || pokerCards.size() > 14) {
			return null;
		}

		// if (pokerCards.size() == 1) {
		// PokerCard pc = (PokerCard) pokerCards.getCard(0);
		// return new SequencePokerUnitCards(pokerCards, pc.getPokerNumber(),
		// pc.getPokerNumber());
		// }

		if (pokerCards.size() == 14) {
			if (null != pokerCards.findSingleNumberSequencePokerCards(1, 14)) {
				return new SequencePokerUnitCards(pokerCards, 1, 14);
			}
			return null;
		}

		Set<Integer> set = pokerCards.findAllPokerNumber();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(set);
		Collections.sort(list);

		int min = list.get(0);
		int max = list.get(list.size() - 1);
		// if (min == 1) {
		// max = 14;
		// min = list.get(1);
		// } else {
		// max = list.get(list.size() - 1);
		// }
		PokerCards tmpPcs = pokerCards.findSingleNumberSequencePokerCards(min,
				max);
		if (tmpPcs != null && tmpPcs.size() == pokerCards.size()) {
			return new SequencePokerUnitCards(pokerCards, min, max);
		}

		if (min == 1) {
			max = 14;
			min = list.get(1);
		}
		tmpPcs = pokerCards.findSingleNumberSequencePokerCards(min, max);
		if (tmpPcs != null && tmpPcs.size() == pokerCards.size()) {
			return new SequencePokerUnitCards(pokerCards, min, max);
		}

		return null;
	}


	public static void main(String[] args) {
		SequencePokerUnitCardsJudger j = new SequencePokerUnitCardsJudger();

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 11));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 12));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 13));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 9));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 7));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 8));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 5));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 1));
//		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
//				PokerCard.Color.HONGTAO, 1));

		// System.out.println(pcs.chooseAllNotSamePokerNumberCardsBySize(2));
		System.out.println(j.judge(pcs));

	}

}
