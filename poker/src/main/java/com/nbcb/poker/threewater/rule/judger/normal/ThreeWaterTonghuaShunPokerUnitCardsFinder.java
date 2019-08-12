package com.nbcb.poker.threewater.rule.judger.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCards;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

public class ThreeWaterTonghuaShunPokerUnitCardsFinder implements
		PokerUnitCardsFinder {

	private SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder;

	public void setSequencePokerUnitCardsFinder(
			SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder) {
		this.sequencePokerUnitCardsFinder = sequencePokerUnitCardsFinder;
	}

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		// TODO Auto-generated method stub

		if (pokerCards.size() < 5) {
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

		List<PokerUnitCards> listRet = new ArrayList<PokerUnitCards>();

		for (PokerCard.Color color : map.keySet()) {
			List<PokerCard> list = map.get(color);
			if (list == null || list.size() < 5) {
				continue;
			}
			List tmpList = sequencePokerUnitCardsFinder.find(
					this.toPokerCards(list), 5);
			if (tmpList == null || tmpList.size() == 0) {
				continue;
			}
			listRet.addAll(tmpList);
		}

		if (listRet.size() == 0) {
			return null;
		}

		List<PokerUnitCards> listRet1 = new ArrayList<PokerUnitCards>();

		for (PokerUnitCards pucs : listRet) {
			listRet1.add(this
					.toThreeWaterTonghuashunPokerUnitCards((SequencePokerUnitCards) pucs));

		}
		return listRet1;
	}

	private ThreeWaterTonghuaShunPokerUnitCards toThreeWaterTonghuashunPokerUnitCards(
			SequencePokerUnitCards spucs) {
		PokerCard pc = (PokerCard) spucs.getHeadCard();
		return new ThreeWaterTonghuaShunPokerUnitCards(spucs, spucs,
				pc.getPokerColor());

	}

	private PokerCards toPokerCards(List<PokerCard> list) {
		PokerCards pcs = new PokerCards();
		for (PokerCard pc : list) {
			pcs.addTailCard(pc);
		}
		return pcs;
	}

	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterTonghuaShunPokerUnitCardsFinder j = new ThreeWaterTonghuaShunPokerUnitCardsFinder();
		j.setSequencePokerUnitCardsFinder(sequencePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		// PokerCards pcs = new PokerCards();
		//
		//
		//
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 7));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 8));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 4));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 5));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 6));
		//
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 10));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 9));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.FANGKUAI, 11));
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

		System.out.println(j.find(pokerAllCards, 5));
	}

}
