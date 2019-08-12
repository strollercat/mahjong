package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangPaiHuJudger implements MajiangHuJudger {

	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.PAIHU;
	}

	public static class PaihuDetail {
		public static int TWOBAIDA = 1;
		public static int ZFB = 2;
		public static int WF = 3;
		public static int QF = 4;
		public static int NORMAL = 5;

		private int duiziType;

		public int getDuiziType() {
			return duiziType;
		}

		public PaihuDetail(int duiziType) {
			this.duiziType = duiziType;
		}
	}

	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		List<MajiangUnitCards> listMjUnitCards = mjHuCards.getListMjUnitCards();
		for (MajiangUnitCards mucs : listMjUnitCards) {
			if (mucs instanceof MajiangPengUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangAnPengUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangMingGangUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangAnGangUnitCards) {
				return new HuType(false, null);
			}
			if (mucs instanceof MajiangXianGangUnitCards) {
				return new HuType(false, null);
			}
		}
		MajiangDuiziUnitCards mdzucs = mjHuCards.findDuiziUnitCards();
		int totalBaida = mdzucs.totalBaida();
		MajiangCard mc = null;
		if (totalBaida == 2) {
			return new HuType(true, new PaihuDetail(PaihuDetail.TWOBAIDA));
		} else {
			mc = mdzucs.firstNonBaidaCard();
		}
		if (mc.isUnit(MajiangCard.ZFB)) {
			return new HuType(true, new PaihuDetail(PaihuDetail.ZFB));
		}
		if (mc.isUnit(MajiangCard.DNXB)) {
			int dis = Math.abs(mjPlayer.getPlayerOrder()
					- mjGame.getDealer().getPlayerOrder());
			int dis1 = ZiHuaOrder.getDnxbOrder(mc.getType());
			if (dis == dis1) {
				return new HuType(true, new PaihuDetail(PaihuDetail.WF));
			}
			MajiangGameInfo mjGameInfo = (MajiangGameInfo) mjGame.getGameInfo();
			if (dis1 == mjGameInfo.getQuan()) {
				return new HuType(true, new PaihuDetail(PaihuDetail.QF));
			}
		}
		return new HuType(true, new PaihuDetail(PaihuDetail.NORMAL));
	}
}
