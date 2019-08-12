package com.nbcb.majiang.rule.executor;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDaHuaActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();
		MajiangMiddleCards mmcs = mp.getMajiangMiddleCards();
		MajiangInnerCards mmis = mp.getMajiangInnerCards();
		MajiangCard mc = (MajiangCard) mmis.removeCard(mjAction.getCards()
				.getTailCard());
	//	System.out.println("### mc " + mc);
		mmcs.addHuaCard(mc);
	}

}
