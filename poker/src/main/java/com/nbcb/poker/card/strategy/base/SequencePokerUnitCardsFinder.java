package com.nbcb.poker.card.strategy.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;

public class SequencePokerUnitCardsFinder implements PokerUnitCardsFinder {

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		if (size <= 1) {
			return null;
		}
		if (pokerCards == null || pokerCards.size() < size) {
			return null;
		}
		PokerCards pcs = new PokerCards();
		pcs.addTailCards(pokerCards.toArray());
		pcs.sort();

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();
		Set<Integer> set = pcs.findAllPokerNumber();
		for (int pokerNumber : set) {
			PokerCards tmpPcs = pcs.findSingleNumberSequencePokerCards(
					pokerNumber, size - 1 + pokerNumber);
			if (tmpPcs == null || tmpPcs.size() == 0) {
				continue;
			}
			if (tmpPcs.containedPokerCardsByPokerNumber(listRet)) {
				continue;
			}
			listRet.add(new SequencePokerUnitCards(tmpPcs, pokerNumber, size
					- 1 + pokerNumber));
		}
		return listRet.size() == 0 ? null : listRet;
	}

	public static void main(String[] args) {
		SequencePokerUnitCardsFinder j = new SequencePokerUnitCardsFinder();

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		// PokerCards pcs = new PokerCards();
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HEITAO, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.CAOHUA, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 2));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HEITAO, 3));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		// System.out.println(pcs.chooseAllNotSamePokerNumberCardsBySize(2));
		System.out.println(j.find(pokerAllCards, 2));

	}

}
