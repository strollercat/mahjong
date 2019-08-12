package com.nbcb.majiang.rule.executor;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;

public class MajiangHuangPaiActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangHuResult mjHuResult = new MajiangHuResult();
		mjHuResult.setHuType(MajiangAction.HUANGPAI);
		mjHuResult.setFan(0);
		mjHuResult.setDetails("黄牌");
		mjGame.addMajiangHuResult(mjHuResult);
	}

}
