package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.game.MajiangGame;

public class MajiangHuActionCalculator extends MajiangActionCalculator {

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		if (mjGame.getLegalActions() == null) {
			MajiangActions mas = new MajiangActions();
			mas.addAction(new MajiangAction(null, MajiangAction.COMPLETE, null,
					false));
			return mas;
		}
		return null;
	}

}
