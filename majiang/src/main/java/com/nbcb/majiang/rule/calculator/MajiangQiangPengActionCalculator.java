package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Card;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.helper.TanShuCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangQiangPengActionCalculator extends
		MajiangPengActionCalculator {

	protected TanShuCalculator allTanShuCalculator;

	public void setAllTanShuCalculator(TanShuCalculator allTanShuCalculator) {
		this.allTanShuCalculator = allTanShuCalculator;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {

		MajiangPlayer actionPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangPengUnitCards mucs = (MajiangPengUnitCards) actionPlayer
				.getMajiangMiddleCards().getLatestMajiangUnitCards();
		MajiangPlayer daPlayer = mucs.getMjDaPlayer();

		if (allTanShuCalculator.calculatorTanShu(actionPlayer, daPlayer) >= 3) {
			return super.calculateActionInner(mjGame, mjAction);
		}

		Card[] leftPengCards = actionPlayer.getMajiangInnerCards()
				.findCardsByType(mucs.getMjDaCard().getType());
		if (leftPengCards == null || leftPengCards.length == 0) {
			return super.calculateActionInner(mjGame, mjAction);
		}

		Actions actions = new MajiangActions();
		Action pengAction = new MajiangAction(mjAction.getPlayer(),
				MajiangAction.DANONHUA, null, true);
		MajiangCards copyCards = new MajiangCards();
		copyCards.addTailCards(leftPengCards);
		pengAction.setAttribute(MajiangAction.CANNOTDA,
				copyCards.toNumberArray());
		actions.addAction(pengAction);
		return actions;
	}

	/**
	 * 
	 * @param mjGame
	 * @param mjAction
	 * @return
	 */
	protected int[] canNotDaCardsArr(MajiangGame mjGame, MajiangAction mjAction) {

		MajiangPlayer actionPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangPengUnitCards mucs = (MajiangPengUnitCards) actionPlayer
				.getMajiangMiddleCards().getLatestMajiangUnitCards();
		MajiangPlayer daPlayer = mucs.getMjDaPlayer();

		Card[] leftPengCards = actionPlayer.getMajiangInnerCards()
				.findCardsByType(mucs.getMjDaCard().getType());
		if (leftPengCards == null || leftPengCards.length == 0) {
			return null;
		}
		MajiangCards mcs = new MajiangCards();
		mcs.addTailCards(leftPengCards);
		return mcs.toNumberArray();
	}
}
