package com.nbcb.poker.threewater.rule.judger.normal;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCards;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

public class ThreeWaterTonghuaPokerUnitCardsFinder implements
		PokerUnitCardsFinder {

	private ColorSamePokerUnitCardsFinder colorSamePokerUnitCardsFinder;

	public void setColorSamePokerUnitCardsFinder(
			ColorSamePokerUnitCardsFinder colorSamePokerUnitCardsFinder) {
		this.colorSamePokerUnitCardsFinder = colorSamePokerUnitCardsFinder;
	}

	@Override
	public List<PokerUnitCards> find(PokerCards pokerCards, int size) {
		List<PokerUnitCards> listRet = colorSamePokerUnitCardsFinder.find(
				pokerCards, 5);
		if (listRet == null || listRet.size() == 0) {
			return null;
		}
		List<PokerUnitCards> listRet1 = new ArrayList<PokerUnitCards>();
		for (PokerUnitCards pucs : listRet) {
			listRet1.add(this
					.toThreeWaterTonghuaPokerUnitCards((ColorSamePokerUnitCards) pucs));
		}
		return listRet1;

	}

	private ThreeWaterTonghuaPokerUnitCards toThreeWaterTonghuaPokerUnitCards(
			ColorSamePokerUnitCards colorSamePokerUnitCards) {
		PokerCard pc = (PokerCard) colorSamePokerUnitCards.getHeadCard();
		return new ThreeWaterTonghuaPokerUnitCards(colorSamePokerUnitCards,
				pc.getPokerColor());
	}

	public static void main(String[] args) {

		ColorSamePokerUnitCardsFinder colorSamePokerUnitCardsFinder = new ColorSamePokerUnitCardsFinder();
		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterTonghuaPokerUnitCardsFinder j = new ThreeWaterTonghuaPokerUnitCardsFinder();
		j.setColorSamePokerUnitCardsFinder(colorSamePokerUnitCardsFinder);
		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		 PokerCards pcs = new PokerCards();
		
		
		
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 7));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 8));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 4));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 5));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 6));
		
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 10));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 9));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.FANGKUAI, 11));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.CAOHUA, 12));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.CAOHUA, 13));
		
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.CAOHUA, 1));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.CAOHUA, 2));
		 pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		 PokerCard.Color.CAOHUA, 3));

		System.out.println(j.find(pcs, 5));
	}

}
