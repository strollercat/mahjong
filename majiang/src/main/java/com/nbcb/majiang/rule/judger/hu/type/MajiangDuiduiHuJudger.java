package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangChiUnitCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDuiduiHuJudger implements MajiangHuJudger {

	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.DUIDUIHU;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		List<MajiangUnitCards> listMjUnitCards = mjHuCards.getListMjUnitCards();
		if (listMjUnitCards == null || listMjUnitCards.size() == 0) {
			return new HuType(false, null);
		}
		for (MajiangUnitCards mucs : listMjUnitCards) {
			if (mucs instanceof MajiangChiUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangAnChiUnitCards) {
				return new HuType(false, null);
			}
		}
		MajiangDuiziUnitCards mdzucs = mjHuCards.findDuiziUnitCards();
		MajiangCard tmpMc = (MajiangCard) mdzucs.getHeadCard();
		if (tmpMc.isUnit(MajiangCard.ZFB)) {
			return new HuType(true, true);
		}
		if (tmpMc.isUnit(MajiangCard.DNXB)) {
			boolean shooted = ZiHuaOrder.getDnxbOrder(tmpMc.getType())
					.intValue() == Math.abs(mjGame.getDealer().getPlayerOrder()
					- mjPlayer.getPlayerOrder());
			return new HuType(true, shooted);
		}
		return new HuType(true, false);

	}

}
