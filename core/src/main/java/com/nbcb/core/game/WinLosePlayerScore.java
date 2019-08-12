package com.nbcb.core.game;

import com.nbcb.core.user.Player;

public class WinLosePlayerScore {

	private Player winPlayer;

	private Player losePlayer;

	private int score;

	public WinLosePlayerScore(Player winPlayer, Player losePlayer, int score) {
		this.winPlayer = winPlayer;
		this.losePlayer = losePlayer;
		this.score = score;
	}

	public Player getWinPlayer() {
		return winPlayer;
	}

	public Player getLosePlayer() {
		return losePlayer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
