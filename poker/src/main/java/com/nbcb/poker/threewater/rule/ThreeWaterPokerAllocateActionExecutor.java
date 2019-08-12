package com.nbcb.poker.threewater.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.poker.card.PokerBlackCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;
import com.nbcb.poker.threewater.ThreeWaterPokerCards;
import com.nbcb.poker.threewater.ThreeWaterPokerGame;
import com.nbcb.poker.threewater.ThreeWaterPokerPlayer;

public class ThreeWaterPokerAllocateActionExecutor implements ActionExecutor {

	private static final Logger logger = LoggerFactory
			.getLogger(ThreeWaterPokerAllocateActionExecutor.class);

	private ThreeWaterPokerShootChooser threeWaterPokerShootChooser;

	private PokerUnitCardsJudger specialPokerUnitCardsJudger;

	public void setSpecialPokerUnitCardsJudger(
			PokerUnitCardsJudger specialPokerUnitCardsJudger) {
		this.specialPokerUnitCardsJudger = specialPokerUnitCardsJudger;
	}

	public void setThreeWaterPokerShootChooser(
			ThreeWaterPokerShootChooser threeWaterPokerShootChooser) {
		this.threeWaterPokerShootChooser = threeWaterPokerShootChooser;
	}

	@Override
	public void exec(Game game, Action action) {
		ThreeWaterPokerGame twpg = (ThreeWaterPokerGame) game;
		ThreeWaterPokerAction twpa = (ThreeWaterPokerAction) action;
		PokerBlackCards pbcs = twpg.getPokerBlackCards();

		for (int i = 0; i < twpg.getActivePlayers(); i++) {
			ThreeWaterPokerPlayer twpp = (ThreeWaterPokerPlayer) twpg
					.getPlayerByIndex(i);
			ThreeWaterPokerCards threeWaterPokerCards = twpp
					.getThreeWaterPokerCards();

			threeWaterPokerCards.addTailCards(pbcs.removeTailCards(13));


			PokerUnitCards pucs = specialPokerUnitCardsJudger
					.judge(threeWaterPokerCards);

			logger.info("specialPokerUnitCards[" + pucs + "]");

			if (pucs != null) {
				pucs.sort();
				threeWaterPokerCards.setSpecialCards(pucs);
			} else {
				twpp.setListCandidate(threeWaterPokerShootChooser
						.chooseBetter(twpp.getThreeWaterPokerCards()));
			}
		}
	}
}
