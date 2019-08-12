package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangGangtoukaihuaHuJudger implements MajiangHuJudger {

	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		return new HuType(huType == MajiangAction.MOBACKHU, null);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.GANGTOUKAIHUA;
	}

}
