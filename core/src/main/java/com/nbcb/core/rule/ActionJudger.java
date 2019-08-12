package com.nbcb.core.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;

public interface ActionJudger {
	public Actions judge(Game game,Action action);
}
