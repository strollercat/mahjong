package com.nbcb.core.rule;

import com.nbcb.core.action.Action;

public class DefaultActionJudgerMapping implements ActionJudgerMapping {

	private ActionJudger[] actionJudgers;

	public void setActionJudgers(ActionJudger[] actionJudgers) {
		this.actionJudgers = actionJudgers;
	}

	@Override
	public ActionJudger getActionJudger(Action action) {
		// TODO Auto-generated method stub
		return this.actionJudgers[action.getType()];
	}

}
