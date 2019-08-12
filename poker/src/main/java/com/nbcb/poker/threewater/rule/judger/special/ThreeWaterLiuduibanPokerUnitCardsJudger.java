package com.nbcb.poker.threewater.rule.judger.special;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

/**
 * 
 * @author lele 六对半 六组对子，加上一张单张牌
 * 
 */
public class ThreeWaterLiuduibanPokerUnitCardsJudger implements
		PokerUnitCardsJudger {

	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {

		PokerCards copyPokerCards = new PokerCards();
		copyPokerCards.addTailCards(pokerCards.toArray());

		List<PokerCards> listCards = new ArrayList<PokerCards>();

		List<PokerUnitCards> list = numberSamePokerUnitCardsFinder.find(
				copyPokerCards, 2);
//		System.out.println(list);
		if (list != null && list.size() != 0) {
			listCards.addAll(list);
			for (PokerUnitCards pucs : list) {
				copyPokerCards.removeCards(pucs.toArray());
			}
		}
//		System.out.println(copyPokerCards);
		list = numberSamePokerUnitCardsFinder.find(copyPokerCards, 2);
//		System.out.println(list);
		if (list != null && list.size() != 0) {
			listCards.addAll(list);
			for (PokerUnitCards pucs : list) {
				copyPokerCards.removeCards(pucs.toArray());
			}
		}
		if (copyPokerCards.size() != 1) {
			return null;
		}
//		System.out.println(copyPokerCards);
		PokerCard pc = (PokerCard) copyPokerCards.getHeadCard();
//		int pk = pc.getPokerNumber();
//		Set<Integer> set = pokerCards.findAllPokerNumber();
//		if (set.contains(pk)) {
//			return null;
//		}
		return new ThreeWaterLiuduibanPokerUnitCards(pokerCards, listCards, pc);
	}
	
	
	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterLiuduibanPokerUnitCardsJudger j = new ThreeWaterLiuduibanPokerUnitCardsJudger();
		j.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();

		

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 7));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA,6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 6));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 2));
		
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 2));

		System.out.println(j.judge(pcs));
	}

}
