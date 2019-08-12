package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.game.MajiangGame;

public class MajiangPengActionCalculator extends MajiangActionCalculator {

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = new MajiangActions();
		// Cards cards = new MajiangCards();
		// cards.addTailCard(mjGame.getMajiangBlackCards().getHeadCard());
		actions.addAction(new MajiangAction(mjAction.getPlayer(), MajiangAction.DANONHUA, null, true));
		return actions;
	}

}
