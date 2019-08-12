package com.nbcb.majiang.rule.executor;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.ScoreComputer;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;

public class MajiangCompleteActionExecutor extends MajiangActionExecutor {

	private ScoreComputer scoreComputer;

	public void setScoreComputer(ScoreComputer scoreComputer) {
		this.scoreComputer = scoreComputer;
	}

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangHuResults mjHuResults = mjGame.getMajiangHuResults();
		PlayerScores playerScores = scoreComputer.computerScore(mjGame,
				mjHuResults);
		if (playerScores == null) {
			mjGame.setPlayerScores(new DefaultPlayerScores());
		} else {
			mjGame.setPlayerScores(playerScores);
		}
	}

}
