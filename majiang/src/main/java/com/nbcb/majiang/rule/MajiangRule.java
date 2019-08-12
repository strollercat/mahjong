package com.nbcb.majiang.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.core.io.MessageListener;
import com.nbcb.core.room.RoomStopJudger;
import com.nbcb.core.rule.AbstractRule;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.helper.TanShuCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangRule extends AbstractRule {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangRule.class);

	protected MessageListener messageListener = null;

	protected RoomStopJudger roomStopJudger;

	protected TanShuCalculator allTanShuCalculator;

	public void setAllTanShuCalculator(TanShuCalculator allTanShuCalculator) {
		this.allTanShuCalculator = allTanShuCalculator;
	}

	public void setRoomStopJudger(RoomStopJudger roomStopJudger) {
		this.roomStopJudger = roomStopJudger;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * 发送一些吃三下或者碰三下的一些提示信息
	 * 
	 * @param game
	 * @param action
	 * @param translatedAction
	 */
	protected void sendMessage(Game game, Action action, Action translatedAction) {
		if (this.messageListener == null) {
			return;
		}
		/**
		 * 即使抛异常也不要影响主流程
		 */
		try {
			int type = translatedAction.getType();
			if (type != MajiangAction.PENG && type != MajiangAction.CHI
					&& type != MajiangAction.MINGGANG) {
				return;
			}

			MajiangGame mjGame = (MajiangGame) game;
			MajiangPlayer mjPlayer = (MajiangPlayer) translatedAction
					.getPlayer();
			Player daPlayer = mjGame.getHistoryActions()
					.getLastActionByType(MajiangAction.DANONHUA).getPlayer();

			int pengSize = this.allTanShuCalculator.calculatorTanShu(mjPlayer,
					(MajiangPlayer) daPlayer);
			logger.info("### pengSize[" + pengSize + "]");
			if (pengSize >= 2) {
				String tintMessage = messageListener
						.getPlaceHolderAccount(daPlayer.getAccount())
						+ "被"
						+ messageListener
								.getPlaceHolderAccount(translatedAction
										.getPlayer().getAccount())
						+ "碰了"
						+ pengSize + "下";
				logger.info("### tintMessage[" + tintMessage + "]");
				messageListener.listen(daPlayer.getAccount(), tintMessage);
				messageListener.listen(translatedAction.getPlayer()
						.getAccount(), tintMessage);
			}
		} catch (Exception e) {
			logger.error("### message push error", e);
			return;
		}

	}

	protected void recordLastWaitUserActionTime1(Game game,
			Action currentAction, Action[] legalActions) {
		if (legalActions == null || legalActions.length == 0) {
			this.recordLastWaitUserActionTime(game, currentAction, null);
		}
		Actions actions = new MajiangActions();
		for (int i = 0; i < legalActions.length; i++) {
			actions.addAction(legalActions[i]);
		}
		this.recordLastWaitUserActionTime(game, currentAction, actions);
	}

	protected void recordLastWaitUserActionTime(Game game,
			Action currentAction, Actions legalActions) {

		MajiangGame mjGame = (MajiangGame) game;

		if (legalActions != null && legalActions.size() > 0) {
			for (int i = 0; i < legalActions.size(); i++) {
				Action action = legalActions.getAction(i);
				if (!action.isUserAction()) {
					continue;
				}
				int type = action.getType();
				if (type != MajiangAction.NO && type != MajiangAction.DANONHUA
						&& type != MajiangAction.PENG
						&& type != MajiangAction.MINGGANG
						&& type != MajiangAction.XIANGANG
						&& type != MajiangAction.ANGANG
						&& type != MajiangAction.CHI
						&& type != MajiangAction.MOFRONTHU
						&& type != MajiangAction.MOBACKHU
						&& type != MajiangAction.QIANGGANGHU) {
					continue;
				}
				if (action.getPlayer() == null) {
					continue;
				}
				String account = action.getPlayer().getAccount();
				mjGame.putLastWaitUserActionTime(account,
						System.currentTimeMillis());
			}
		}

		if (!currentAction.isUserAction()) {
			return;
		}
		int type = currentAction.getType();
		if (type != MajiangAction.NO && type != MajiangAction.USERDA
				&& type != MajiangAction.USERPENG
				&& type != MajiangAction.USERGANG
				&& type != MajiangAction.USERHU
				&& type != MajiangAction.USERCHI) {
			return;
		}
		if (currentAction.getPlayer() == null) {
			return;
		}
		if (!mjGame.isPlayerManagerd(currentAction.getPlayer().getAccount())) { // 没有托管的状态下，记一下时间
			mjGame.putLastWaitUserActionTime(currentAction.getPlayer()
					.getAccount(), System.currentTimeMillis() + 3 * 60 * 60
					* 1000);
		}
	}

	protected void nextInner(Game game, Action action) {
		logger.info("### enter into the next action, action "
				+ (action == null ? null : action));
		Actions nextActions;
		Action translatedAction = this.translateAction(game, action);
		logger.info("### translatedAction is " + translatedAction);
		logger.info("### after translate legalActions "
				+ game.getLegalActions());
		if (null == translatedAction) { // 表示非法的
			return;
		}

		if (translatedAction.getType() == MajiangAction.NO) {
			nextActions = game.getLegalActions();
			((MajiangAction) translatedAction).setAttribute("NOTNEXT", true);
			// game.addAction(translatedAction);
		} else {
			ActionExecutor actionExecutor = actionExecutorMapping
					.getActionExecutor(translatedAction);
			actionExecutor.exec(game, translatedAction);
			game.addAction(translatedAction);
			this.sendMessage(game, action, translatedAction);

			// must wait the user to action again
			if (game.getLegalActions() != null) {
				Action[] userActions = game.getLegalActions()
						.getActionsByUserAction(true);
				if (userActions != null && userActions.length != 0) {
					logger.info("### must wait the user to action again");

					try {
						this.recordLastWaitUserActionTime1(game, action,
								userActions);
					} catch (Exception e) {
						logger.error("### recordLastWaitUserActionTime", e);
					}

					((MajiangAction) translatedAction).setAttribute("NOTNEXT",
							true);
					actionNotify.notify(game, translatedAction);
					return;
				}
			}
			nextActions = calNext(game, translatedAction);
			game.setLegalActions(nextActions);
		}

		try {
			this.recordLastWaitUserActionTime(game, action, nextActions);
		} catch (Exception e) {
			logger.error("### recordLastWaitUserActionTime", e);
		}

		logger.info("### nextActions " + nextActions);
		if (nextActions != null) {
			actionNotify.notify(game, translatedAction);
		} else {
			game.stop();
			actionNotify.notify(game, translatedAction);
			if (roomStopJudger.mayStop(game.getRoom())) {
				game.getRoom().getChannel().getServer()
						.destroyRoom(game.getRoom().getId(), "游戏结束");
			}
			return;
		}

		if (nextActions.size() == 1) {
			Action nextAction = nextActions.getHeadAction();
			if (!nextAction.isUserAction()) {
				this.next(game, nextAction);
			}
			return;
		}
		logger.info("### majiang Rule to the end");
	}

	public void next(Game game, Action action) {

		this.nextInner(game, action);

	}

	private Action translateAction(Game game, Action action) {

		// logger.info("### start to translate action");
		int type = action.getType();

		// 系统自己生成的
		if (!action.isUserAction()) {
			game.setLegalActions(null);
			return action;
		}

		if (type != MajiangAction.NO && type != MajiangAction.USERDA
				&& type != MajiangAction.USERPENG
				&& type != MajiangAction.USERGANG
				&& type != MajiangAction.USERHU
				&& type != MajiangAction.USERCHI) {
			logger.error("### " + action + " is illegal action");
			return null;
		}
		if (action.getPlayer() == null) {
			logger.error("### action player can not be null ");
			return null;
		}

		if (type == MajiangAction.NO) {
			return this.translateNoAction(game, action);
		}

		if (type == MajiangAction.USERDA) {
			return this.translateUserDaAction(game, action);
		}
		if (type == MajiangAction.USERPENG) {
			return this.translateUserPengAction(game, action);
		}

		if (type == MajiangAction.USERGANG) {
			return this.translateUserGangAction(game, action);
		}

		if (type == MajiangAction.USERHU) {
			return this.translateUserHuAction(game, action);
		}

		if (type == MajiangAction.USERCHI) {
			return this.translateUserChiAction(game, action);
		}

		return null;
	}

	protected Action translateNoAction(Game game, Action action) {
		// logger.info("### enter into the translateNoAction");
		Actions legalActions = game.getLegalActions();
		Action[] arrAction = legalActions.getActionsByPlayer(action.getPlayer()
				.getAccount());
		if (arrAction == null || arrAction.length == 0) {
			logger.info(action + " is illegal action,there is no "
					+ action.getPlayer().getAccount() + "action ");
			return null;
		}

		// 同一个用户有不同的选择，把胡，碰，吃，杠,跟的都干掉，只剩下打和摸，因为不可能不打或不摸
		for (Action a : arrAction) {
			if (a.getType() != MajiangAction.MOFRONT
					&& a.getType() != MajiangAction.DANONHUA
					&& a.getType() != MajiangAction.MOBACK) {
				legalActions.removeActionByTypeAndPlayer(a.getPlayer()
						.getAccount(), a.getType());
			}
		}
		MajiangAction maRet = (MajiangAction) action;
		return maRet;
	}

	protected Action translateUserDaAction(Game game, Action action) {
		// logger.info("### translateUserDaAction ");
		Action[] arrActions = game.getLegalActions().getActionsByTypeAndPlayer(
				action.getPlayer().getAccount(), MajiangAction.DANONHUA);
		if (arrActions == null) {
			return null;
		}
		if (action.getCards() == null || action.getCards().size() != 1) {
			logger.info("### error,translateUserDaAction cards is illegal");
			return null;
		}

		MajiangPlayer mp = (MajiangPlayer) action.getPlayer();
		if (!mp.getMajiangInnerCards().containsCard(
				action.getCards().getCard(0))) {
			logger.info("### error,translateUserDaAction cards is illegal");
			return null;
		}
		Action retAction = arrActions[0];
		retAction.changeCards(action.getCards());
		game.setLegalActions(null);
		return retAction;
	}

	protected Action translateUserPengAction(Game game, Action action) {
		MajiangPlayer mjPlayer = (MajiangPlayer) action.getPlayer();
		Action[] arrActions = game.getLegalActions().getActionsByTypeAndPlayer(
				mjPlayer.getAccount(), MajiangAction.PENG);

		// 没有碰的Action
		if (arrActions == null || arrActions.length == 0) {
			logger.info("### action is illegal " + action);
			return null;
		}

		// 有没有其他用户比碰大的动作，如胡
		for (int i = 0; i < game.getActivePlayers(); i++) {
			MajiangPlayer nextPlayer = (MajiangPlayer) game.getPlayerByIndex(i);
			if (nextPlayer == action.getPlayer()) {
				continue;
			}
			if (nextPlayer == game.getLastAction().getPlayer()) {
				continue;
			}

			Action[] otherActions = game.getLegalActions().getActionsByPlayer(
					nextPlayer.getAccount());
			if (otherActions == null || otherActions.length == 0) {
				continue;
			}
			for (Action otherAction : otherActions) {
				if (otherAction.getType() == MajiangAction.FANGQIANGHU) {
					logger.info("### mjPlayer[" + mjPlayer.getAccount()
							+ "]peng action 有其他用户比碰大的动作");
					return null;
				}
			}
		}
		game.setLegalActions(null);
		return arrActions[0];

	}

	// 可以是明港暗釭险釭中的一种
	protected Action translateUserGangAction(Game game, Action action) {
		logger.info("### translateUserGangAction ");
		MajiangPlayer mjPlayer = (MajiangPlayer) action.getPlayer();
		if (action.getCards() == null || action.getCards().size() != 1) {
			logger.info("### gang action card size must be 1");
			return null;
		}

		Action[] arrMingGangActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.MINGGANG);
		Action[] arrAnGangActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.ANGANG);
		Action[] arrXianGangActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.XIANGANG);

		if (arrMingGangActions != null && arrMingGangActions.length == 1) {
			// 有没有其他用户比杠大的动作，如胡
			for (int i = 0; i < game.getActivePlayers(); i++) {
				MajiangPlayer nextPlayer = (MajiangPlayer) game
						.getPlayerByIndex(i);
				if (nextPlayer == mjPlayer) { // 如果是自己的动作,跳过
					continue;
				}
				Action[] otherActions = game.getLegalActions()
						.getActionsByPlayer(nextPlayer.getAccount());
				if (otherActions == null || otherActions.length == 0) {
					continue;
				}
				for (Action otherAction : otherActions) {
					if (otherAction.getType() == MajiangAction.FANGQIANGHU) {
						logger.info("### mjPlayer[" + mjPlayer.getAccount()
								+ "] gang action 有其他用户比杠大的动作");
						return null;
					}
				}
			}
			game.setLegalActions(null);
			return arrMingGangActions[0];
		}

		Card mjCard = action.getCards().getHeadCard();
		// logger.info("### mjCard "+mjCard.getNumber() );
		if (arrAnGangActions != null && arrAnGangActions.length >= 1) {
			for (int i = 0; i < arrAnGangActions.length; i++) {
				if (arrAnGangActions[i].getCards().getHeadCard().getType()
						.equals(mjCard.getType())) {
					game.setLegalActions(null);
					return arrAnGangActions[i];
				}
			}
		}
		if (arrXianGangActions != null && arrXianGangActions.length >= 1) {
			for (int i = 0; i < arrXianGangActions.length; i++) {
				if (arrXianGangActions[i].getCards().getHeadCard().getType()
						.equals(mjCard.getType())) {
					game.setLegalActions(null);
					return arrXianGangActions[i];
				}
			}
		}
		return null;
	}

	protected Action translateUserHuAction(Game game, Action action) {
		MajiangPlayer mjPlayer = (MajiangPlayer) action.getPlayer();
		Actions legalActions = game.getLegalActions();
		Action[] arrMoFrontHuActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.MOFRONTHU);
		Action[] arrMoBackHuActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.MOBACKHU);
		Action[] arrFangQiangHuActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.FANGQIANGHU);
		Action[] arrQiangGangHuActions = game.getLegalActions()
				.getActionsByTypeAndPlayer(mjPlayer.getAccount(),
						MajiangAction.QIANGGANGHU);

		if (arrFangQiangHuActions != null && arrFangQiangHuActions.length == 1) {
			game.getLegalActions().removeActionByTypeAndPlayer(
					action.getPlayer().getAccount(), MajiangAction.FANGQIANGHU);

			Action[] fqhActions = legalActions
					.getActionsByType(MajiangAction.FANGQIANGHU);
			if (fqhActions == null || fqhActions.length == 0) {
				game.setLegalActions(null);
			}
			return arrFangQiangHuActions[0];
		}

		if (arrQiangGangHuActions != null && arrQiangGangHuActions.length == 1) {
			game.getLegalActions().removeActionByTypeAndPlayer(
					action.getPlayer().getAccount(), MajiangAction.QIANGGANGHU);

			Action[] qghActions = legalActions
					.getActionsByType(MajiangAction.QIANGGANGHU);
			if (qghActions == null || qghActions.length == 0) {
				game.setLegalActions(null);
			}
			return arrQiangGangHuActions[0];
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

	protected Action translateUserChiAction(Game game, Action action) {
		MajiangPlayer mjPlayer = (MajiangPlayer) action.getPlayer();
		Action[] arrActions = game.getLegalActions().getActionsByTypeAndPlayer(
				mjPlayer.getAccount(), MajiangAction.CHI);

		// 没有吃的Action
		if (arrActions == null || arrActions.length == 0) {
			logger.info("### action is illegal " + action);
			return null;
		}
		if (action.getCards() == null || action.getCards().size() != 2) {
			logger.info("### action is illegal " + action);
			return null;
		}

		// 有没有其他用户比吃大的动作，如杠，碰，胡
		MajiangPlayer nextPlayer = mjPlayer;
		while (true) {
			nextPlayer = (MajiangPlayer) game.nextPlayer(nextPlayer);
			if (nextPlayer == mjPlayer) {
				break;
			}
			if (nextPlayer == game.getLastAction().getPlayer()) {
				continue;
			}
			Action[] otherActions = game.getLegalActions().getActionsByPlayer(
					nextPlayer.getAccount());
			if (otherActions == null || otherActions.length == 0) {
				continue;
			}
			for (Action otherAction : otherActions) {
				if (otherAction.getType() == MajiangAction.FANGQIANGHU
						|| otherAction.getType() == MajiangAction.PENG
						|| otherAction.getType() == MajiangAction.MINGGANG) {
					logger.info("### mjPlayer[" + mjPlayer.getAccount()
							+ "]chi action 有其他用户比吃大的动作");
					return null;
				}
			}
		}

		action.getCards().sort();
		for (Action a : arrActions) {
			a.getCards().sort();
			if (a.getCards().sameByCardNumberAndType(action.getCards())) {
				game.setLegalActions(null);
				return a;
			}
		}
		return null;
	}
}
