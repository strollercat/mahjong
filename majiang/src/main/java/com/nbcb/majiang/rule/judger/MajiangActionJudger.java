package com.nbcb.majiang.rule.judger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionJudger;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.game.MajiangGame;

public abstract class MajiangActionJudger implements ActionJudger {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangActionJudger.class);

	public Actions judge(Game game, Action action) {
		// TODO Auto-generated method stub
		logger.debug("### enter into " + this.getClass());
		return this.judgeInner((MajiangGame) game, (MajiangAction) action);
	}

	abstract protected MajiangActions judgeInner(MajiangGame mjGame,
			MajiangAction mjAction);

}
