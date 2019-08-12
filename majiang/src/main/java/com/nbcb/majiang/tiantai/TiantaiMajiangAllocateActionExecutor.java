package com.nbcb.majiang.tiantai;

import com.nbcb.core.action.Action;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangAllocateWithHuaActionExecutor;

public class TiantaiMajiangAllocateActionExecutor extends
		MajiangAllocateWithHuaActionExecutor {

	public void exec(Game game, Action action) {

		super.exec(game, action);
		MajiangGame mg = (MajiangGame) game;
		TiantaiMajiangGameInfo gf = (TiantaiMajiangGameInfo) mg.getGameInfo();
		if (!gf.isHasBaida()) {
			return;
		}
		Card[] cards = mg.getAllCards().findCardsByType("白");
		for (int i = 0; i < cards.length; i++) {
			((MajiangCard) cards[i]).setBaida(true);
		}
	}

}
