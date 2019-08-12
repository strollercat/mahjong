package com.nbcb.web.db;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;

public class ThreeWaterPokerDatabaseActionListener extends DatabaseActionListener {
	
	@Override
	public void listen(Game game, Action action) {
		// TODO Auto-generated method stub
		final Game fGame = game;
		final Action fAction = action;
		if (action.getType() == ThreeWaterPokerAction.COMPLETE) {
			taskExecutor.submit(new Runnable() {
				public void run() {
					ThreeWaterPokerDatabaseActionListener.this.completeAction(fGame, fAction);
				}
			});
		} else if (action.getType() == ThreeWaterPokerAction.ALLOCATE) {
			taskExecutor.submit(new Runnable() {
				public void run() {
					ThreeWaterPokerDatabaseActionListener.this.allocateAction(fGame, fAction);
				}
			});
		}
	}
	
	
}
