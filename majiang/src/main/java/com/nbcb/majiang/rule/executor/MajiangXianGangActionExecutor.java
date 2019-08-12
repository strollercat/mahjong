package com.nbcb.majiang.rule.executor;

import java.util.List;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangXianGangActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();
		MajiangCard mc = (MajiangCard) mjAction.getCards().getHeadCard();
		List<MajiangPengUnitCards> list = mp.getMajiangMiddleCards()
				.getPengUnitCards();
		for (MajiangPengUnitCards mucs : list) {
			if (mucs.getTailCard().getType().equals(mc.getType())) {
				mp.getMajiangMiddleCards().removeMajiangUnitCards(mucs);
				MajiangXianGangUnitCards majiangXianGangUnitCards = new MajiangXianGangUnitCards(
						mucs.getMjDaPlayer(), mucs.getMjDaCard(),
						(MajiangCard) mucs.getCard(0),
						(MajiangCard) mucs.getCard(1),
						(MajiangCard) mucs.getCard(2), mc);
				mp.getMajiangMiddleCards().addMajiangUnitCards(
						majiangXianGangUnitCards);
				mp.getMajiangInnerCards().removeCard(mc);
				return;
			}
		}
	}
}
