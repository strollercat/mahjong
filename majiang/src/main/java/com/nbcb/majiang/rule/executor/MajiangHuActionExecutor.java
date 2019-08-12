package com.nbcb.majiang.rule.executor;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;

public class MajiangHuActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangHuResult mjHuResult = (MajiangHuResult) mjAction
				.getAttribute(MajiangAction.MJHURESULT);
		mjGame.addMajiangHuResult(mjHuResult);
	}

}
