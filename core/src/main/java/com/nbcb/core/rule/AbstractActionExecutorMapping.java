package com.nbcb.core.rule;

import com.nbcb.core.action.Action;

public class AbstractActionExecutorMapping implements ActionExecutorMapping {

	private ActionExecutor[] actionExecutors;

	public void setActionExecutors(ActionExecutor[] actionExecutors) {
		this.actionExecutors = actionExecutors;
	}

	@Override
	public ActionExecutor getActionExecutor(Action action) {
		// TODO Auto-generated method stub
		return actionExecutors[action.getType()];
	}

}
