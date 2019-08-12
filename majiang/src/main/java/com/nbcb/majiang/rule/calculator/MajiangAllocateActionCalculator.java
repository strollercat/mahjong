package com.nbcb.majiang.rule.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;

public class MajiangAllocateActionCalculator extends MajiangActionCalculator {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangAllocateActionCalculator.class);

	// public Action[] calculateAction(Game game, Action action) {
	// TODO Auto-generated method stub
	// logger.debug("MajiangAllocateActionCalculator calculateAction "
	// + action);
	// MajiangGame mg = (MajiangGame) game;
	// MajiangPlayer mp = mg.getDealer();
	//
	// Card cards[] = new Card[1];
	// MajiangBlackCards mbcs = mg.getMajiangBlackCards();
	// cards[0] = mbcs.getTail();
	//
	// MajiangAction ma = new MajiangAction(mp, MajiangAction.MOFRONT, cards,
	// false);
	// return new Action[] { ma };
	// }

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Cards cards = new MajiangCards();
		MajiangBlackCards mbcs = mjGame.getMajiangBlackCards();
		cards.addTailCard(mbcs.getHeadCard());

		MajiangAction ma = new MajiangAction(mjGame.getDealer(),
				MajiangAction.MOFRONT, cards, false);
		Actions actions = new MajiangActions();
		actions.addAction(ma);
		return actions;
	}

}
