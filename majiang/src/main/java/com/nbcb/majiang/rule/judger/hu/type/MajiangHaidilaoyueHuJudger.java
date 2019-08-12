package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangHaidilaoyueHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.HAIDILAOYUE;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		if (huType != MajiangAction.MOFRONTHU
				&& huType != MajiangAction.MOBACKHU) {
			return new HuType(false, null);
		}
		return new HuType(mjGame.getMajiangBlackCards().size() == 0, null);
	}

}
