package com.nbcb.majiang.rule.calculator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.core.io.MessageListener;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;

public class MajiangDaNonHuaWithNotSameActionCalculator extends
		MajiangDaNonHuaActionCalculator {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangDaNonHuaWithNotSameActionCalculator.class);

	protected MessageListener messageListener = null;

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * 如果是同一圈打了同样的牌两次，那么不能碰 明杠 以及防放冲胡
	 * @param mjGame
	 * @param mjAction
	 * @param actions
	 * @return
	 */
	protected Actions getNotSameDaActions(MajiangGame mjGame,
			MajiangAction mjAction, Actions actions) {

		logger.info("### getNotSameDaActions actions[" + actions + "]");

		if (actions == null || actions.size() == 0) {
			return actions;
		}

		List<String> listAcc = new ArrayList<String>();
		if (actions.size() > 0) {
			String[] differentAccounts = actions.getDifferentAccounts();
			logger.info("### defferentAccounts");
			for (String acc : differentAccounts) {
				logger.info("### acc[" + acc + "]");
				Action sameAction = this.sameDa(mjGame, mjAction, acc);
				if (null == sameAction) {
					continue;
				}
				logger.info("### sameAction[" + sameAction + "]");
				if (this.messageListener != null) {
					String tintMessage = "同一圈"
							+ messageListener.getPlaceHolderAccount(sameAction
									.getPlayer().getAccount()) + "打过"
							+ sameAction.getCards().getCard(0).getType()
							+ ",不能碰杠胡";
					messageListener.listen(acc, tintMessage);
				}
				listAcc.add(acc);
			}
		}
		Actions anotherActions = new MajiangActions();
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.getAction(i);
			boolean removed = false;
			for (String acc : listAcc) {
				if (a.getPlayer().getAccount().equals(acc)) {
					removed = true;
					break;
				}
			}
			if (!removed) {
				anotherActions.addAction(a);
			}
		}
		return anotherActions;
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

		actions = this.getNotSameDaActions(mjGame, mjAction, actions);

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
	 * 同一圈是否有其他人打过
	 * 
	 * @param mjGame
	 * @param mjAction
	 * @param mjAccount
	 *            判断的玩家,该玩家能不能碰杠胡
	 * @return 最早打同样牌的人
	 */
	protected Action sameDa(MajiangGame mjGame, MajiangAction mjAction,
			String mjAccount) {
		Actions historyActions = mjGame.getHistoryActions();
		if (historyActions == null || historyActions.size() == 0) {
			return null;
		}
		Action lastDaAction = historyActions.getLastActionByPlayerAndType(
				mjAccount, MajiangAction.DANONHUA);
		Actions compareActions;
		if (lastDaAction == null) {
			compareActions = historyActions;
		} else {
			compareActions = new MajiangActions();
			for (int i = historyActions.size() - 1; i >= 0; i--) {
				Action tmpAction = historyActions.getAction(i);
				if (tmpAction != lastDaAction) { // 不相等加进去
					compareActions.addAction(tmpAction);
				} else { // 相等的话,跳出循环
					break;
				}
			}
		}
		logger.info("### compareActions[" + compareActions + "]");
		if (compareActions == null || compareActions.size() == 0) {
			return null;
		}

		Card daCard = mjAction.getCards().getCard(0);
		logger.info("### daCard[" + daCard + "]");
		int total = 0;
		Action retAction = null;
		for (int i = compareActions.size() - 1; i >= 0; i--) {
			Action tmpAction = compareActions.getAction(i);
			Cards tmpCards = tmpAction.getCards();
			if (tmpCards == null || tmpCards.size() != 1) {
				continue;
			}
			if (tmpAction.getType() != MajiangAction.DANONHUA) {
				continue;
			}
			if (tmpCards.getCard(0).getType().equals(daCard.getType())) {
				total += 1;
				retAction = tmpAction;
			}
		}
		return total >= 2 ? retAction : null;
	}

}
