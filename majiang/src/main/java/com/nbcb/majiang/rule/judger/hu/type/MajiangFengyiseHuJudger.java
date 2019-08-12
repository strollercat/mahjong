package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangFengyiseHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.FENGYISE;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub

		for (int i = 0; i < mjHuCards.size(); i++) {
			MajiangCard mc = (MajiangCard) mjHuCards.getCard(i);
			if (mc.isBaida()) {
				continue;
			}
			if (mc.isUnit(MajiangCard.ZFB)) {
				continue;
			}
			if (mc.isUnit(MajiangCard.DNXB)) {
				continue;
			}
			if (mc.isUnit(MajiangCard.HUA)) {
				continue;
			}
			return new HuType(false, null);
		}
		return new HuType(true, null);
	}

}
