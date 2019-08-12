package com.nbcb.poker.threewater.rule.judger.special;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCards;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;

/**
 * 
 * @author lele 五队三条 五组对子，加上一组三条
 * 
 */
public class ThreeWaterWuduisantiaoPokerUnitCardsJudger implements
		PokerUnitCardsJudger {

	private NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder;

	public void setNumberSamePokerUnitCardsFinder(
			NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder) {
		this.numberSamePokerUnitCardsFinder = numberSamePokerUnitCardsFinder;
	}

	private List<PokerCards> getPokerDuizi(PokerCards pcs) {
		PokerCards pcs1 = new PokerCards();
		pcs1.addTailCards(pcs.removeTailCards(2));

		PokerCards pcs2 = new PokerCards();
		pcs2.addTailCards(pcs.removeTailCards(2));

		List<PokerCards> list = new ArrayList<PokerCards>();
		int pk1 = ((PokerCard) (pcs1.getHeadCard())).getPokerNumber();
		int pk2 = ((PokerCard) (pcs2.getHeadCard())).getPokerNumber();
		list.add(new NumberSamePokerUnitCards(pcs1, pk1));
		list.add(new NumberSamePokerUnitCards(pcs2, pk2));
		return list;
	}

	@Override
	public PokerUnitCards judge(PokerCards pokerCards) {
		// TODO Auto-generated method stub
		PokerCards copyPokerCards = new PokerCards();
		copyPokerCards.addTailCards(pokerCards.toArray());

		List<PokerCards> listThree = new ArrayList<PokerCards>();
		List<PokerCards> listTwo = new ArrayList<PokerCards>();

		List<PokerUnitCards> list = numberSamePokerUnitCardsFinder.find(
				copyPokerCards, 4);
		if (list != null && list.size() != 0) {
			for (PokerUnitCards pucs : list) {
				copyPokerCards.removeCards(pucs.toArray());
				listTwo.addAll(this.getPokerDuizi(pucs));
			}
		}

		list = numberSamePokerUnitCardsFinder.find(copyPokerCards, 3);
		if (list == null) {
			return null;
		}
		if (list.size() != 1) {
			return null;
		}

		copyPokerCards.removeCards(list.get(0).toArray());
		listThree.addAll(list);

		list = numberSamePokerUnitCardsFinder.find(copyPokerCards, 2);
		if (list != null && list.size() != 0) {
			listTwo.addAll(list);
			for (PokerUnitCards pucs : list) {
				copyPokerCards.removeCards(pucs.toArray());
			}
		}
		list = numberSamePokerUnitCardsFinder.find(copyPokerCards, 2);
		if (list != null && list.size() != 0) {
			listTwo.addAll(list);
			for (PokerUnitCards pucs : list) {
				copyPokerCards.removeCards(pucs.toArray());
			}
		}
		if (copyPokerCards.size() != 0) {
			return null;
		}
		return new ThreeWaterWuduisantiaoPokerUnitCards(pokerCards, listTwo,
				listThree);

	}

	public static void main(String[] args) {

		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ThreeWaterWuduisantiaoPokerUnitCardsJudger j = new ThreeWaterWuduisantiaoPokerUnitCardsJudger();
		j.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 5));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 5));

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
				PokerCard.Color.FANGKUAI, 2));

		System.out.println(j.judge(pcs));
	}

}
