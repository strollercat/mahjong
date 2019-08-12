package com.nbcb.majiang.hongzhong;

import java.util.List;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.MajiangXianGangActionJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class HongzhongMajiangXianGangActionJudger extends
		MajiangXianGangActionJudger {

	@Override
	protected MajiangActions judgeInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangActions mas = new MajiangActions();
		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();

		MajiangCard mc = (MajiangCard) mjAction.getCards().getTailCard();
		MajiangMiddleCards mmcs = mp.getMajiangMiddleCards();
		String type = mc.getType();
		List<MajiangUnitCards> listUcs = mmcs.getListUnitCards();
		if (listUcs == null || listUcs.size() == 0) {
			return null;
		}
		for (MajiangUnitCards ucs : listUcs) {
			if (ucs instanceof MajiangPengUnitCards) {
				if (ucs.getHeadCard().getType().equals(type)) {
					Cards cards = new MajiangCards();
					cards.addTailCard(mc);
					mas.addAction(new MajiangAction(mp, MajiangAction.XIANGANG,
							cards, true));
					return mas;
				}
			}
		}
		return null;
	}

}
