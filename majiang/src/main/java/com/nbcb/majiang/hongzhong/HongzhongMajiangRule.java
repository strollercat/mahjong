package com.nbcb.majiang.hongzhong;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.rule.MajiangRule;

public class HongzhongMajiangRule extends MajiangRule {

	private boolean hasHu(Action[] arrAction) {
		for (Action a : arrAction) {
			if (a.getType() == MajiangAction.MOFRONTHU
					|| a.getType() == MajiangAction.MOBACKHU
					|| a.getType() == MajiangAction.QIANGGANGHU) {
				return true;
			}
		}
		return false;
	}

	protected Action translateNoAction(Game game, Action action) {
		// logger.info("### enter into the translateNoAction");
		Actions legalActions = game.getLegalActions();
		Action[] arrAction = legalActions.getActionsByPlayer(action.getPlayer()
				.getAccount());
		if (arrAction == null || arrAction.length == 0) {
			return null;
		}
		// 如果当前用户有胡的，过指令失效!!!
		if (this.hasHu(arrAction)) {
			return null;
		}
		return super.translateNoAction(game, action);
	}
}
