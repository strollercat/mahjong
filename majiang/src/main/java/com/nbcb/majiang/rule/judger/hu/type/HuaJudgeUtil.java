package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangHuaUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.user.MajiangPlayer;

public class HuaJudgeUtil {

	private static boolean judgeZhengHua(int dealerOrder, int playerOrder,
			MajiangCard mc) {

		Integer order = ZiHuaOrder.getCxqdOrder(mc.getType());
		if (order == null) {
			order = ZiHuaOrder.getMljzOrder(mc.getType());
		}
		int distance = (playerOrder - dealerOrder + 4) % 4;
		return order.intValue() == distance;
	}

	public static HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer,
			MajiangHuaUnitCards mhucs) {
		if (mhucs == null) {
			return new HuType(false, null);
		}
		int size = mhucs.size();
		int sequence = 0;
		int zhengHua = 0;
		int yeHua = 0;
		for (int i = 0; i < size; i++) {
			MajiangCard mc = (MajiangCard) mhucs.getCard(i);
			int dealerOrder = mjGame.getDealer().getPlayerOrder();
			if (judgeZhengHua(dealerOrder, mjPlayer.getPlayerOrder(), mc)) {
				zhengHua++;
			} else {
				yeHua++;
			}
		}
		if (size < 4) {
			return new HuType(true, new HuaDetail(sequence, zhengHua, yeHua));
		}
		// logger.info("### fullCxqd "+ZiHuaOrder.fullCxqd(mhucs));
		if (ZiHuaOrder.fullCxqd(mhucs)) {
			sequence++;
		}
		// logger.info("### fullMljz "+ZiHuaOrder.fullMljz(mhucs));
		if (ZiHuaOrder.fullMljz(mhucs)) {
			sequence++;
		}
		return new HuType(true, new HuaDetail(sequence, zhengHua, yeHua));
	}
}
