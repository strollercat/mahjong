package com.nbcb.core.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractPlayerScores implements PlayerScores {

	private Map<String, Integer> mapScore = new HashMap<String, Integer>();

	@Override
	public int getScore(String account) {
		// TODO Auto-generated method stub
		Integer intRet = mapScore.get(account);
		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();
	}

	@Override
	public Set<String> accountSet() {
		// TODO Auto-generated method stub
		return mapScore.keySet();
	}

	@Override
	public void addWinLosePlayerScore(WinLosePlayerScore winLosePlayerScore) {
		// TODO Auto-generated method stub
		
		if(winLosePlayerScore == null) {
			return ;
		}
		
		String winAccount = winLosePlayerScore.getWinPlayer().getAccount();
		String loseAccount = winLosePlayerScore.getLosePlayer().getAccount();
		int score = winLosePlayerScore.getScore();
		
		Integer intValue = this.mapScore.get(winAccount);
		if(intValue == null) {
			this.mapScore.put(winAccount, score);
		}else {
			this.mapScore.put(winAccount, intValue.intValue() + score);
		}
		
		intValue = this.mapScore.get(loseAccount);
		if(intValue == null) {
			this.mapScore.put(loseAccount, -score);
		}else {
			this.mapScore.put(loseAccount, intValue.intValue()-score);
		}
	}

	@Override
	public void addPlayerScores(PlayerScores playerScores) {
		if (playerScores == null) {
			return;
		}
		Set<String> keySet = playerScores.accountSet();
		if (keySet != null && keySet.size() != 0) {
			for (String account : keySet) {
				Integer intValue = mapScore.get(account);
				int score = playerScores.getScore(account);
				if (intValue == null) {
					mapScore.put(account, score);
				} else {
					mapScore.put(account, score + intValue.intValue());
				}
			}
		}
	}

	

	@Override
	public void setPlayerScore(String account, int score) {
		this.mapScore.put(account, score);
	}
	
	@Override
	public void removePlayerScoreByAccount(String account) {
		this.mapScore.remove(account);
	}


	@Override
	public Map format() {
		return this.mapScore;
	}

	@Override
	public String toString() {
		return this.mapScore.toString();
	}

}
