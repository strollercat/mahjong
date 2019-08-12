package com.nbcb.core.rule;

import com.nbcb.core.action.Action;

public abstract class AbstractActionCalculatorMapping implements
		ActionCalculatorMapping {

	private ActionCalculator[] actionCalculators;

	public void setActionCalculators(ActionCalculator[] actionCalculators) {
		this.actionCalculators = actionCalculators;
	}

	public ActionCalculator getActionCalculator(Action action) {
		return actionCalculators[action.getType()];
	}
}
