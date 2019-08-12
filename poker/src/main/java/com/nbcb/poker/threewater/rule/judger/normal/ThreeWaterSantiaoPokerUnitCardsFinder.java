package com.nbcb.poker.threewater.rule.judger.normal;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;
import com.nbcb.poker.threewater.helper.ThreeWaterPokerCardsUtil;

public class ThreeWaterSantiaoPokerUnitCardsFinder implements
		PokerUnitCardsFinder {

	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	private List<PokerCards> getWulong2(PokerCards pcs) {
		List<PokerCards> list = new ArrayList<PokerCards>();
		for (int i = 0; i < pcs.size(); i++) {
			for (int j = 0; j < pcs.size(); j++) {
				if (i == j) {
					continue;
				}
				PokerCard pc1 = (PokerCard) pcs.getCard(i);
				PokerCard pc2 = (PokerCard) pcs.getCard(j);
				if (pc1.getPokerNumber() == pc2.getPokerNumber()) {
					continue;
				}
				PokerCards tmpPcs = new PokerCards();
				tmpPcs.addTailCard(pc1);
				tmpPcs.addTailCard(pc2);
				if (tmpPcs.containedPokerCardsByPokerNumber(list)) {
					continue;
				}
				list.add(tmpPcs);

			}
		}
		return list;
	}

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		// TODO Auto-generated method stub
		List<PokerUnitCards> list = numberSamePokerUnitCardsFinder.find(
				pokerCards, 3);
		if (list == null || list.size() == 0) {
			return null;
		}

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();
		for (PokerUnitCards pucs : list) {
			listRet.addAll(toSandui(pucs, pokerCards));
		}
		return listRet;
	}

	private List<PokerUnitCards> toSandui(PokerCards sanduiPcs, PokerCards pcs) {

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();

		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(pcs.toArray());
		copyPcs.removeCards(sanduiPcs.toArray());

		List<PokerCards> list = ThreeWaterPokerCardsUtil.getNotSameCardsList(
				copyPcs, 2);
		for (PokerCards tmpPcs : list) {
			PokerCards finalPcs = new PokerCards();
			finalPcs.addTailCards(tmpPcs.toArray());
			finalPcs.addTailCards(sanduiPcs.toArray());
			if (null != numberSamePokerUnitCardsFinder.find(finalPcs, 4)) {
				continue;
			}
			listRet.add(new ThreeWaterSantiaoPokerUnitCards(finalPcs,
					sanduiPcs, tmpPcs));
		}
		return listRet;
	}
	
	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterSantiaoPokerUnitCardsFinder j = new ThreeWaterSantiaoPokerUnitCardsFinder();
		j.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 7));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 7));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 8));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 8));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 8));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 9));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 11));
//		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
//				PokerCard.Color.FANGKUAI, 12));
//		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
//				PokerCard.Color.FANGKUAI, 13));
//
//		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
//				PokerCard.Color.FANGKUAI, 1));
//		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
//				PokerCard.Color.FANGKUAI, 2));
//		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
//				PokerCard.Color.FANGKUAI, 3));

		System.out.println(j.find(pcs, 5));
	}


}
