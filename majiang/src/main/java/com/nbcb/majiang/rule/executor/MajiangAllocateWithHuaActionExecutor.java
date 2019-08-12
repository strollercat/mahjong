package com.nbcb.majiang.rule.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangAllocateWithHuaActionExecutor extends
		MajiangAllocateActionExecutor {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangAllocateWithHuaActionExecutor.class);

	public void exec(Game game, Action action) {

		super.exec(game, action);
		MajiangGame mg = (MajiangGame) game;
		MajiangPlayer dealer = mg.getDealer();
		MajiangPlayer nextPlayer = dealer;
		do {
			this.changeHua(mg, nextPlayer);
			nextPlayer = (MajiangPlayer) mg.nextPlayer(nextPlayer);
		} while (nextPlayer != dealer);

	}

	private void changeHua(MajiangGame mg, MajiangPlayer player) {
		MajiangInnerCards mics = player.getMajiangInnerCards();
		MajiangMiddleCards mmcs = player.getMajiangMiddleCards();
		MajiangBlackCards mbcs = mg.getMajiangBlackCards();
		MajiangCard[] mcs = mics.getCardsByUnit(MajiangCard.HUA);
		if (mcs == null || mcs.length == 0) {
			return;
		}

		for (MajiangCard mc : mcs) {
			mmcs.addHuaCard(mc);
		}

		mics.removeCards(mcs);
		mics.addTailCards(mbcs.removeTailCards(mcs.length));

		this.changeHua(mg, player);

	}
}
