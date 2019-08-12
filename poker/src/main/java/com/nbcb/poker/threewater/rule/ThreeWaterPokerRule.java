package com.nbcb.poker.threewater.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.poker.action.PokerAction;
import com.nbcb.poker.game.PokerGame;
import com.nbcb.poker.rule.PokerRule;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;

public class ThreeWaterPokerRule extends PokerRule {

	@Override
	protected PokerAction judgeLegalAction(PokerGame pokerGame,
			PokerAction pokerAction) {
		
		
		if(pokerAction.getType() == ThreeWaterPokerAction.SHOOT){
			Actions actions = pokerGame.getLegalActions();
			Action  []retActions = actions.getActionsByTypeAndPlayer(pokerAction.getPlayer().getAccount(),ThreeWaterPokerAction.SHOOT );
			if(retActions == null || retActions.length == 0){
				return null;
			}
//			actions.removeActionByTypeAndPlayer(pokerAction.getPlayer().getAccount(),ThreeWaterPokerAction.SHOOT );
			return pokerAction;
		}
		
		return pokerAction;
	}

}
