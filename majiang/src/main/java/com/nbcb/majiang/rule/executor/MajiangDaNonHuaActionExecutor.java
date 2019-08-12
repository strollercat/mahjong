package com.nbcb.majiang.rule.executor;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.card.MajiangOutterCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDaNonHuaActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		MajiangPlayer player = (MajiangPlayer) mjAction.getPlayer();
		MajiangInnerCards mics = player.getMajiangInnerCards();
		MajiangOutterCards mocs = player.getMajiangOutterCards();
		Card c = mjAction.getCards().getHeadCard();
		mics.removeCard(c);
		mocs.addTailCard(c);
	}

}
