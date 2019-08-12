package com.nbcb.majiang.hongzhong;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangActionCalculator;

public class HongzhongMajiangMaimaActionCalculator extends
		MajiangActionCalculator {

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangActions mas = new MajiangActions();
		mas.addAction(new MajiangAction(null, MajiangAction.COMPLETE, null,
				false));
		return mas;
	}

}
