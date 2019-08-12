package com.nbcb.majiang.rule.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionCalculator;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;

public abstract class MajiangActionCalculator implements ActionCalculator {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangActionCalculator.class);
	
	public Actions calculateAction(Game game, Action action) {
		// TODO Auto-generated method stub
		return calculateActionInner((MajiangGame) game, (MajiangAction) action);
	}

	abstract protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction);

}
