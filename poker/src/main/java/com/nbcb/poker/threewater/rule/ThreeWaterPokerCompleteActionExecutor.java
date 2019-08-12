package com.nbcb.poker.threewater.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.poker.threewater.ThreeWaterPokerGame;

public class ThreeWaterPokerCompleteActionExecutor implements ActionExecutor {

	private ThreeWaterPokerScoreComputer threeWaterPokerScoreComputer;

	public void setThreeWaterPokerScoreComputer(
			ThreeWaterPokerScoreComputer threeWaterPokerScoreComputer) {
		this.threeWaterPokerScoreComputer = threeWaterPokerScoreComputer;
	}

	@Override
	public void exec(Game game, Action action) {
		ThreeWaterPokerGame twpg = (ThreeWaterPokerGame) game;
		PlayerScores playerScores = threeWaterPokerScoreComputer.computerScore(
				game, twpg.getThreeWaterPokerResults());
		if (playerScores == null) {
			game.setPlayerScores(new DefaultPlayerScores());
			return;
		}
		game.setPlayerScores(playerScores);
	}

}
