package com.nbcb.majiang.rule.executor;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public abstract class MajiangMoActionExecutor extends MajiangActionExecutor {

	abstract protected int getDir();

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangBlackCards mbcs = mjGame.getMajiangBlackCards();
		MajiangPlayer mjPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangInnerCards mics = mjPlayer.getMajiangInnerCards();
		MajiangCard c;
		if (getDir() == 0) {
			c = (MajiangCard) mbcs.removeHeadCard();
		} else {
			c = (MajiangCard) mbcs.removeTailCard();
		}
		mics.addTailCard(c);
		mjPlayer.getMajiangInnerCards().sort();
	}
}
