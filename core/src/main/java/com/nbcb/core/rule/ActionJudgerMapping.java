package com.nbcb.core.rule;

import com.nbcb.core.action.Action;

public interface ActionJudgerMapping {
	public ActionJudger getActionJudger(Action action);
}
