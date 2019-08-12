package com.nbcb.poker.threewater.rule.judger.special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCards;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.threewater.helper.ThreeWaterPokerCardsRoads;

/**
 * 
 * @author lele 三同花 三堆都是同花
 * 
 */
public class ThreeWaterSantonghuaPokerUnitCardsJudger implements
		PokerUnitCardsJudger {

	private PokerUnitCardsJudger colorSamePokerUnitCardsJudger;

	public void setColorSamePokerUnitCardsJudger(
			PokerUnitCardsJudger colorSamePokerUnitCardsJudger) {
		this.colorSamePokerUnitCardsJudger = colorSamePokerUnitCardsJudger;
	}

	private ColorSamePokerUnitCards getColorSamePokerUnitCards(
			PokerCard.Color color, List<PokerCard> list, int start, int size) {
		PokerCards pcs = new PokerCards();
//		System.out.println(start + " " + size);
		for (int i = start; i < start + size; i++) {
			pcs.addTailCard(list.get(i));
		}
		ColorSamePokerUnitCards csPucs = new ColorSamePokerUnitCards(pcs, color);
		return csPucs;
	}

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {

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

		int colorSize = 0;
		for (PokerCard.Color color : map.keySet()) {
			if (map.get(color) != null) {
				colorSize++;
			}
		}
		if (colorSize == 1) {
			return null;
		}
		if (colorSize == 4) {
			return null;
		}
		List<ColorSamePokerUnitCards> listCards = new ArrayList<ColorSamePokerUnitCards>();
		if (colorSize == 2) {
			for (PokerCard.Color color : map.keySet()) {
				if (map.get(color).size() != 3 && map.get(color).size() != 10) {
					return null;
				}
				List<PokerCard> list = map.get(color);
				if (list.size() == 10) {
//					System.out.println("10");
					ColorSamePokerUnitCards csPucs1 = this
							.getColorSamePokerUnitCards(color, list, 0, 5);
					ColorSamePokerUnitCards csPucs2 = this
							.getColorSamePokerUnitCards(color, list, 5, 5);
					listCards.add(csPucs1);
					listCards.add(csPucs2);
				} else {
//					System.out.println("3");
//					System.out.println(list.size());
					ColorSamePokerUnitCards csPucs = this
							.getColorSamePokerUnitCards(color, list, 0,
									list.size());
					listCards.add(csPucs);
				}
			}
		}
		if (colorSize == 3) {
			for (PokerCard.Color color : map.keySet()) {
				if (map.get(color).size() != 3 && map.get(color).size() != 5
						&& map.get(color).size() != 5) {
					return null;
				}
				List<PokerCard> list = map.get(color);
				ColorSamePokerUnitCards csPucs = this
						.getColorSamePokerUnitCards(color, list, 0, list.size());
				listCards.add(csPucs);
			}
		}

		return new ThreeWaterSantonghuaPokerUnitCards(pokerCards, listCards);
	}

	public static void main(String[] args) {

		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		ThreeWaterSantonghuaPokerUnitCardsJudger j = new ThreeWaterSantonghuaPokerUnitCardsJudger();
		j.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();

		

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 5));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 6));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 9));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 8));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 7));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 11));
		
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 5));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 6));

		System.out.println(j.judge(pcs));
	}

}
