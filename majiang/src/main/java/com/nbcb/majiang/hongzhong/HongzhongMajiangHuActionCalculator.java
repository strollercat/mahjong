package com.nbcb.majiang.hongzhong;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangActionCalculator;

public class HongzhongMajiangHuActionCalculator extends MajiangActionCalculator {

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		MajiangActions mas = new MajiangActions();
		mas.addAction(new MajiangAction(null, MajiangAction.MAIMA, null, false));
		return mas;
	}

}
