package com.nbcb.poker.threewater.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;

public class ThreeWaterPokerCardsRoads {

	private List<PokerCards> listFirst = new ArrayList<PokerCards>();
	private Map<String, List<PokerCards>> mapPokerNumberSecond = new HashMap<String, List<PokerCards>>();
	private Map<String, List<PokerCards>> mapPokerSecond = new HashMap<String, List<PokerCards>>();

	private String getPokerNumberKey(PokerCards pcs) {
		ThreeWaterPokerCardsUtil.sortByPokerNumber(pcs);
		String key = "";
		for (int i = 0; i < pcs.size(); i++) {
			PokerCard pc = (PokerCard) pcs.getCard(i);
			key += pc.getPokerNumber() + "+";
		}
		return key;
	}

	private String getPokerKey(PokerCards pcs) {
		pcs.sort();
		String key = "";
		for (int i = 0; i < pcs.size(); i++) {
			PokerCard pc = (PokerCard) pcs.getCard(i);
			key += pc.getType() + "+";
		}
		return key;
	}

	public void addPokerCards(PokerCards pokerCards) {

		PokerCards copyPokerCards = new PokerCards();
		copyPokerCards.addTailCards(pokerCards.toArray());

		if (copyPokerCards.size() <= 3) {
			listFirst.add(copyPokerCards);
			return;
		}
		if (copyPokerCards.size() <= 8) {
			PokerCards firstPcs = ThreeWaterPokerCardsUtil
					.getPokerCardsByIndex(copyPokerCards, 0, 3);
			PokerCards secondPcs = ThreeWaterPokerCardsUtil
					.getPokerCardsByIndex(copyPokerCards, 3,
							copyPokerCards.size() - 3);
			String key = this.getPokerNumberKey(firstPcs);
			if (mapPokerNumberSecond.get(key) == null) {
				List<PokerCards> listPcs = new ArrayList<PokerCards>();
				listPcs.add(secondPcs);
				mapPokerNumberSecond.put(key, listPcs);
			} else {
				List<PokerCards> listPcs = mapPokerNumberSecond.get(key);
				listPcs.add(secondPcs);
			}

			key = this.getPokerKey(firstPcs);
			if (mapPokerSecond.get(key) == null) {
				List<PokerCards> listPcs = new ArrayList<PokerCards>();
				listPcs.add(secondPcs);
				mapPokerSecond.put(key, listPcs);
			} else {
				List<PokerCards> listPcs = mapPokerSecond.get(key);
				listPcs.add(secondPcs);
			}
			return;
		}
	}

	public boolean walkedByPokerNumber(PokerCards pokerCards) {

		if (pokerCards.size() <= 3) {
			if (pokerCards.containedPokerCardsByPokerNumber(listFirst)) {
				return true;
			}
			return false;
		}
		if (pokerCards.size() <= 8) {
			PokerCards firstPcs = ThreeWaterPokerCardsUtil
					.getPokerCardsByIndex(pokerCards, 0, 3);
			PokerCards secondPcs = ThreeWaterPokerCardsUtil
					.getPokerCardsByIndex(pokerCards, 3, pokerCards.size() - 3);

			String key = this.getPokerNumberKey(firstPcs);
			if (secondPcs
					.containedPokerCardsByPokerNumber(this.mapPokerNumberSecond
							.get(key))) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean walkedByPoker(PokerCards pokerCards) {

		if (pokerCards.size() <= 3) {
			if (pokerCards
					.containedPokerCardsByPokerNumberAndPokerColor(listFirst)) {
				return true;
			}
			return false;
		}
		if (pokerCards.size() <= 8) {
			PokerCards firstPcs = ThreeWaterPokerCardsUtil
					.getPokerCardsByIndex(pokerCards, 0, 3);
			PokerCards secondPcs = ThreeWaterPokerCardsUtil
					.getPokerCardsByIndex(pokerCards, 3, pokerCards.size() - 3);

			String key = this.getPokerKey(firstPcs);
			if (secondPcs
					.containedPokerCardsByPokerNumberAndPokerColor(this.mapPokerSecond
							.get(key))) {
				return true;
			}
			return false;
		}
		return false;
	}

}
