package com.nbcb.majiang.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.card.Card;

public class MajiangInnerCards extends MajiangCards {

	public List<MajiangAnGangUnitCards> findMajiangAnGangUnitCards() {
		MajiangInnerCards otherCards = new MajiangInnerCards();
		otherCards.addTailCards(this.toArray());
		List<MajiangAnGangUnitCards> list = new ArrayList<MajiangAnGangUnitCards>();
		this.findMajiangAnGangUnitCards(otherCards, list);
		return list.size() == 0 ? null : list;
	}

	private void findMajiangAnGangUnitCards(MajiangCards mjCards,
			List<MajiangAnGangUnitCards> list) {

		Map<String, Object> map = new HashMap();
		Card[] arrCards = mjCards.toArray();
		for (Card c : arrCards) {
			Card[] mjRetCards = mjCards.findCardsByType(c.getType());
			if (mjRetCards != null && mjRetCards.length == 4) {
				MajiangCard mc = (MajiangCard) mjRetCards[0];
				if (mc.isBaida()) {
					continue;
				}
				if (map.get(mc.getType()) != null) {
					continue;
				}
				map.put(mc.getType(), new Object());
				list.add(new MajiangAnGangUnitCards(
						(MajiangCard) mjRetCards[0],
						(MajiangCard) mjRetCards[1],
						(MajiangCard) mjRetCards[2],
						(MajiangCard) mjRetCards[3]));
			}
		}
	}
}
