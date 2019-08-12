package com.nbcb.majiang.fenhua;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangAllocateActionExecutor;
import com.nbcb.majiang.user.MajiangPlayer;

public class FenhuaMajiangAllocateActionExecutor extends
		MajiangAllocateActionExecutor {

	private static final Logger logger = LoggerFactory
			.getLogger(FenhuaMajiangAllocateActionExecutor.class);

	// 翻百搭
	public void exec(Game game, Action action) {
		super.exec(game, action);
		MajiangGame mjGame = (MajiangGame) game;
		MajiangAllCards macs = (MajiangAllCards) mjGame.getAllCards();
		MajiangBlackCards mbcs = mjGame.getMajiangBlackCards();
		MajiangCard mc = (MajiangCard) mbcs.getTailCard();

		if (mc.isUnit(MajiangCard.HUA)) {
			MajiangCard[] mcs = macs.getCardsByUnit(MajiangCard.HUA);
//			logger.info("### hua mcs "+mcs.length);
			for (int i = 0; i < mcs.length; i++) {
				if (mcs[i].getNumber() / 4 == mc.getNumber() / 4) {
//					logger.info("### number "+mcs[i]);
					mcs[i].setBaida(true);
				}
			}
		} else {
			String nextType = mc.getType();
			Card[] cards = macs.findCardsByType(nextType);
			for (Card c : cards) {
				MajiangCard tmpMc = (MajiangCard) c;
				tmpMc.setBaida(true);
			}
		}
		mbcs.removeCard(mc);
		mjGame.setBaida(mc);

		MajiangPlayer dealer = mjGame.getDealer();
		MajiangPlayer nextPlayer = dealer;
		do {
			this.changeHua(mjGame, nextPlayer);
			nextPlayer = (MajiangPlayer) mjGame.nextPlayer(nextPlayer);
		} while (nextPlayer != dealer);
	}

	private void changeHua(MajiangGame mg, MajiangPlayer player) {
//		logger.info("### change hua");
		MajiangInnerCards mics = player.getMajiangInnerCards();
		MajiangMiddleCards mmcs = player.getMajiangMiddleCards();
		MajiangBlackCards mbcs = mg.getMajiangBlackCards();
		MajiangCard[] mcs = mics.getCardsByUnit(MajiangCard.HUA);
//		if(mcs!=null && mcs.length!=0 ){
//			logger.info("### hua mcs length ["+mcs.length+"]");
//		}
		if (mcs == null || mcs.length == 0) {
			return;
		}

		int total = 0;
		for (MajiangCard mc : mcs) {
			if (mc.isBaida()) {
				total++;
			}
		}
//		logger.info("### baida hua is ["+total +"]");
		// 所有的花都是白搭，不需要去处理
		if (total == mcs.length) {
			return;
		}

		for (MajiangCard mc : mcs) {
			if (!mc.isBaida()) {
				mmcs.addHuaCard(mc);
				mics.removeCard(mc);
				mics.addTailCard(mbcs.removeTailCard());
			}
		}
		this.changeHua(mg, player);
	}
}
