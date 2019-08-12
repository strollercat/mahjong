package com.nbcb.core.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.ActionNotify;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;

public abstract class AbstractRule implements Rule {

	protected ActionExecutorMapping actionExecutorMapping;

	protected ActionCalculatorMapping actionCalculatorMapping;

	protected ActionNotify actionNotify;

	public void setActionNotify(ActionNotify actionNotify) {
		this.actionNotify = actionNotify;
	}

	public void setActionCalculatorMapping(
			ActionCalculatorMapping actionCalculatorMapping) {
		this.actionCalculatorMapping = actionCalculatorMapping;
	}

	public void setActionExecutorMapping(
			ActionExecutorMapping actionExecutorMapping) {
		this.actionExecutorMapping = actionExecutorMapping;
	}


	protected Actions calNext(Game game, Action action) {
		return actionCalculatorMapping.getActionCalculator(action)
				.calculateAction(game, action);
	}

}
