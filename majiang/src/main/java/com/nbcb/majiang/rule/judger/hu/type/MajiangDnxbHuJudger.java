package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangDnxbHuJudger implements MajiangHuJudger {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangDnxbHuJudger.class);

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.DNXB;
	}

	public static class QuanWeiFeng {
		private int quanfeng;
		private int weifeng;

		public QuanWeiFeng(int quanfeng, int weifeng) {
			this.quanfeng = quanfeng;
			this.weifeng = weifeng;
		}

		public int getQuanfeng() {
			return quanfeng;
		}

		public int getWeifeng() {
			return weifeng;
		}
	}

	/**
	 * 如果三个是白搭,先不去算它
	 */

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		int weifeng = 0;
		int quanfeng = 0;
		List<MajiangUnitCards> listUnitCards = mjHuCards.getListMjUnitCards();
		for (MajiangUnitCards mucs : listUnitCards) {
			if (mucs instanceof MajiangAnPengUnitCards
					|| mucs instanceof MajiangAnGangUnitCards
					|| mucs instanceof MajiangPengUnitCards
					|| mucs instanceof MajiangXianGangUnitCards
					|| mucs instanceof MajiangMingGangUnitCards) {
				if (mucs.totalBaida() == 3) {
					continue;
				}
				String type = mucs.firstNonBaidaCard().getType();

				Integer order = ZiHuaOrder.getDnxbOrder(type);

				if (order == null) {
					continue;
				}

				MajiangGameInfo mgi = (MajiangGameInfo) mjGame.getGameInfo();
//				logger.info("### order[" + order + "]quan[" + mgi.getQuan()
//						+ "]dealer[" + mjGame.getDealer().getPlayerOrder()
//						+ "]");
				int quan = mgi.getQuan();
				if (quan == order.intValue()) {
					quanfeng += 1;
				}
				if (order.intValue() == ((mjPlayer.getPlayerOrder()
						- mjGame.getDealer().getPlayerOrder() + 4) % 4)) {
					weifeng += 1;
				}
			}
		}
		if (weifeng == 0 && quanfeng == 0) {
			return new HuType(false, null);
		}
		return new HuType(true, new QuanWeiFeng(quanfeng, weifeng));
	}

}
