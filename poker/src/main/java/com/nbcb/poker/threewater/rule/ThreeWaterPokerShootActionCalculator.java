package com.nbcb.poker.threewater.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.action.DefaultActions;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionCalculator;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;
import com.nbcb.poker.threewater.ThreeWaterPokerPlayer;

public class ThreeWaterPokerShootActionCalculator implements ActionCalculator {

	@Override
	public Actions calculateAction(Game game, Action action) {
//		for(int i = 0;i< game.getActivePlayers();i++){
//			ThreeWaterPokerPlayer pp = (ThreeWaterPokerPlayer)game.getPlayerByIndex(i);
//		}
		
		DefaultActions das = new DefaultActions();
		das.addAction(new ThreeWaterPokerAction(null,ThreeWaterPokerAction.COMPLETE,null,false));
		return das;
	}

}
