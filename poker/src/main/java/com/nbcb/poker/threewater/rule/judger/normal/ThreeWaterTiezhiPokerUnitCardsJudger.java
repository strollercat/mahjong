package com.nbcb.poker.threewater.rule.judger.normal;

import java.util.List;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

public class ThreeWaterTiezhiPokerUnitCardsJudger implements
		PokerUnitCardsJudger {


	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		if (pokerCards.size() != 5) {
			return null;
		}
		List<PokerUnitCards> list = numberSamePokerUnitCardsFinder.find(
				pokerCards, 4);
		if (null != list) {
			PokerCards pcs = list.get(0);
			PokerCards copyPcs = new PokerCards();
			copyPcs.addTailCards(pokerCards.toArray());
			copyPcs.removeCards(pcs.toArray());
			return new ThreeWaterTiezhiPokerUnitCards(pokerCards, pcs,
					(PokerCard) copyPcs.getCard(0));
		}
		return null;

	}

	public static void main(String[] args) {
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();

		ThreeWaterTiezhiPokerUnitCardsJudger j = new ThreeWaterTiezhiPokerUnitCardsJudger();
		j.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		System.out.println(j.judge(pcs));
	}

}
