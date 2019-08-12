package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangZimoHuJudger implements MajiangHuJudger {

	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		boolean ret = (huType == MajiangAction.MOFRONTHU
				|| huType == MajiangAction.MOBACKHU || huType == MajiangAction.QIANGGANGHU);
		return new HuType(ret, null);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.ZIMO;
	}

}
