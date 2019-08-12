package com.nbcb.core.game;

public class GameResult {
	private Object gameResultDetail;
	private PlayerScores PlayerScores;

	public GameResult(Object gameResultDetail, PlayerScores PlayerScores) {
		this.gameResultDetail = gameResultDetail;
		this.PlayerScores = PlayerScores;
	}

	public Object getGameResultDetail() {
		return gameResultDetail;
	}

	public PlayerScores getPlayerScores() {
		return PlayerScores;
	}

}
