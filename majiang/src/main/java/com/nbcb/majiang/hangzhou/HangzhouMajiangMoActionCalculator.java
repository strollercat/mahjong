package com.nbcb.majiang.hangzhou;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangMoActionCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class HangzhouMajiangMoActionCalculator extends
		MajiangMoActionCalculator {

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = new MajiangActions();
		MajiangPlayer mjPlayer = (MajiangPlayer) mjAction.getPlayer();
		HangzhouMajiangGame hzMjGame = (HangzhouMajiangGame) mjGame;

		if (hzMjGame.getBaidaPlayer() == null) { //没有漂才的情况下
			actions.addAction(new MajiangAction(mjPlayer,
					MajiangAction.DANONHUA, null, true));

			if (huActionJudger != null) {
				actions.addActions(huActionJudger.judge(mjGame, mjAction));
			}

			if (anGangActionJudger != null) {
				actions.addActions(anGangActionJudger.judge(mjGame, mjAction));
			}

			if (xianGangActionJudger != null) {
				actions.addActions(xianGangActionJudger.judge(mjGame, mjAction));
			}
			return actions;
		} else { // 漂才的情况下
			Actions huActions = huActionJudger.judge(mjGame, mjAction);
			if (huActions != null) {
				actions.addActions(huActions);
			}
			if (mjPlayer == hzMjGame.getBaidaPlayer()) {
				actions.addAction(new MajiangAction(mjAction.getPlayer(),
						MajiangAction.DANONHUA, null, true));

				if (anGangActionJudger != null) {
					actions.addActions(anGangActionJudger.judge(mjGame,
							mjAction));
				}

				if (xianGangActionJudger != null) {
					actions.addActions(xianGangActionJudger.judge(mjGame,
							mjAction));
				}
				return actions;
			} else {
				if (anGangActionJudger != null) {
					actions.addActions(anGangActionJudger.judge(mjGame,
							mjAction));
				}
				
				MajiangAction ma = new MajiangAction(mjPlayer,
						MajiangAction.DANONHUA, null, true);
				MajiangInnerCards mjInnerCards = mjPlayer
						.getMajiangInnerCards();
				MajiangCards copyCards = new MajiangCards();
				copyCards.addTailCards(mjInnerCards.toArray());
				copyCards.removeCard(mjAction.getCards().getCard(0));
				ma.setAttribute(MajiangAction.CANNOTDA, copyCards.toNumberArray());
				actions.addAction(ma);
				return actions;
			}
		}
	}
}
