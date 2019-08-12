package com.nbcb.core.rule;

import com.nbcb.core.action.Action;

public interface ActionExecutorMapping {
	public ActionExecutor getActionExecutor(Action action);
}
