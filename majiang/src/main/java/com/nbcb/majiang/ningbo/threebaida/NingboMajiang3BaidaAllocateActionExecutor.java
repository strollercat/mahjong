package com.nbcb.majiang.ningbo.threebaida;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.ningbo.NingboMajiangGame;
import com.nbcb.majiang.rule.executor.MajiangAllocateWithHuaActionExecutor;

public class NingboMajiang3BaidaAllocateActionExecutor extends
		MajiangAllocateWithHuaActionExecutor {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang3BaidaAllocateActionExecutor.class);

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
		// System.out.println(mc);
		String nextType = mc.getType();
		Card[] cards = macs.findCardsByType(nextType);

		for (Card c : cards) {
			MajiangCard tmpMc = (MajiangCard) c;
			// System.out.println(tmpMc);
			tmpMc.setBaida(true);
		}
		mbcs.removeCard(mc);
		// System.out.println("mbcs " + mbcs.size());
		ningboMajiangGame.setBaida(mc);
	}
}
