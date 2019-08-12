package com.nbcb.majiang.rule.executor;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangAnGangActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub

		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();
		Card[] arrCards = mjAction.getCards().toArray();
		mp.getMajiangInnerCards().removeCards(arrCards);
		MajiangAnGangUnitCards magucs = new MajiangAnGangUnitCards((MajiangCard) arrCards[0], (MajiangCard) arrCards[1],
				(MajiangCard) arrCards[2], (MajiangCard) arrCards[3]);
		mp.getMajiangMiddleCards().addMajiangUnitCards(magucs);

	}

}
