package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangKadaoHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.KAZHANG;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub

		MajiangCard huMc = mjHuCards.getMjHuCard();

		List<MajiangUnitCards> list = mjHuCards.getListMjUnitCards();
		for (MajiangUnitCards mucs : list) {
			if (mucs.containsCard(mjHuCards.getMjHuCard())) {
				if (mucs instanceof MajiangAnChiUnitCards) {

					if (!huMc.isBaida()) {
						if (huMc.isUnit(MajiangCard.DNXB)) {
							return new HuType(false, null);
						}
						if (huMc.isUnit(MajiangCard.ZFB)) {
							return new HuType(false, null);
						}
						int huMcInt = Integer.parseInt(huMc.getType()
								.substring(0, 1));
						if (huMcInt == 1 || huMcInt == 9) {
							return new HuType(false, null);
						}
					}
					
					MajiangCards copyMcs = new MajiangCards();
					copyMcs.addTailCards(mucs.toArray());
					copyMcs.removeCard(mjHuCards.getMjHuCard());
					MajiangCard mc1 = (MajiangCard) copyMcs.getHeadCard();
					MajiangCard mc2 = (MajiangCard) copyMcs.getTailCard();
					if (copyMcs.totalBaida() == 2) {
						return new HuType(true, null);
					}
					if (copyMcs.totalBaida() == 1) {
						Card firstCard = copyMcs.firstNonBaidaCard();
						if (huMc.isBaida()) {
							return new HuType(true, null);
						} else {
							int int1 = Integer.parseInt(firstCard.getType()
									.substring(0, 1));
							int int2 = Integer.parseInt(huMc.getType()
									.substring(0, 1));
							int dis = Math.abs(int1 - int2);
							if (dis == 1) {
								return new HuType(true, null);
							} else if (dis == 2) {
								if (int1 == 1 || int1 == 9) {
									return new HuType(true, null);
								}
								return new HuType(false, null);
							}
						}
					}
					if (copyMcs.totalBaida() == 0) {
						if (mc1.isUnit(MajiangCard.DNXB)) {
							return new HuType(false, null);
						}
						if (mc1.isUnit(MajiangCard.ZFB)) {
							return new HuType(false, null);
						}
						int int1 = Integer.parseInt(mc1.getType().substring(0,
								1));
						int int2 = Integer.parseInt(mc2.getType().substring(0,
								1));
						int min = Math.min(int1, int2);
						int max = Math.max(int1, int2);
						int dis = Math.abs(int1 - int2);
						if (dis == 1) {
							if (min == 1 || max == 9) {
								return new HuType(true, null);
							}
							return new HuType(false, null);
						}
						if (dis == 2) {
							return new HuType(true, null);
						}
						return new HuType(false, null);
					}
				}
				return new HuType(false, null);
			}
		}
		return new HuType(false, null);
	}
}
