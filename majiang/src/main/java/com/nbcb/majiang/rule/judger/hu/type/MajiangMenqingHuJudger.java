package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangChiUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangMenqingHuJudger implements MajiangHuJudger {

	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		List<MajiangUnitCards> listMjUnitCards = mjHuCards.getListMjUnitCards();
		if (listMjUnitCards == null || listMjUnitCards.size() == 0) {
			return new HuType(false, null);
		}
		for (MajiangUnitCards mucs : listMjUnitCards) {
			if (mucs instanceof MajiangChiUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangPengUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangMingGangUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangXianGangUnitCards) {
				return new HuType(false, null);
			}
		}
		return new HuType(true, null);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.MENGQING;
	}

}
