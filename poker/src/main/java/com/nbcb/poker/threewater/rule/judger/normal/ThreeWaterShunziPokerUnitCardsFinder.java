package com.nbcb.poker.threewater.rule.judger.normal;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCards;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

public class ThreeWaterShunziPokerUnitCardsFinder implements
		PokerUnitCardsFinder {

	private SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder;

	public void setSequencePokerUnitCardsFinder(
			SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder) {
		this.sequencePokerUnitCardsFinder = sequencePokerUnitCardsFinder;
	}

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		// TODO Auto-generated method stub
		List<PokerUnitCards> listRet = this.sequencePokerUnitCardsFinder.find(
				pokerCards, 5);
		if (listRet == null || listRet.size() == 0) {
			return null;
		}

		List<PokerUnitCards> listRet1 = new ArrayList<PokerUnitCards>();

		for (PokerUnitCards pucs : listRet) {
			SequencePokerUnitCards spucs = (SequencePokerUnitCards) pucs;
			listRet1.add(new ThreeWaterShunziPokerUnitCards(spucs, spucs));
		}
		return listRet1;
	}
	

	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterShunziPokerUnitCardsFinder j = new ThreeWaterShunziPokerUnitCardsFinder();
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
