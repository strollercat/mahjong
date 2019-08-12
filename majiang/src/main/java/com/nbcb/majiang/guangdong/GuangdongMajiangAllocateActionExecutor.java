package com.nbcb.majiang.guangdong;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangAllocateActionExecutor;

public class GuangdongMajiangAllocateActionExecutor extends
		MajiangAllocateActionExecutor {

	public void exec(Game game, Action action) {
		super.exec(game, action);

		/**
		 * for auto dapai
		 */
		MajiangGame mjGame = (MajiangGame) game;
		for (int i = 0; i < game.getActivePlayers(); i++) {
			mjGame.putLastWaitUserActionTime(game.getPlayerByIndex(i)
					.getAccount(), System.currentTimeMillis() + 2 * 60 * 60
					* 1000);
		}

	}
}
