package com.nbcb.majiang.hongzhong;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangAnGangActionCalculator;

public class HongzhongMajiangAnGangActionCalculator extends
		MajiangAnGangActionCalculator {

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = new MajiangActions();
		Cards cards = new MajiangCards();
		if (mjGame.getMajiangBlackCards().size() == 6) {
			actions.addAction(new MajiangAction(null, MajiangAction.HUANGPAI,
					null, false));
			return actions;
		}

		cards.addTailCard(mjGame.getMajiangBlackCards().getTailCard());
		actions.addAction(new MajiangAction(mjAction.getPlayer(),
				MajiangAction.MOBACK, cards, false));
		return actions;
	}

}
