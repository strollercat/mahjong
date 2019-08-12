package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangPaodeHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.PAODA;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		MajiangCards mcs = mjHuCards.findDuiziUnitCards();
		if (!mcs.containsCard(mjHuCards.getMjHuCard())) {
			return new HuType(false, null);
		}
		MajiangCard mc1 = (MajiangCard) mcs.getHeadCard();
		MajiangCard mc2 = (MajiangCard) mcs.getTailCard();
		MajiangCard otherCard = null;
		if (mc1 == mjHuCards.getMjHuCard()) {
			otherCard = mc2;
		} else if (mc2 == mjHuCards.getMjHuCard()) {
			otherCard = mc1;
		}
		if (otherCard == null) {
			return new HuType(false, null);
		}
		if (otherCard.isBaida()) {
			return new HuType(true, null);
		}
		return new HuType(false, null);
	}

}
