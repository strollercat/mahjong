package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.MajiangActionJudger;

public class MajiangChiWithGangActionCalculator extends
		MajiangChiActionCalculator {

	private MajiangActionJudger majiangXianGangActionJudger;

	private MajiangActionJudger majiangAnGangActionJudger;

	public void setMajiangAnGangActionJudger(
			MajiangActionJudger majiangAnGangActionJudger) {
		this.majiangAnGangActionJudger = majiangAnGangActionJudger;
	}

	public void setMajiangXianGangActionJudger(
			MajiangActionJudger majiangXianGangActionJudger) {
		this.majiangXianGangActionJudger = majiangXianGangActionJudger;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = super.calculateActionInner(mjGame, mjAction);
		if (majiangXianGangActionJudger != null) {
			actions.addActions(majiangXianGangActionJudger.judge(mjGame,
					mjAction));
		}
		if(majiangAnGangActionJudger!=null){
			actions.addActions(majiangAnGangActionJudger.judge(mjGame,
					mjAction));
		}
		return actions;
	}

}
