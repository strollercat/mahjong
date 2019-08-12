package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

public class ThreeWaterWulongPokerUnitCardsJudger implements
		PokerUnitCardsJudger {


	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;
	private ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger;
	private SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	public void setColorSamePokerUnitCardsJudger(
			ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger) {
		this.colorSamePokerUnitCardsJudger = colorSamePokerUnitCardsJudger;
	}

	public void setSequencePokerUnitCardsJudger(
			SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger) {
		this.sequencePokerUnitCardsJudger = sequencePokerUnitCardsJudger;
	}

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		if (pokerCards.size() != 3 && pokerCards.size() != 5) {
			return null;
		}
		if (pokerCards.size() == 3) {
			if (null != numberSamePokerUnitCardsFinder.find(pokerCards, 2)) {
				return null;
			}
			return new ThreeWaterWulongPokerUnitCards(pokerCards);
		}

		if (null != numberSamePokerUnitCardsFinder.find(pokerCards, 2)) {
			return null;
		}
		if (null != colorSamePokerUnitCardsJudger.judge(pokerCards)) {
			return null;
		}
		if (null != sequencePokerUnitCardsJudger.judge(pokerCards)) {
			return null;
		}
		return new ThreeWaterWulongPokerUnitCards(pokerCards);

	}


	public static void main(String[] args) {
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();

		ThreeWaterWulongPokerUnitCardsJudger j = new ThreeWaterWulongPokerUnitCardsJudger();
		j.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);
		j.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		System.out.println(j.judge(pcs));
	}

}
