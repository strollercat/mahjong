package com.nbcb.poker.threewater.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.action.DefaultActions;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionCalculator;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;

public class ThreeWaterPokerAllocateActionCalculator implements
		ActionCalculator {

	@Override
	public Actions calculateAction(Game game, Action action) {
		// TODO Auto-generated method stub
		DefaultActions das = new DefaultActions();
		
		for(int i = 0;i< game.getActivePlayers();i++){
			das.addAction(new ThreeWaterPokerAction(game.getPlayerByIndex(i),ThreeWaterPokerAction.SHOOT,null,true));
		}
		return das;
	}

}
