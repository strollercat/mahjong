package com.nbcb.majiang.rule.executor;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;

public abstract class MajiangActionExecutor implements ActionExecutor {

	public void exec(Game game, Action action) {
		// TODO Auto-generated method stub
		MajiangGame mjGame = (MajiangGame) game;
		MajiangAction mjAction = (MajiangAction) action;
		this.execInner(mjGame, mjAction);
	}

	abstract public void execInner(MajiangGame mjGame, MajiangAction mjAction);

}
