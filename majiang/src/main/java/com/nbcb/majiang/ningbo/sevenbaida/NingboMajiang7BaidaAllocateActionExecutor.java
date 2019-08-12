package com.nbcb.majiang.ningbo.sevenbaida;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.RandomUtil;
import com.nbcb.core.action.Action;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.core.helper.WinLosePlayerStrategy;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.ningbo.NingboMajiangGame;
import com.nbcb.majiang.ningbo.NingboMajiangHuaJudgeUtil;
import com.nbcb.majiang.rule.executor.MajiangAllocateWithHuaActionExecutor;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang7BaidaAllocateActionExecutor extends
		MajiangAllocateWithHuaActionExecutor {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang7BaidaAllocateActionExecutor.class);

	private WinLosePlayerStrategy winLosePlayerStrategy;

	public void setWinLosePlayerStrategy(
			WinLosePlayerStrategy winLosePlayerStrategy) {
		this.winLosePlayerStrategy = winLosePlayerStrategy;
	}

	private MajiangCard findFirstNonHuaCard(MajiangBlackCards mbcs, int len) {
		MajiangCard mc = (MajiangCard) mbcs.getCard(len - 1);
		if (!mc.isUnit(MajiangCard.HUA)) {
			return mc;
		}
		return this.findFirstNonHuaCard(mbcs, len - 1);
	}

	// 翻百搭
	public void exec(Game game, Action action) {
		super.exec(game, action);
		NingboMajiangGame ningboMajiangGame = (NingboMajiangGame) game;
		MajiangAllCards macs = (MajiangAllCards) ningboMajiangGame
				.getAllCards();
		MajiangBlackCards mbcs = ningboMajiangGame.getMajiangBlackCards();
		MajiangCard mc = this.findFirstNonHuaCard(mbcs, mbcs.size());
		String type = mc.getType();
		Card[] cards = macs.findCardsByType(type);
		for (Card c : cards) {
			MajiangCard tmpMc = (MajiangCard) c;
			tmpMc.setBaida(true);
		}
		type = mc.nextType();
		cards = macs.findCardsByType(type);
		for (Card c : cards) {
			MajiangCard tmpMc = (MajiangCard) c;
			tmpMc.setBaida(true);
		}
		mbcs.removeCard(mc);
		ningboMajiangGame.setBaida(mc);

		Map tmpMap = new HashMap();
		for (int i = 0; i < ningboMajiangGame.getActivePlayers(); i++) {
			Player tmpPlayer = ningboMajiangGame.getPlayerByIndex(i);
			tmpMap.put(tmpPlayer.getPlayerOrder() + "",
					NingboMajiangHuaJudgeUtil.judge(ningboMajiangGame,
							(MajiangPlayer) tmpPlayer));
		}
		action.setAttribute("HUATAISHUALL", tmpMap);

		this.winJudge((MajiangGame) game);
		this.loseJudge((MajiangGame) game);
		this.removeZiCards((MajiangGame) game);

	}

	private void winJudge(MajiangGame mjGame) {
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			MajiangPlayer mjPlayer = (MajiangPlayer) mjGame.getPlayerByIndex(i);
			if (!this.winLosePlayerStrategy.isNeedWinPlayer(mjPlayer
					.getAccount())) {
				continue;
			}
			logger.info("### winplayer[" + mjPlayer.getAccount() + "]");
			MajiangInnerCards mjInnerCards = mjPlayer.getMajiangInnerCards();
			if (mjInnerCards.baidaCards().size() >= 2) {
				continue;
			}
			logger.info("### winplayer[" + mjPlayer.getAccount()
					+ "]baicards < 2");
			int random = RandomUtil.getRandomNumber(1, 100);
			if (random > winLosePlayerStrategy.needWinRatio(mjPlayer
					.getAccount())) {
				continue;
			}

			MajiangBlackCards mjBcs = mjGame.getMajiangBlackCards();

			if (mjBcs.baidaCards().size() == 0) {
				continue;
			}
			logger.info("### baidacards size != 0");
			mjBcs.addTailCard(mjInnerCards.removeTailCard());
			MajiangCard baidaCard = (MajiangCard) mjBcs.baidaCards().getCard(0);
			mjInnerCards.addTailCard(baidaCard);
			mjBcs.removeCard(baidaCard);

			if (mjBcs.baidaCards().size() == 0) {
				continue;
			}

			if (mjInnerCards.baidaCards().size() >= 2) {
				continue;
			}
			logger.info("### winplayer[" + mjPlayer.getAccount()
					+ "]baicards < 2");
			mjBcs.addTailCard(mjInnerCards.removeHeadCard());
			baidaCard = (MajiangCard) mjBcs.baidaCards().getCard(0);
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

				Card blackTmpCard = mjBcs.firstNonBaidaNonZiCard();
				mjInnerCards.addTailCard(blackTmpCard);
				mjBcs.removeCard(blackTmpCard);
			}
		}
	}

	private void removeZiCards(MajiangGame mjGame) {

		MajiangBlackCards mbcs = mjGame.getMajiangBlackCards();

		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			Player tmpPlayer = mjGame.getPlayerByIndex(i);
			MajiangInnerCards mics = ((MajiangPlayer) tmpPlayer)
					.getMajiangInnerCards();
			MajiangCards ziCards = mics.ziCards();
			int ziSize = ziCards.size();
			if (ziSize < 4) {
				continue;
			}
			Card[] removeCards = ziCards.removeTailCards(ziSize - 4);
			mbcs.addTailCards(removeCards);
			mics.removeCards(removeCards);

			MajiangCards nonZiCards = mbcs.nonZiCards();
			removeCards = nonZiCards.removeTailCards(ziSize - 4);
			mics.addTailCards(removeCards);
			mbcs.removeCards(removeCards);
		}
	}

}
