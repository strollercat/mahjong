package com.nbcb.majiang.hongzhong;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangDaNonHuaWithNotSameActionCalculator;

public class HongzhongMajiangDaNonHuaActionCalculator extends
		MajiangDaNonHuaWithNotSameActionCalculator {
	
	
	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {

		Actions actions = new MajiangActions();

		

		if (mjGame.getMajiangBlackCards().size() == 6) {
			actions.addAction(new MajiangAction(null, MajiangAction.HUANGPAI,
					null, false));
			return actions;
		}

		if (mingGangActionJudger != null) {
			actions.addActions(mingGangActionJudger.judge(mjGame, mjAction));
		}
		if (pengActionJudger != null) {
			actions.addActions(pengActionJudger.judge(mjGame, mjAction));
		}

		actions = this.getNotSameDaActions(mjGame, mjAction, actions);

		

		Cards cards = new MajiangCards();
		cards.addTailCard(mjGame.getMajiangBlackCards().getHeadCard());
		MajiangAction moMajiangAction = new MajiangAction(
				mjGame.nextPlayer(mjAction.getPlayer()), MajiangAction.MOFRONT,
				cards, false);
		actions.addAction(moMajiangAction);
		return actions;
	}
}
