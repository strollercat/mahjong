package com.nbcb.poker.card.strategy.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.card.Card;
import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;

public class ColorSamePokerUnitCardsFinder implements PokerUnitCardsFinder {

	private List<ColorSamePokerUnitCards> choose(List<PokerCard> list, int size) {

		List<ColorSamePokerUnitCards> listRet = new ArrayList<ColorSamePokerUnitCards>();

		PokerCards oriPcs = new PokerCards();
		for (PokerCard pc : list) {
			oriPcs.addTailCard(pc);
		}
		this.chooseInner(listRet, new ArrayList<PokerCards>(), oriPcs,
				new PokerCards(), size);
		return listRet;
	}

	private void chooseInner(List<ColorSamePokerUnitCards> list,
			List<PokerCards> listRoad, PokerCards oriPcs, PokerCards pcs,
			int size) {
		if (pcs.size() == size) {
			// if (pcs.containedPokerCardsByPokerNumberAndPokerColor(list)) {
			// return;
			// }
			PokerCard pc = (PokerCard) pcs.getCard(0);
			list.add(new ColorSamePokerUnitCards(pcs, pc.getPokerColor()));
			return;
		}

		Card card[] = oriPcs.toArray();
		for (Card c : card) {
			oriPcs.removeCard(c);
			pcs.addTailCard(c);
			if (pcs.containedPokerCardsByPokerNumberAndPokerColor(listRoad)) {
				oriPcs.addTailCard(c);
				pcs.removeCard(c);
				continue;
			}
			PokerCards copyPcs = new PokerCards();
			copyPcs.addTailCards(pcs.toArray());
			listRoad.add(copyPcs);

			chooseInner(list, listRoad, oriPcs, pcs, size);
			oriPcs.addTailCard(c);
			pcs.removeCard(c);
		}
	}

	/**
	 * 剔除完全一模一样的
	 */
	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		if (size <= 1) {
			return null;
		}
		if (pokerCards.size() < size) {
			return null;
		}
		Map<PokerCard.Color, List<PokerCard>> map = new HashMap<PokerCard.Color, List<PokerCard>>();
		for (int i = 0; i < pokerCards.size(); i++) {
			PokerCard pc = (PokerCard) pokerCards.getCard(i);
			List<PokerCard> list = map.get(pc.getPokerColor());
			if (list == null) {
				list = new ArrayList<PokerCard>();
			}
			list.add(pc);
			map.put(pc.getPokerColor(), list);
		}
		// System.out.println(map);
		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();

		for (PokerCard.Color color : map.keySet()) {
			List<PokerCard> list = map.get(color);
			if (list == null || list.size() < size) {
				continue;
			}
			List tmpList = this.choose(list, size);
			if (tmpList == null || tmpList.size() == 0) {
				continue;
			}
			listRet.addAll(tmpList);
		}
		return listRet.size() == 0 ? null : listRet;
	}

	public static void main(String[] args) {
		ColorSamePokerUnitCardsFinder j = new ColorSamePokerUnitCardsFinder();

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		// pokerAllCards.removeTailCards(2);
		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 4));

		// System.out.println(pcs.chooseAllNotSamePokerNumberCardsBySize(2));
		System.out.println(j.find(pokerAllCards, 5));
		System.out.println(pcs);

	}

}
