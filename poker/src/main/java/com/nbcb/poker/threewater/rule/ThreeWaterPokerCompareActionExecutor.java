package com.nbcb.poker.threewater.rule;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.poker.card.PokerUnitCardsCompare;
import com.nbcb.poker.threewater.ThreeWaterPokerGame;
import com.nbcb.poker.threewater.ThreeWaterPokerPlayer;
import com.nbcb.poker.threewater.ThreeWaterPokerResult;
import com.nbcb.poker.threewater.ThreeWaterPokerResults;

public class ThreeWaterPokerCompareActionExecutor implements ActionExecutor {

	private PokerUnitCardsCompare normalPokerUnitCardsCompare;

	private PokerUnitCardsCompare specialPokerUnitCardsCompare;

	public void setNormalPokerUnitCardsCompare(
			PokerUnitCardsCompare normalPokerUnitCardsCompare) {
		this.normalPokerUnitCardsCompare = normalPokerUnitCardsCompare;
	}

	public void setSpecialPokerUnitCardsCompare(
			PokerUnitCardsCompare specialPokerUnitCardsCompare) {
		this.specialPokerUnitCardsCompare = specialPokerUnitCardsCompare;
	}

	@Override
	public void exec(Game game, Action action) {

		ThreeWaterPokerResults threeWaterPokerResults = new ThreeWaterPokerResults();
		ThreeWaterPokerGame threeWaterPokerGame = (ThreeWaterPokerGame) game;
		threeWaterPokerGame.setThreeWaterPokerResults(threeWaterPokerResults);

		for (int i = 0; i < game.getActivePlayers(); i++) {
			for (int j = 0; j < game.getActivePlayers(); j++) {
				ThreeWaterPokerPlayer pp1 = (ThreeWaterPokerPlayer) game
						.getPlayerByIndex(i);
				ThreeWaterPokerPlayer pp2 = (ThreeWaterPokerPlayer) game
						.getPlayerByIndex(j);
				if (pp1 != pp2) {
					if (pp1.getThreeWaterPokerCards().isSpecial()
							&& !pp2.getThreeWaterPokerCards().isSpecial()) {
						threeWaterPokerResults
								.addThreeWaterPokerResult(new ThreeWaterPokerResult(
										pp1, pp2, 0, 0, 0, 0));
						continue;
					}
					if (!pp1.getThreeWaterPokerCards().isSpecial()
							&& pp2.getThreeWaterPokerCards().isSpecial()) {
						threeWaterPokerResults
								.addThreeWaterPokerResult(new ThreeWaterPokerResult(
										pp1, pp2, 0, 0, 0, 0));
						continue;
					}
					if (pp1.getThreeWaterPokerCards().isSpecial()
							&& pp2.getThreeWaterPokerCards().isSpecial()) {

						int specialResult = specialPokerUnitCardsCompare
								.compare(pp1.getThreeWaterPokerCards()
										.getSpecialCards(), pp2
										.getThreeWaterPokerCards()
										.getSpecialCards());
						threeWaterPokerResults
						.addThreeWaterPokerResult(new ThreeWaterPokerResult(
								pp1, pp2, specialResult, 0, 0, 0));
						continue;
					}
					
					int firstResult =  normalPokerUnitCardsCompare.compare(pp1.getThreeWaterPokerCards().getFirstPokerCards(), pp2.getThreeWaterPokerCards().getFirstPokerCards());
					int secondResult = normalPokerUnitCardsCompare.compare(pp1.getThreeWaterPokerCards().getSecondPokerCards(), pp2.getThreeWaterPokerCards().getSecondPokerCards());
					int thirdResult = normalPokerUnitCardsCompare.compare(pp1.getThreeWaterPokerCards().getThirdPokerCards(), pp2.getThreeWaterPokerCards().getThirdPokerCards());
					threeWaterPokerResults
					.addThreeWaterPokerResult(new ThreeWaterPokerResult(
							pp1, pp2, 0, firstResult, secondResult,thirdResult));
					
				}
			}
		}

	}
}
