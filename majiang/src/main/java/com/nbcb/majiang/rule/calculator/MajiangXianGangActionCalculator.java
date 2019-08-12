package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.MajiangActionJudger;

public class MajiangXianGangActionCalculator extends MajiangActionCalculator {

	private MajiangActionJudger qiangGangHu;

	public void setQiangGangHu(MajiangActionJudger qiangGangHu) {
		this.qiangGangHu = qiangGangHu;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = new MajiangActions();
		if (qiangGangHu != null) {
			Actions as = qiangGangHu.judge(mjGame, mjAction);
			if (as != null) {
				actions.addActions(as);
			}
		}

		if (actions.size() == 0 && mjGame.getMajiangBlackCards().size() == 0) {
			actions.addAction(new MajiangAction(null, MajiangAction.HUANGPAI,
					null, false));
		} else {
			Cards cards = new MajiangCards();
			cards.addTailCard(mjGame.getMajiangBlackCards().getTailCard());
			actions.addAction(new MajiangAction(mjAction.getPlayer(),
					MajiangAction.MOBACK, cards, false));
		}
		return actions;
	}

}
