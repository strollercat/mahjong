package com.nbcb.core.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;

public interface Rule {

	public void next(Game game, Action action);

//	public boolean canNext(Game game, Action action);
//
//	public Action  [] calNext(Game game, Action action);

}
