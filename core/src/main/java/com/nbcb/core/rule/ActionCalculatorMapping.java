package com.nbcb.core.rule;

import com.nbcb.core.action.Action;

public interface ActionCalculatorMapping {
	
	public ActionCalculator getActionCalculator(Action action);
}
