package com.nbcb.majiang.rule.judger;

import java.util.List;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangXianGangActionJudger extends MajiangActionJudger {

	private MajiangActions xianGangJuger(MajiangPlayer mp,
			MajiangMiddleCards mmcs) {
		MajiangActions mas = new MajiangActions();
		MajiangInnerCards mics = mp.getMajiangInnerCards();
		
		List<MajiangUnitCards> listUcs = mmcs.getListUnitCards();
		if (listUcs == null || listUcs.size() == 0) {
			return null;
		}
		for (MajiangUnitCards ucs : listUcs) {
			if (ucs instanceof MajiangPengUnitCards) {
				for (int i = 0; i < mics.size(); i++) {
					Card card = mics.getCard(i);
					String type = card.getType();
					if (ucs.getHeadCard().getType().equals(type)) {
						Cards cards = new MajiangCards();
						cards.addTailCard(card);
						mas.addAction(new MajiangAction(mp, MajiangAction.XIANGANG,
								cards, true));
					}
				}
			}
		}
		return mas.size() == 0 ? null : mas;
	}

	@Override
	protected MajiangActions judgeInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangActions mas = new MajiangActions();
		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();
		MajiangActions tmpMas = this.xianGangJuger(mp,
				mp.getMajiangMiddleCards());
		if (tmpMas != null) {
			mas.addActions(tmpMas);
		}
		return mas.size() == 0 ? null : mas;
	}
}
