package com.nbcb.majiang.hangzhou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.action.DefaultActions;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.helper.MajiangGangHelper;
import com.nbcb.majiang.helper.TanShuCalculator;
import com.nbcb.majiang.rule.calculator.MajiangDaNonHuaWithNotSameActionCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class HangzhouMajiangDaNonHuaActionCalculator extends
		MajiangDaNonHuaWithNotSameActionCalculator {

	private TanShuCalculator chiTanShuCalculator;

	private TanShuCalculator pengGangTanShuCalculator;

	private MajiangGangHelper majiangGangHelper;

	public void setMajiangGangHelper(MajiangGangHelper majiangGangHelper) {
		this.majiangGangHelper = majiangGangHelper;
	}

	public void setChiTanShuCalculator(TanShuCalculator chiTanShuCalculator) {
		this.chiTanShuCalculator = chiTanShuCalculator;
	}

	public void setPengGangTanShuCalculator(
			TanShuCalculator pengGangTanShuCalculator) {
		this.pengGangTanShuCalculator = pengGangTanShuCalculator;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(HangzhouMajiangDaNonHuaActionCalculator.class);

	private Actions removedNonZuanFangqianghuAction(MajiangGame mjGame,
			Actions fangqianghuActions) {
		if (fangqianghuActions == null || fangqianghuActions.size() == 0) {
			return null;
		}
		Actions retActions = new DefaultActions();
		for (int i = 0; i < fangqianghuActions.size(); i++) {
			Action a = fangqianghuActions.getAction(i);
			if (a.getPlayer() == mjGame.getDealer()) {
				retActions.addAction(a);
			}
		}
		return retActions;

	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {

		Actions actions = new MajiangActions();
		HangzhouMajiangGame hzMjGame = (HangzhouMajiangGame) mjGame;
		HangzhouMajiangGameInfo hzMjGameInfo = (HangzhouMajiangGameInfo) mjGame
				.getGameInfo();
		MajiangPlayer daPlayer = (MajiangPlayer) mjAction.getPlayer();

		if (!hzMjGameInfo.isZimoHu()) {
			Actions fangqianghuActions = fangQiangHuActionJudger.judge(mjGame,
					mjAction);
			if (hzMjGame.getDealer() != daPlayer) {
				fangqianghuActions = this.removedNonZuanFangqianghuAction(
						mjGame, fangqianghuActions);
			}
			actions.addActions(fangqianghuActions);
		}
		if (mjGame.getMajiangBlackCards().size() == 0) {
			actions.addAction(new MajiangAction(null, MajiangAction.HUANGPAI,
					null, false));
			return actions;
		}

		MajiangCard mc = (MajiangCard) mjAction.getCards().getHeadCard();
		if (mc.isBaida()) {
			if (hzMjGame.getBaidaPlayer() == null) { // 如果是第一次打得
				hzMjGame.setBaidaPlayer((MajiangPlayer) mjAction.getPlayer());
				hzMjGame.setBaidaTimes(1);
				int gangSize = majiangGangHelper.sequenceGangSize(hzMjGame,
						(MajiangPlayer) mjAction.getPlayer(), 2, true, true,
						true, true);
				if (gangSize != 0) {
					hzMjGame.setPiaocaiGangTimes(gangSize);
				}
			} else { // 已经是漂才得情况
				MajiangPlayer mjPlayer = hzMjGame.getBaidaPlayer();
				if (mjPlayer == mjAction.getPlayer()) { // 如果是第一次打的人又打了
					hzMjGame.setBaidaTimes(hzMjGame.getBaidaTimes() + 1);
				} else { // 其他人打了

				}
			}
		} else {
			if (hzMjGame.getBaidaPlayer() != null) { // 漂才的情况下
				MajiangPlayer mjPlayer = hzMjGame.getBaidaPlayer();
				if (mjPlayer == mjAction.getPlayer()) { // 第一次打得人没有胡，而是选择了打
					hzMjGame.setBaidaPlayer(null);
					hzMjGame.setBaidaTimes(0);
					hzMjGame.setPiaocaiGangTimes(0);
				} else { // 其他人打得
				}
			}
		}

		if (hzMjGame.getBaidaPlayer() == null) {// 不是漂财的情况下
			Actions mingGangActions = mingGangActionJudger.judge(mjGame,
					mjAction);
			mingGangActions = this.getPengGangActions(hzMjGame, hzMjGameInfo,
					mingGangActions, daPlayer);
			actions.addActions(mingGangActions);

			Actions pengActions = pengActionJudger.judge(mjGame, mjAction);
			pengActions = this.getPengGangActions(hzMjGame, hzMjGameInfo,
					pengActions, daPlayer);
			actions.addActions(pengActions);

			actions = this.getNotSameDaActions(hzMjGame, mjAction, actions);

			if (mjGame.getGameInfo().getPlayerNumber() == 4) {
				Actions chiActions = chiActionJudger.judge(mjGame, mjAction);
				chiActions = this.getChiActions(hzMjGame, hzMjGameInfo,
						chiActions, daPlayer);
				actions.addActions(chiActions);
			}
		}
		Cards cards = new MajiangCards();
		cards.addTailCard(mjGame.getMajiangBlackCards().getHeadCard());
		MajiangAction moMajiangAction = new MajiangAction(
				mjGame.nextPlayer(mjAction.getPlayer()), MajiangAction.MOFRONT,
				cards, false);
		actions.addAction(moMajiangAction);

		return actions;
	}

	// 处理三滩碰的逻辑
	private Actions getPengGangActions(HangzhouMajiangGame hzMjGame,
			HangzhouMajiangGameInfo hzMjGameInfo, Actions actions,
			MajiangPlayer daPlayer) {

		if (actions == null || actions.size() == 0) {
			return null;
		}
		Actions retActions = new MajiangActions();
		MajiangPlayer dealer = hzMjGame.getDealer();
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.getAction(i);
			MajiangPlayer mp1 = (MajiangPlayer) a.getPlayer();
			if (!hzMjGameInfo.isPengTan()) {
				retActions.addAction(a);
			} else {
				int tanshu = chiTanShuCalculator
						.calculatorTanShu(mp1, daPlayer);
				tanshu += pengGangTanShuCalculator.calculatorTanShu(mp1,
						daPlayer);
				if (tanshu < 2) { // 少于2滩随便搞
					retActions.addAction(a);
				} else {
					if (dealer != mp1 && dealer != daPlayer) { // 两个都是闲家
						String tintMessage = "闲家之间不能三滩";
						messageListener.listen(mp1.getAccount(), tintMessage);
					} else {
						if (hzMjGameInfo.isSanTan()) {
							retActions.addAction(a);
						} else { // 不能吃三滩
							String tintMessage = "不能三滩";
							messageListener.listen(mp1.getAccount(),
									tintMessage);
							return null;
						}
					}
				}
			}
		}
		return retActions;
	}

	// 处理三滩吃的逻辑
	private Actions getChiActions(HangzhouMajiangGame hzMjGame,
			HangzhouMajiangGameInfo hzMjGameInfo, Actions actions,
			MajiangPlayer daPlayer) {
		if (actions == null || actions.size() == 0) {
			return null;
		}
		Actions retActions = new MajiangActions();
		MajiangPlayer dealer = hzMjGame.getDealer();
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.getAction(i);
			MajiangPlayer mp1 = (MajiangPlayer) a.getPlayer();
			logger.info("### mp1 [" + mp1.getAccount() + "]");

			int tanshu = chiTanShuCalculator.calculatorTanShu(mp1, daPlayer);
			if (hzMjGameInfo.isPengTan()) {
				tanshu += pengGangTanShuCalculator.calculatorTanShu(mp1,
						daPlayer);
			}
			if (tanshu < 2) { // 少于2滩随便搞
				retActions.addAction(a);
			} else {
				if (dealer != mp1 && dealer != daPlayer) { // 两个都是闲家
					String tintMessage = "闲家之间不能吃三滩";
					messageListener.listen(mp1.getAccount(), tintMessage);
				} else {
					if (hzMjGameInfo.isSanTan()) {
						retActions.addAction(a);
					} else { // 不能吃三滩
						String tintMessage = "不能吃三滩";
						messageListener.listen(mp1.getAccount(), tintMessage);
						return null;
					}
				}
			}
		}
		return retActions;
	}

}
