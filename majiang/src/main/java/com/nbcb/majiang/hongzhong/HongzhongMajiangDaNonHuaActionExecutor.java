package com.nbcb.majiang.hongzhong;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangDaNonHuaActionExecutor;

public class HongzhongMajiangDaNonHuaActionExecutor extends
		MajiangDaNonHuaActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		super.execInner(mjGame, mjAction);
		MajiangCard mc = (MajiangCard) mjAction.getCards().getHeadCard();
		if (mc.isBaida()) {
			((HongzhongMajiangGame) mjGame).putDaHongzhong(mjAction.getPlayer()
					.getAccount());
		}
	}

}
