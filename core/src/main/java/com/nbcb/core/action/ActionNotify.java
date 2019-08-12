package com.nbcb.core.action;

import com.nbcb.core.game.Game;

public interface ActionNotify {
	
	public void notify(Game game, Action action);

	public void addActionlistener(ActionListener actionListener);
}
