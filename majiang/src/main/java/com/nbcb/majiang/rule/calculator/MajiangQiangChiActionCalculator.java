package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangChiUnitCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.helper.TanShuCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangQiangChiActionCalculator extends MajiangChiActionCalculator {

	protected TanShuCalculator allTanShuCalculator;

	public void setAllTanShuCalculator(TanShuCalculator allTanShuCalculator) {
		this.allTanShuCalculator = allTanShuCalculator;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub

		MajiangPlayer actionPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangChiUnitCards mcus = (MajiangChiUnitCards) actionPlayer
				.getMajiangMiddleCards().getLatestMajiangUnitCards();
		MajiangPlayer daPlayer = mcus.getMjDaPlayer();

		Action ma = new MajiangAction(mjAction.getPlayer(),
				MajiangAction.DANONHUA, null, true);

		if (this.allTanShuCalculator.calculatorTanShu(actionPlayer, daPlayer) < 3) {
			int noDa[] = this.canNotDaCardsArr(mjGame, mjAction);
			if (noDa != null) {
				ma.setAttribute(MajiangAction.CANNOTDA, noDa);
			}
		}

		Actions actions = new MajiangActions();
		actions.addAction(ma);
		return actions;
	}

	/**
	 * 获取吃了以后不能打的牌
	 * 
	 * @param mjGame
	 * @param mjAction
	 * @return
	 */
	protected int[] canNotDaCardsArr(MajiangGame mjGame, MajiangAction mjAction) {
		MajiangPlayer mjPlayer = (MajiangPlayer) mjAction.getPlayer();
		Cards chiCards = mjAction.getCards();
		if (chiCards == null || chiCards.size() != 2) {
			return null;
		}
		MajiangInnerCards innerCards = mjPlayer.getMajiangInnerCards();
		MajiangCard mc1 = (MajiangCard) chiCards.getHeadCard();
		MajiangCard mc2 = (MajiangCard) chiCards.getTailCard();
		if (mc1.getType().length() == 1 || mc2.getType().length() == 1) {
			return null;
		}
		if (!mc2.getSecondType().equals(mc1.getSecondType())) {
			return null;
		}

		int number1 = mc1.getFirstNumber();
		int number2 = mc2.getFirstNumber();
		if (number1 == number2) {
			return null;
		}

		int maxNumber = number1 > number2 ? number1 : number2;
		int minNumber = number1 > number2 ? number2 : number1;

		Cards copyCards = new MajiangCards();

		if (maxNumber - minNumber == 2) {
			String type = ((minNumber + 1) + mc1.getSecondType());
			Card[] findCards = innerCards.findCardsByType(type);
			copyCards.addTailCards(findCards);
		} else if (maxNumber - minNumber == 1) {
			if (maxNumber == 9) {
				String type = ((minNumber - 1) + mc1.getSecondType());
				Card[] findCards = innerCards.findCardsByType(type);
				copyCards.addTailCards(findCards);
			} else if (minNumber == 1) {
				String type = ((minNumber + 2) + mc1.getSecondType());
				Card[] findCards = innerCards.findCardsByType(type);
				copyCards.addTailCards(findCards);
			} else {
				String type = ((maxNumber + 1) + mc1.getSecondType());
				Card[] findCards = innerCards.findCardsByType(type);
				copyCards.addTailCards(findCards);

				type = ((minNumber - 1) + mc1.getSecondType());
				findCards = innerCards.findCardsByType(type);
				copyCards.addTailCards(findCards);
			}
		} else {
			return null;
		}

		if (copyCards.size() == 0) {
			return null;
		}
		return copyCards.toNumberArray();
	}
}
