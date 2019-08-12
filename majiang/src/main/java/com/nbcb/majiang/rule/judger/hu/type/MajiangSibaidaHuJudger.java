package com.nbcb.majiang.rule.judger.hu.type;

import java.util.List;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangSibaidaHuJudger implements MajiangHuJudger {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.SIBAIDA;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		int total = 0;
		for (int i = 0; i < mjHuCards.size(); i++) {
			MajiangCard mc = (MajiangCard) mjHuCards.getCard(i);
			if (mc.isBaida()) {
				total++;
			}
		}
		if (total == 4) {
			return new HuType(true, null);
		}
		return new HuType(false,null);
	}

}
