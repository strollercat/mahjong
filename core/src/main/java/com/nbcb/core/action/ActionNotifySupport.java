package com.nbcb.core.action;

import com.nbcb.core.game.Game;

public class ActionNotifySupport implements ActionNotify {

	private ActionListener[] actionListeners;

	public void setActionListeners(ActionListener[] actionListeners) {
		this.actionListeners = actionListeners;
	}

	@Override
	public void notify(Game game, Action action) {
		// TODO Auto-generated method stub
		for (ActionListener actionListener : actionListeners) {
			actionListener.listen(game, action);
		}
	}

	@Override
	public void addActionlistener(ActionListener actionListener) {
		// TODO Auto-generated method stub

	}

}
