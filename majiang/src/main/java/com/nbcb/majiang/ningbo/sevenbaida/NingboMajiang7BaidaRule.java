package com.nbcb.majiang.ningbo.sevenbaida;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangChiUnitCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.rule.MajiangRule;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang7BaidaRule extends MajiangRule {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang7BaidaRule.class);

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
		try {
			NingboMajiang7BaidaGame nbMjGame = (NingboMajiang7BaidaGame) game;
			int type = translatedAction.getType();
//			logger.info("### sendMessage " + type);
			if (type != MajiangAction.PENG && type != MajiangAction.CHI
					&& type != MajiangAction.MINGGANG) {
				return;
			}
			// 处于承包状态，不需要在通知了
			if (nbMjGame.isBao()) {
				return;
			}
			Player daPlayer = nbMjGame.getHistoryActions()
					.getLastActionByType(MajiangAction.DANONHUA).getPlayer();
			String actionAccount = messageListener
					.getPlaceHolderAccount(translatedAction.getPlayer()
							.getAccount());
			String daAccount = messageListener.getPlaceHolderAccount(daPlayer
					.getAccount());
			if (type == MajiangAction.MINGGANG) {
				String tintMessage = actionAccount + "杠了" + daAccount + ","
						+ actionAccount + "与" + daAccount + "处于承包关系";
				messageListener.listen(game.getRoom(), tintMessage);
				return;
			}
			// 碰和吃的逻辑
			MajiangPlayer mjPlayer = (MajiangPlayer) translatedAction
					.getPlayer();
			int pengSize = this.allTanShuCalculator.calculatorTanShu(mjPlayer, (MajiangPlayer)daPlayer);
			logger.info("### pengSize[" + pengSize + "]");
			if (pengSize == 2) {
				String tintMessage = daAccount + "被" + actionAccount + "碰了"
						+ pengSize + "下";
				logger.info("### tintMessage[" + tintMessage + "]");
				messageListener.listen(daPlayer.getAccount(), tintMessage);
				messageListener.listen(translatedAction.getPlayer()
						.getAccount(), tintMessage);
			} else if (pengSize == 3) {
				String tintMessage = actionAccount + "碰了" + daAccount + "三下,"
						+ actionAccount + "与" + daAccount + "处于承包关系";
				messageListener.listen(game.getRoom(), tintMessage);
				return;
			}
		} catch (Exception e) {
			logger.error("### message push error", e);
		}
	}

	@Override
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
		if (translatedAction.getType() == MajiangAction.GEN) {
			ActionExecutor actionExecutor = actionExecutorMapping
					.getActionExecutor(translatedAction);
			actionExecutor.exec(game, translatedAction);
			game.addAction(translatedAction);

			if (null != messageListener) {
				messageListener.listen(
						game.getRoom(),
						messageListener.getPlaceHolderAccount(translatedAction
								.getPlayer().getAccount()) + "选择了跟,加入承包");
			}
			nextActions = game.getLegalActions();
		} else if (translatedAction.getType() == MajiangAction.NO) {
			nextActions = game.getLegalActions();
			Action[] daActions = nextActions
					.getActionsByType(MajiangAction.DANONHUA);
			if (daActions != null && daActions.length > 0
					&& null != translatedAction.getAttribute("bugen")) {
				/**
				 * 处理三碰下跟或不跟的逻辑 要把打的消息发送出去
				 */
			} else {
				/**
				 * 设置不要发next的消息
				 */
				((MajiangAction) translatedAction)
						.setAttribute("NOTNEXT", true);
			}
			if (null != translatedAction.getAttribute("bugen")) {
				if (null != messageListener) {
					String tintMessage = messageListener
							.getPlaceHolderAccount(translatedAction.getPlayer()
									.getAccount())
							+ "选择了不跟";
					logger.info("### tintMessage[" + tintMessage + "]");
					messageListener.listen(game.getRoom(), tintMessage);
				}
				game.addAction(translatedAction);
			}
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
					((MajiangAction) translatedAction).setAttribute("NOTNEXT",
							true);
					actionNotify.notify(game, translatedAction);
					return;
				}
			}
			nextActions = calNext(game, translatedAction);
			game.setLegalActions(nextActions);
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
				&& type != MajiangAction.USERGEN) {
			logger.error("### " + action + " is illegal action");
			return null;
		}

		Action[] genActions = game.getLegalActions().getActionsByType(
				MajiangAction.GEN);
		if (genActions != null && genActions.length > 0) {
			if (type != MajiangAction.NO && type != MajiangAction.USERGEN) {
				logger.error("### legalAction中还有跟,不允许其他的action!!!!!!");
				return null;
			}
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

		if (type == MajiangAction.USERGEN) {
			return this.translateUserGenAction(game, action);
		}

		return null;
	}

	protected Action translateUserGenAction(Game game, Action action) {
		Actions legalActions = game.getLegalActions();

		Action[] arrAction = legalActions.getActionsByTypeAndPlayer(action
				.getPlayer().getAccount(), MajiangAction.GEN);
		if (arrAction == null || arrAction.length == 0) {
			logger.info(action + " is illegal action,there is no "
					+ action.getPlayer().getAccount() + "action ");
			return null;
		}
		legalActions.removeActionByTypeAndPlayer(action.getPlayer()
				.getAccount(), MajiangAction.GEN);
		return arrAction[0];

	}

	@Override
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
		boolean isGen = false;
		for (Action a : arrAction) {
			if (a.getType() != MajiangAction.MOFRONT
					&& a.getType() != MajiangAction.DANONHUA
					&& a.getType() != MajiangAction.MOBACK) {
				legalActions.removeActionByTypeAndPlayer(a.getPlayer()
						.getAccount(), a.getType());

				if (a.getType() == MajiangAction.GEN) {
					NingboMajiang7BaidaGame ningboMajiang7BaidaGame = (NingboMajiang7BaidaGame) game;
					ningboMajiang7BaidaGame.putGen(action.getPlayer()
							.getAccount(), false);
					isGen = true;
				}
			}
		}
		MajiangAction maRet = (MajiangAction) action;
		if (isGen) {
			maRet.setAttribute("bugen", action.getPlayer().getPlayerOrder());
		}
		return maRet;
	}


	
}
