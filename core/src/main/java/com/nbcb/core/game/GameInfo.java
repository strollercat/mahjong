package com.nbcb.core.game;

public class GameInfo {

	protected int timeOut;

	protected int playerNum;

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getTimeOut() {
		// TODO Auto-generated method stub
		return this.timeOut;
	}

	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return this.playerNum;
	}

	public String toString() {
		return "timeOut[" + this.timeOut + "] " + "playNum[" + this.playerNum
				+ "] ";
	}

}
