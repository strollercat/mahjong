package com.nbcb.majiang.rule.calculator;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.MajiangActionJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangMoActionCalculator extends MajiangActionCalculator {

	protected MajiangActionJudger huActionJudger = null;

	protected MajiangActionJudger anGangActionJudger = null;

	protected MajiangActionJudger xianGangActionJudger = null;

	public void setHuActionJudger(MajiangActionJudger huActionJudger) {
		this.huActionJudger = huActionJudger;
	}

	public void setAnGangActionJudger(MajiangActionJudger anGangActionJudger) {
		this.anGangActionJudger = anGangActionJudger;
	}

	public void setXianGangActionJudger(MajiangActionJudger xianGangActionJudger) {
		this.xianGangActionJudger = xianGangActionJudger;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = new MajiangActions();
		MajiangPlayer mjPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangCard mjCard = (MajiangCard) mjAction.getCards().getHeadCard();

		if (mjCard.isUnit(MajiangCard.HUA) && !mjCard.isBaida()) {
			Cards cards = new MajiangCards();
			cards.addHeadCard(mjCard);
			actions.addAction(new MajiangAction(mjAction.getPlayer(),
					MajiangAction.DAHUA, cards, false));
			return actions;
		}
		actions.addAction(new MajiangAction(mjPlayer, MajiangAction.DANONHUA,
				null, true));

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
	}

}
