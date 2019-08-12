package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDadiaoHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.DADIAO;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		List<MajiangUnitCards> list = mjHuCards.getListMjUnitCards();
		for (MajiangUnitCards mucs : list) {
			if (mucs instanceof MajiangAnChiUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangAnPengUnitCards) {
				return new HuType(false, null);
			}
		}
		return new HuType(true, null);

	}

}
