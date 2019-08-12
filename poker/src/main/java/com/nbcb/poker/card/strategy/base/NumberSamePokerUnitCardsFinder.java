package com.nbcb.poker.card.strategy.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;

public class NumberSamePokerUnitCardsFinder implements PokerUnitCardsFinder {

	/**
	 * 剔除pokerNumber重复的
	 */
	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {

		if (size <= 1) {
			return null;
		}

		if (pokerCards == null || pokerCards.size() < size) {
			return null;
		}
		List<PokerUnitCards> list = new ArrayList<PokerUnitCards>();

		Set<Integer> set = pokerCards.findAllPokerNumber();
		for (Integer pk : set) {
			PokerCards pcs = pokerCards.findPokerCardsByPokerNumber(pk);
			if (pcs.size() >= size) {
				pcs.removeTailCards(pcs.size() - size);
				list.add(new NumberSamePokerUnitCards(pcs, pk));
			}
		}
		return list.size() == 0 ? null : list;
	}

	public static void main(String[] args) {
		NumberSamePokerUnitCardsFinder j = new NumberSamePokerUnitCardsFinder();

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
		System.out.println(j.find(pokerAllCards, 4));

	}

}
