package com.nbcb.poker.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.RoomStopJudger;
import com.nbcb.core.rule.AbstractRule;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.poker.action.PokerAction;
import com.nbcb.poker.game.PokerGame;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;

public abstract class PokerRule extends AbstractRule {

	private static final Logger logger = LoggerFactory
			.getLogger(PokerRule.class);

	protected RoomStopJudger roomStopJudger;
	
	public void setRoomStopJudger(RoomStopJudger roomStopJudger) {
		this.roomStopJudger = roomStopJudger;
	}

	@Override
	public void next(Game game, Action action) {
		// TODO Auto-generated method stub
		if (action == null) {
			logger.error("### action is null,illegal!");
			return;
		}
		Action translateAction;
		Actions nextActions;

		translateAction = this.judgeLegalAction((PokerGame)game, (PokerAction)action);
		if (null == translateAction) {
			logger.error("### judgeLegalAction,action is illegal!");
			return;
		}
		logger.info("### enter into the next action, action " + translateAction);

		ActionExecutor actionExecutor = actionExecutorMapping
				.getActionExecutor(translateAction);
		actionExecutor.exec(game, translateAction);
		
		Object oRet = translateAction.getAttribute("legal");
		if(oRet != null ){
			Boolean bRet = (Boolean)oRet;
			if(!bRet){
				actionNotify.notify(game, translateAction);
				return;
			}
		}
		
		game.addAction(translateAction);
		if(translateAction.isUserAction()){
			game.getLegalActions().removeActionByTypeAndPlayer(translateAction.getPlayer().getAccount(),translateAction.getType());
		}
	
		
		
		if (game.getLegalActions() != null) {
			Action[] userActions = game.getLegalActions()
					.getActionsByUserAction(true);
			if (userActions != null && userActions.length != 0) {
				logger.info("### must wait the user to action again");
				actionNotify.notify(game, translateAction);
				return;
			}
		}
		
		
		nextActions = calNext(game, translateAction);
		game.setLegalActions(nextActions);
		logger.info("### nextActions " + nextActions);
		
		
		if(nextActions != null){
			actionNotify.notify(game, action);
		}else {
			game.stop();
			actionNotify.notify(game, action);
			if (roomStopJudger.mayStop(game.getRoom())) {
				game.getRoom().getChannel().getServer()
						.destroyRoom(game.getRoom().getId(), "游戏结束");
			}
			return;
		}

		if (nextActions.size() == 1) {
			Action nextAction = nextActions.getHeadAction();
			if (!nextAction.isUserAction()) {
				this.next(game, nextAction);
			}
			return;
		}
		logger.info("### poker Rule to the end");

	}
	
	abstract  protected PokerAction judgeLegalAction(PokerGame pokerGame,PokerAction pokerAction);



}
