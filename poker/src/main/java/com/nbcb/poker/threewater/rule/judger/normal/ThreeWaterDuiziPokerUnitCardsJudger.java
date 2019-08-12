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

public class ThreeWaterDuiziPokerUnitCardsJudger implements
		PokerUnitCardsJudger {


	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		if (pokerCards.size() != 5 && pokerCards.size() != 3) {
			return null;
		}

		if (null != numberSamePokerUnitCardsFinder.find(pokerCards, 4)) {
			return null;
		}
		if (null != numberSamePokerUnitCardsFinder.find(pokerCards, 3)) {
			return null;
		}
		List<PokerUnitCards> list = numberSamePokerUnitCardsFinder.find(
				pokerCards, 2);
		if (list == null || list.size() == 0 || list.size() != 1) {
			return null;
		}
		PokerCards duizi = list.get(0);
		PokerCards wulong = new PokerCards();
		wulong.addTailCards(pokerCards.toArray());
		wulong.removeCards(duizi.toArray());
		return new ThreeWaterDuiziPokerUnitCards(pokerCards, duizi, wulong);
	}

	public static void main(String[] args) {
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();

		ThreeWaterDuiziPokerUnitCardsJudger j = new ThreeWaterDuiziPokerUnitCardsJudger();
		j.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 5));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));

		System.out.println(j.judge(pcs));
	}

}
