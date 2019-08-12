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
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterSantiaoPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterSantiaoPokerUnitCardsFinder;

public class ThreeWaterDuiziPokerUnitCardsFinder implements
		PokerUnitCardsFinder {

	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		// TODO Auto-generated method stub
		List<PokerUnitCards> list = numberSamePokerUnitCardsFinder.find(
				pokerCards, 2);
		if (list == null || list.size() == 0) {
			return null;
		}

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();
		for (PokerUnitCards pucs : list) {
			listRet.addAll(toDuizi(pucs, pokerCards));
		}
		return listRet;
	}

	private List<PokerUnitCards> toDuizi(PokerCards duiziPcs, PokerCards pcs) {

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();

		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(pcs.toArray());
		copyPcs.removeCards(duiziPcs.toArray());

		List<PokerCards> list = ThreeWaterPokerCardsUtil.getNotSameCardsList(
				copyPcs, 3);
		for (PokerCards tmpPcs : list) {
			PokerCards finalPcs = new PokerCards();
			finalPcs.addTailCards(tmpPcs.toArray());
			finalPcs.addTailCards(duiziPcs.toArray());
			if (null != numberSamePokerUnitCardsFinder.find(finalPcs, 3)) {
				continue;
			}
			if (null != numberSamePokerUnitCardsFinder.find(finalPcs, 4)) {
				continue;
			}
			listRet.add(new ThreeWaterDuiziPokerUnitCards(finalPcs, duiziPcs,
					tmpPcs));
		}
		return listRet;
	}

	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterDuiziPokerUnitCardsFinder j = new ThreeWaterDuiziPokerUnitCardsFinder();
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
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 12));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 13));
		//
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 2));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 3));

		System.out.println(j.find(pcs, 5));
	}

}
