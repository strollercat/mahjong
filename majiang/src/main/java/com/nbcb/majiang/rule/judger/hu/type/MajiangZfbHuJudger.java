package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangZfbHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.ZFB;
	}
	
	/**
	 * 如果三个是百搭,暂时不算它
	 */
	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		int total = 0;
		List<MajiangUnitCards> listUnitCards = mjHuCards.getListMjUnitCards();
		for (MajiangUnitCards mucs : listUnitCards) {

			if (mucs instanceof MajiangAnPengUnitCards) {
				if (mucs.totalBaida() == 3) {
					continue;
				}
				MajiangCard mc = mucs.firstNonBaidaCard();
				String type = mc.getType();
				if (type.equals("中") || type.equals("发") || type.equals("白")) {
					total += 1;
				}
				continue;

			}
			if (mucs instanceof MajiangPengUnitCards || mucs instanceof MajiangMingGangUnitCards || mucs instanceof MajiangXianGangUnitCards || mucs instanceof MajiangAnGangUnitCards) {
				String type = mucs.getHeadCard().getType();
				if (type.equals("中") || type.equals("发") || type.equals("白")) {
					total += 1;
				}
				continue;
			}
		}

		return new HuType(true, total);
	}

}
