package com.nbcb.majiang.rule.judger.hu.type;

import java.util.Arrays;
import java.util.List;

import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangHuandaHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.HUANDA;
	}

	/**
	 * 对子一样或者暗碰一样都算进去了
	 */
	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		int total = 0;
		List<MajiangUnitCards> listUnitCards = mjHuCards.getListMjUnitCards();
		for (MajiangUnitCards mucs : listUnitCards) {
			if (mucs instanceof MajiangDuiziUnitCards) {
				if (mucs.totalBaida() == 2) {
					total += 2;
				} else if (mucs.totalBaida() == 1) {
					total += 0;
				} else if (mucs.totalBaida() == 0) {
					total += 0;
				}
				continue;
			}
			if (mucs instanceof MajiangAnPengUnitCards) {
				if (mucs.totalBaida() == 3) {
					total += 3;
				} else {
					total += 0;
				}
				continue;
			}
			if (mucs instanceof MajiangAnChiUnitCards) {
				if (mucs.totalBaida() == 3) {
					total += 1;
				} else if (mucs.totalBaida() == 2) {
					MajiangCard mc = mucs.firstNonBaidaCard();
					MajiangCard baidaCard = (MajiangCard) mucs.baidaCards()
							.getHeadCard();
					if (mc.isUnit(MajiangCard.DNXB)
							|| mc.isUnit(MajiangCard.ZFB)
							|| mc.isUnit(MajiangCard.HUA)) {
						continue;
					}
					if (baidaCard.isUnit(MajiangCard.DNXB)
							|| baidaCard.isUnit(MajiangCard.ZFB)
							|| baidaCard.isUnit(MajiangCard.HUA)) {
						continue;
					}
					int intmc = Integer.parseInt(mc.getType().substring(0, 1));
					int intbaida = Integer.parseInt(baidaCard.getType()
							.substring(0, 1));
					String unitMc = mc.getType().substring(1, 2);
					String unitBaida = baidaCard.getType().substring(1, 2);
					if (Math.abs(intmc - intbaida) <= 2) {
						if (unitMc.equals(unitBaida)) {
							total += 1;
						}
					}
				} else if (mucs.totalBaida() == 1) {
					MajiangCards copyMcs = new MajiangCards();
					copyMcs.addTailCards(mucs.toArray());
					MajiangCard mc1 = (MajiangCard) copyMcs.getCard(0);
					MajiangCard mc2 = (MajiangCard) copyMcs.getCard(1);
					MajiangCard mc3 = (MajiangCard) copyMcs.getCard(2);
					if (mc1.getType().length() == 1
							|| mc2.getType().length() == 1
							|| mc3.getType().length() == 1) {
						continue;
					}

					int int1 = mc1.getFirstNumber();
					int int2 = mc2.getFirstNumber();
					int int3 = mc3.getFirstNumber();
					String unit1 = mc1.getSecondType();
					String unit2 = mc2.getSecondType();
					String unit3 = mc3.getSecondType();
					int ints[] = new int[] { int1, int2, int3 };
					Arrays.sort(ints);
					if ((ints[1] - ints[0] == 1) && (ints[2] - ints[1] == 1)) {
						if (unit1.equals(unit2) && unit2.equals(unit3)) {
							total += 1;
						}
					}
					continue;
				} else if (mucs.totalBaida() == 0) {
					total += 0;
				}
				continue;
			}
		}
		if (total == 0) {
			return new HuType(false, null);
		}
		return new HuType(true, total);
	}
}
