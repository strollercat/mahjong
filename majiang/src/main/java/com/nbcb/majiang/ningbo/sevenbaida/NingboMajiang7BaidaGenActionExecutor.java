package com.nbcb.majiang.ningbo.sevenbaida;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangActionExecutor;

public class NingboMajiang7BaidaGenActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub

		NingboMajiang7BaidaGame ningboMajiang7BaidaGame = (NingboMajiang7BaidaGame) mjGame;

		ningboMajiang7BaidaGame.putGen(mjAction.getPlayer().getAccount(), true);

	}

}
