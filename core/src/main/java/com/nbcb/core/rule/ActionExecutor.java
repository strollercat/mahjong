package com.nbcb.core.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;

public interface ActionExecutor {
	public void exec(Game game, Action action);
}
