package com.nbcb.majiang.fenhua;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.rule.MajiangRule;
import com.nbcb.majiang.user.MajiangPlayer;

public class FenhuaMajiangRule extends MajiangRule {

	private static final Logger logger = LoggerFactory
			.getLogger(FenhuaMajiangRule.class);

	private boolean playerIn(Player player, Action[] actions) {
		for (int i = 0; i < actions.length; i++) {
			if (player == actions[i].getPlayer()) {
				return true;
			}
		}
		return false;
	}

	protected Action translateUserHuAction(Game game, Action action) {
		MajiangPlayer mjPlayer = (MajiangPlayer) action.getPlayer();
		Actions legalActions = game.getLegalActions();
		Action[] arrMoFrontHuActions = legalActions.getActionsByTypeAndPlayer(
				mjPlayer.getAccount(), MajiangAction.MOFRONTHU);
		Action[] arrMoBackHuActions = legalActions.getActionsByTypeAndPlayer(
				mjPlayer.getAccount(), MajiangAction.MOBACKHU);
		Action[] arrFangQiangHuActions = legalActions
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.FANGQIANGHU);
		Action[] arrQiangGangHuActions = legalActions
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.QIANGGANGHU);

		if (arrFangQiangHuActions != null && arrFangQiangHuActions.length == 1) {

			Action[] allFqh = legalActions
					.getActionsByType(MajiangAction.FANGQIANGHU);
			if (allFqh.length == 1) {
				game.setLegalActions(null);
				return arrFangQiangHuActions[0];
			}

			Player daPlayer = game.getHistoryActions()
					.getLastActionByType(MajiangAction.DANONHUA).getPlayer();
			Player nextPlayer = game.nextPlayer(daPlayer);
			while (nextPlayer != daPlayer) {
				if (this.playerIn(nextPlayer, allFqh)) {
					if (nextPlayer == action.getPlayer()) { // 它是第一个
						game.setLegalActions(null);
						return arrFangQiangHuActions[0];
					} else { // 不是第一个
						logger.info("### 有比Player["
								+ action.getPlayer().getAccount() + "]前面的用户有胡!");
						return null;
					}
				}
				nextPlayer = game.nextPlayer(nextPlayer);
			}
		}

		if (arrQiangGangHuActions != null && arrQiangGangHuActions.length == 1) {

			Action[] allAction = legalActions
					.getActionsByType(MajiangAction.QIANGGANGHU);
			if (allAction.length == 1) {
				game.setLegalActions(null);
				return arrQiangGangHuActions[0];
			}

			Player lastPlayer = game.getHistoryActions()
					.getLastActionByType(MajiangAction.XIANGANG).getPlayer();
			Player nextPlayer = game.nextPlayer(lastPlayer);
			while (nextPlayer != lastPlayer) {
				if (this.playerIn(nextPlayer, allAction)) {
					if (nextPlayer == action.getPlayer()) { // 它是第一个
						game.setLegalActions(null);
						return arrQiangGangHuActions[0];
					} else { // 不是第一个
						logger.info("### 有比Player["
								+ action.getPlayer().getAccount() + "]前面的用户有胡!");
						return null;
					}
				}
				nextPlayer = game.nextPlayer(nextPlayer);
			}
		}

		if (arrMoFrontHuActions != null && arrMoFrontHuActions.length == 1) {
			game.setLegalActions(null);
			return arrMoFrontHuActions[0];
		}

		if (arrMoBackHuActions != null && arrMoBackHuActions.length == 1) {
			game.setLegalActions(null);
			return arrMoBackHuActions[0];
		}

		return null;
	}

}
