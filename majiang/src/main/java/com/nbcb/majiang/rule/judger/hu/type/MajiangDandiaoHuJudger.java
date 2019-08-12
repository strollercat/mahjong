package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDandiaoHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.DANDIAO;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		MajiangDuiziUnitCards mdzucs = mjHuCards.findDuiziUnitCards();
		if (mdzucs.containsCard(mjHuCards.getMjHuCard())) {
			return new HuType(true, null);
		}
		return new HuType(false, null);
	}

	

}
