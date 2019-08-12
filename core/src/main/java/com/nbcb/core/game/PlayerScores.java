package com.nbcb.core.game;

import java.util.Set;

import com.nbcb.core.server.Formatter;
import com.nbcb.core.user.Player;

public interface PlayerScores extends Formatter{

	public int getScore(String account);

	public Set<String> accountSet();

	public void addWinLosePlayerScore(WinLosePlayerScore winLosePlayerScore);

	public void addPlayerScores(PlayerScores playerScores);

	public void setPlayerScore(String account, int score);

	public void removePlayerScoreByAccount(String account);

}
