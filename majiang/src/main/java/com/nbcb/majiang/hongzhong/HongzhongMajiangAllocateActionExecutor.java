package com.nbcb.majiang.hongzhong;

import com.nbcb.common.service.AdminService;
import com.nbcb.common.util.RandomUtil;
import com.nbcb.core.action.Action;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.core.helper.WinLosePlayerStrategy;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangAllocateActionExecutor;
import com.nbcb.majiang.user.MajiangPlayer;

public class HongzhongMajiangAllocateActionExecutor extends
		MajiangAllocateActionExecutor {

	private AdminService adminService;

	private WinLosePlayerStrategy winLosePlayerStrategy;

	public void setWinLosePlayerStrategy(
			WinLosePlayerStrategy winLosePlayerStrategy) {
		this.winLosePlayerStrategy = winLosePlayerStrategy;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	private void adminJudge(MajiangGame mjGame) {
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			MajiangPlayer mjPlayer = (MajiangPlayer) mjGame.getPlayerByIndex(i);
			if (!this.adminService.isSuperAdmin(mjPlayer.getAccount())) {
				continue;
			}

			MajiangInnerCards mjInnerCards = mjPlayer.getMajiangInnerCards();
			if (mjInnerCards.baidaCards().size() != 0) {
				break;
			}

			int random = RandomUtil.getRandomNumber(1, 100);
			if (random >= 40) {
				break;
			}

			MajiangBlackCards mjBcs = mjGame.getMajiangBlackCards();

			if (mjBcs.baidaCards().size() == 0) {
				break;
			}
			mjBcs.addTailCard(mjInnerCards.removeTailCard());
			MajiangCard baidaCard = (MajiangCard) mjBcs.baidaCards().getCard(0);
			mjInnerCards.addTailCard(baidaCard);
			mjBcs.removeCard(baidaCard);
			break;
		}
	}

	private void winJudge(MajiangGame mjGame) {
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			MajiangPlayer mjPlayer = (MajiangPlayer) mjGame.getPlayerByIndex(i);
			if (!this.winLosePlayerStrategy.isNeedWinPlayer(mjPlayer
					.getAccount())) {
				continue;
			}

			MajiangInnerCards mjInnerCards = mjPlayer.getMajiangInnerCards();
			if (mjInnerCards.baidaCards().size() != 0) {
				continue;
			}

			int random = RandomUtil.getRandomNumber(1, 100);
			if (random > winLosePlayerStrategy.needWinRatio(mjPlayer
					.getAccount())) {
				continue;
			}

			MajiangBlackCards mjBcs = mjGame.getMajiangBlackCards();

			if (mjBcs.baidaCards().size() == 0) {
				continue;
			}
			mjBcs.addTailCard(mjInnerCards.removeTailCard());
			MajiangCard baidaCard = (MajiangCard) mjBcs.baidaCards().getCard(0);
			mjInnerCards.addTailCard(baidaCard);
			mjBcs.removeCard(baidaCard);
		}
	}

	private void loseJudge(MajiangGame mjGame) {
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			MajiangPlayer mjPlayer = (MajiangPlayer) mjGame.getPlayerByIndex(i);
			if (!this.winLosePlayerStrategy.isNeedLosePlayer(mjPlayer
					.getAccount())) {
				continue;
			}

			// need lose player
			MajiangInnerCards mjInnerCards = mjPlayer.getMajiangInnerCards();
			if (mjInnerCards.baidaCards().size() == 0) {
				continue;
			}

			int random = RandomUtil.getRandomNumber(1, 100);
			if (random > this.winLosePlayerStrategy.needLoseRatio(mjPlayer
					.getAccount())) {
				continue;
			}

			MajiangBlackCards mjBcs = mjGame.getMajiangBlackCards();

			MajiangCards baidaCards = mjInnerCards.baidaCards();

			for (int j = 0; j < baidaCards.size(); j++) {
				Card tmpCard = baidaCards.getCard(j);
				mjBcs.addCard(5 + j, tmpCard);
				mjInnerCards.removeCard(tmpCard);
				mjInnerCards.addTailCard(mjBcs.removeTailCard());
			}
		}
	}

	public void exec(Game game, Action action) {
		super.exec(game, action);
		HongzhongMajiangGame mg = (HongzhongMajiangGame) game;

		Card[] cards = mg.getAllCards().findCardsByType("中");
		for (int i = 0; i < cards.length; i++) {
			((MajiangCard) cards[i]).setBaida(true);
		}

		/**
		 * for auto dapai
		 */
		for (int i = 0; i < game.getActivePlayers(); i++) {
			mg.putLastWaitUserActionTime(game.getPlayerByIndex(i).getAccount(),
					System.currentTimeMillis() + 2 * 60 * 60 * 1000);
		}

		
		this.winJudge(mg);
		this.loseJudge(mg);
//		this.adminJudge(mg);

	}

}
