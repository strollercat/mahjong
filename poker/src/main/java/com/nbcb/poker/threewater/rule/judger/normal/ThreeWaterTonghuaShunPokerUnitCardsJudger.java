package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCards;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

public class ThreeWaterTonghuaShunPokerUnitCardsJudger implements
		PokerUnitCardsJudger {


	private ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger;
	private SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger;

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

		if (pokerCards == null || pokerCards.size() != 5) {
			return null;
		}
		PokerCard pc = (PokerCard) pokerCards.getTailCard();
		SequencePokerUnitCards spucs = (SequencePokerUnitCards) sequencePokerUnitCardsJudger
				.judge(pokerCards);

		if (spucs == null) {
			return null;
		}
		if (colorSamePokerUnitCardsJudger.judge(pokerCards) == null) {
			return null;
		}
		return new ThreeWaterTonghuaShunPokerUnitCards(pokerCards, spucs,
				pc.getPokerColor());
	}


	public static void main(String[] args) {
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();

		ThreeWaterTonghuaShunPokerUnitCardsJudger j = new ThreeWaterTonghuaShunPokerUnitCardsJudger();
		j.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 5));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		System.out.println(j.judge(pcs));
	}

}
