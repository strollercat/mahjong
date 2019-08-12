package com.nbcb.poker.threewater;

public class ThreeWaterPokerResult {
	private ThreeWaterPokerPlayer player1;
	private ThreeWaterPokerPlayer player2;

	private int specialResult;
	private int firstResult;
	private int secondResult;
	private int thirdResult;

	public ThreeWaterPokerResult(ThreeWaterPokerPlayer player1,
			ThreeWaterPokerPlayer player2, int specialResult, int firstResult,
			int secondResult, int thirdResult) {
		this.specialResult = specialResult;
		this.firstResult = firstResult;
		this.secondResult = secondResult;
		this.thirdResult = thirdResult;
		this.player1 = player1;
		this.player2 = player2;
	}

	public int getSpecialResult() {
		return specialResult;
	}

	public ThreeWaterPokerPlayer getPlayer1() {
		return player1;
	}

	public ThreeWaterPokerPlayer getPlayer2() {
		return player2;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getSecondResult() {
		return secondResult;
	}

	public int getThirdResult() {
		return thirdResult;
	}

}
