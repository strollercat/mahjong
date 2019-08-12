package com.nbcb.core.room;

public class RoomInfo {

	private String name;

	private int totalJu;

	private int playerNum;

	private int timeRange;

	public int getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(int timeRange) {
		this.timeRange = timeRange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int getTotalJu() {
		return totalJu;
	}

	public void setTotalJu(int totalJu) {
		this.totalJu = totalJu;
	}

	@Override
	public String toString() {
		return "name [" + this.name + "]" + "totalJu[" + this.totalJu + "]"
				+ "playerNum[" + this.playerNum + "]";
	}
}
