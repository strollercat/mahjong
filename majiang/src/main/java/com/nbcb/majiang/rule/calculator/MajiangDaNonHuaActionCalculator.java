package com.nbcb.majiang.rule.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.MajiangActionJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDaNonHuaActionCalculator extends MajiangActionCalculator {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangDaNonHuaActionCalculator.class);

	protected MajiangActionJudger fangQiangHuActionJudger = null;

	protected MajiangActionJudger mingGangActionJudger = null;

	protected MajiangActionJudger pengActionJudger = null;

	protected MajiangActionJudger chiActionJudger = null;

	public void setFangQiangHuActionJudger(
			MajiangActionJudger fangQiangHuActionJudger) {
		this.fangQiangHuActionJudger = fangQiangHuActionJudger;
	}

	public void setMingGangActionJudger(MajiangActionJudger mingGangActionJudger) {
		this.mingGangActionJudger = mingGangActionJudger;
	}

	public void setPengActionJudger(MajiangActionJudger pengActionJudger) {
		this.pengActionJudger = pengActionJudger;
	}

	public void setChiActionJudger(MajiangActionJudger chiActionJudger) {
		this.chiActionJudger = chiActionJudger;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {

		Actions actions = new MajiangActions();

		if (fangQiangHuActionJudger != null) {
			actions.addActions(fangQiangHuActionJudger.judge(mjGame, mjAction));
		}

		if (mjGame.getMajiangBlackCards().size() == 0) {
			actions.addAction(new MajiangAction(null, MajiangAction.HUANGPAI,
					null, false));
			return actions;
		}

		if (mingGangActionJudger != null) {
			actions.addActions(mingGangActionJudger.judge(mjGame, mjAction));
		}
		if (pengActionJudger != null) {
			actions.addActions(pengActionJudger.judge(mjGame, mjAction));
		}
		if (chiActionJudger != null) {
			actions.addActions(chiActionJudger.judge(mjGame, mjAction));
		}

		Cards cards = new MajiangCards();
		cards.addTailCard(mjGame.getMajiangBlackCards().getHeadCard());
		MajiangAction moMajiangAction = new MajiangAction(
				mjGame.nextPlayer(mjAction.getPlayer()), MajiangAction.MOFRONT,
				cards, false);
		actions.addAction(moMajiangAction);

		return actions;
	}

	/**
	 * 是否是明杠其他人后打的牌,杠后摸的花也算
	 * 
	 * @return 被杠的人
	 */
	protected MajiangPlayer isMingGangDa(MajiangGame mjGame,
			MajiangAction mjAction) {
		logger.info("### isMingGangDa");
		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();
		Actions historyActions = mjGame.getHistoryActions();

		int index = 2;
		Action action = historyActions.getLastActionByIndex(index);
		if (action == null || action.getPlayer() != mp
				|| action.getType() != MajiangAction.MOBACK) {
			return null;
		}
		index += 1;
		while (true) {
			action = historyActions.getLastActionByIndex(index);
			logger.info("### action[" + action + "]");
			if (action == null) {
				break;
			}
			if (action.getType() == MajiangAction.MINGGANG
					&& action.getPlayer() == mp) {
				MajiangMingGangUnitCards ucs = (MajiangMingGangUnitCards) mp
						.getMajiangMiddleCards()
						.getLatestNonHuaMajiangUnitCards();
				return ucs.getMjDaPlayer();
			}
			if (action.getType() == MajiangAction.GEN) {
				index += 1;
				continue;
			}
			if (action.getType() == MajiangAction.NO
					&& null != action.getAttribute("bugen")) {
				index += 1;
				continue;
			}
			if (action.getType() == MajiangAction.DAHUA
					&& action.getPlayer() == mp) {
				Action beforeAction = historyActions
						.getLastActionByIndex(index + 1);
				if (beforeAction == null) {
					break;
				}
				if (beforeAction.getPlayer() == mp
						&& beforeAction.getType() == MajiangAction.MOBACK) {
					index += 2;
					continue;
				}
			}
			break;
		}
		return null;
	}


}
