package com.nbcb.majiang.ningbo;

import com.nbcb.majiang.card.MajiangHuaUnitCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.HuaJudgeUtil;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiangHuaJudgeUtil {

	public static int judge(MajiangGame mjGame, MajiangPlayer mjPlayer) {

		MajiangMiddleCards mmcs = mjPlayer.getMajiangMiddleCards();
		if (mmcs == null) {
			return 0;
		}
		MajiangHuaUnitCards mhucs = mmcs.getMajiangHuaUnitCards();
		if (mhucs == null) {
			return 0;
		}
		HuType huType = HuaJudgeUtil.judge(mjGame, mjPlayer, mhucs);
		if (!huType.isShooted()) {
			return 0;
		}
		HuaDetail huaDetail = (HuaDetail) huType.getDetail();
		NingboMajiangRoomInfo ri = (NingboMajiangRoomInfo) mjGame.getRoom()
				.getRoomInfo();

		return huaDetail.getSequence() + huaDetail.getZhengHua()
				* ri.getJinhua() + huaDetail.getYeHua() * ri.getYehua();
	}
}
