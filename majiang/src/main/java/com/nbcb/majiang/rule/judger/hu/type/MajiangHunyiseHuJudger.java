package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangHunyiseHuJudger implements MajiangHuJudger {

	
	
	
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		String unit = null;
		boolean hasZi = false;
		for (int i = 0; i < mjHuCards.size(); i++) {
			MajiangCard mc = (MajiangCard) mjHuCards.getCard(i);
			if (mc.isBaida()) {
				continue;
			}
			if (mc.isUnit(MajiangCard.ZFB)) {
				hasZi = true;
				continue;
			}
			if (mc.isUnit(MajiangCard.DNXB)) {
				hasZi = true;
				continue;
			}
			if (mc.isUnit(MajiangCard.HUA)) {
				continue;
			}
			String tmpUnit = mc.getSecondType();
			if (unit == null) {
				unit = tmpUnit;
			} else {
				if (!tmpUnit.equals(unit)) {
					return new HuType(false, null);
				}
			}
		}
		if (unit == null) {
			return new HuType(false, null);
		}
		if (hasZi) {
			return new HuType(true, unit);
		}
		return new HuType(false, null);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.HUNYISE;
	}

}
