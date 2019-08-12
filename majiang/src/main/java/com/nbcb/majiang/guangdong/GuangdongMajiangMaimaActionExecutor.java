package com.nbcb.majiang.guangdong;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.hongzhong.HongzhongMajiangMaimaActionExecutor;

public class GuangdongMajiangMaimaActionExecutor extends
		HongzhongMajiangMaimaActionExecutor {

	@Override
	protected int maimaNumber(MajiangGame mjGame) {
		return 4;
	}

	@Override
	protected boolean isZhongma(MajiangCard mc) {

		if (mc.getType().length() == 1) {
			if (mc.getType().equals("东")) {
				return true;
			}
			if (mc.getType().equals("中")) {
				return true;
			}
			return false;
		}
		int number = mc.getFirstNumber();
		return number == 1 || number == 5 || number == 9;
	}
}
