package com.nbcb.majiang.ningbo;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangDaHuaActionExecutor;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiangDaHuaActionExecutor extends
		MajiangDaHuaActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		super.execInner(mjGame, mjAction);

		mjAction.setAttribute("HUATAISHU", NingboMajiangHuaJudgeUtil.judge(
				mjGame, (MajiangPlayer) mjAction.getPlayer()));
	}
}
