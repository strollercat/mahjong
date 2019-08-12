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

public class ThreeWaterLiangduiPokerUnitCardsFinder implements
		PokerUnitCardsFinder {

	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	private List<PokerUnitCards> getList(PokerCards pcs1, PokerCards pcs2,
			PokerCards pcs) {

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();

		PokerCard pc1 = (PokerCard) pcs1.getHeadCard();
		PokerCard pc2 = (PokerCard) pcs2.getHeadCard();

		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(pcs.toArray());
		copyPcs.removeCards(pcs1.toArray());
		copyPcs.removeCards(pcs2.toArray());
		for (int i = 0; i < copyPcs.size(); i++) {
			PokerCard pc = (PokerCard) copyPcs.getCard(i);
			if (pc.getPokerNumber() == pc1.getPokerNumber()
					|| pc.getPokerNumber() == pc2.getPokerNumber()) {
				continue;
			}
			PokerCards tmpPcs = new PokerCards();
			tmpPcs.addTailCards(pcs1.toArray());
			tmpPcs.addTailCards(pcs2.toArray());
			tmpPcs.addTailCard(pc);
			ThreeWaterLiangduiPokerUnitCards twPucs = new ThreeWaterLiangduiPokerUnitCards(
					tmpPcs, pcs1, pcs2, pc);
			if (twPucs.containedPokerCardsByPokerNumber(listRet)) {
				continue;
			}
			listRet.add(twPucs);
		}
		return listRet;
	}

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		List<PokerUnitCards> list1 = this.numberSamePokerUnitCardsFinder.find(
				pokerCards, 2);
		List<PokerUnitCards> list2 = this.numberSamePokerUnitCardsFinder.find(
				pokerCards, 2);

		if (list1 == null) {
			return null;
		}
		if (list2 == null) {
			return null;
		}

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();
		for (PokerUnitCards pucs1 : list1) {
			for (PokerUnitCards pucs2 : list2) {
				PokerCard pc1 = (PokerCard) pucs1.getHeadCard();
				PokerCard pc2 = (PokerCard) pucs2.getHeadCard();
				if (pc1.getPokerNumber() == pc2.getPokerNumber()) {
					continue;
				}
				List<PokerUnitCards> listTmp = this.getList(pucs1, pucs2,
						pokerCards);
				if (listTmp == null || listTmp.size() == 0) {
					continue;
				}
				listRet.addAll(listTmp);
			}
		}
		return listRet;
	}
	
	

	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterLiangduiPokerUnitCardsFinder j = new ThreeWaterLiangduiPokerUnitCardsFinder();
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
				PokerCard.Color.FANGKUAI, 6));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 9));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 11));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 12));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 13));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 3));

		System.out.println(j.find(pcs, 5));
	}

}
