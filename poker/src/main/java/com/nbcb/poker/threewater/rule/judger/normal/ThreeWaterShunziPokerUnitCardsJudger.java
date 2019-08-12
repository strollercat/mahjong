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

public class ThreeWaterShunziPokerUnitCardsJudger implements
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

		SequencePokerUnitCards sequencePokerUnitCards = (SequencePokerUnitCards) sequencePokerUnitCardsJudger
				.judge(pokerCards);

		if (sequencePokerUnitCards == null) {
			return null;
		}
		if (colorSamePokerUnitCardsJudger.judge(pokerCards) == null) {
			return new ThreeWaterShunziPokerUnitCards(pokerCards,
					sequencePokerUnitCards);
		}
		return null;
	}

	public static void main(String[] args) {
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();

		ThreeWaterShunziPokerUnitCardsJudger j = new ThreeWaterShunziPokerUnitCardsJudger();
		j.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 11));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 12));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 13));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		System.out.println(j.judge(pcs));
	}

}
