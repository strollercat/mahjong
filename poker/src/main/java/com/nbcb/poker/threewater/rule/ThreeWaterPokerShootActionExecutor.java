package com.nbcb.poker.threewater.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.rule.ActionExecutor;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsCompare;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.threewater.ThreeWaterPokerPlayer;

public class ThreeWaterPokerShootActionExecutor implements ActionExecutor {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ThreeWaterPokerShootActionExecutor.class);

	private PokerUnitCardsJudger normalPokerUnitCardsJudger;

	private PokerUnitCardsCompare pokerUnitCardsCompare;

	public void setPokerUnitCardsCompare(
			PokerUnitCardsCompare pokerUnitCardsCompare) {
		this.pokerUnitCardsCompare = pokerUnitCardsCompare;
	}

	public void setNormalPokerUnitCardsJudger(
			PokerUnitCardsJudger normalPokerUnitCardsJudger) {
		this.normalPokerUnitCardsJudger = normalPokerUnitCardsJudger;
	}

	@Override
	public void exec(Game game, Action action) {

		ThreeWaterPokerPlayer twpp = (ThreeWaterPokerPlayer) action.getPlayer();
		

		if (null != twpp.getThreeWaterPokerCards().getSpecialCards()) {
			twpp.getThreeWaterPokerCards().setShooted(true);
			action.setAttribute("legal", true);
			return;
		}
		PokerCards pcs = (PokerCards) action.getCards();
		if(!pcs.sameCardsByPokerNumberAndPokerColor(twpp.getThreeWaterPokerCards())){
			logger.error("### 非法请求！！！！！！！！！！！！！！account["+twpp.getAccount()+"]");
			return ;
		}
		
		logger.info("pcs["+pcs+"]");

		PokerCards firstPcs = new PokerCards();
		PokerCards secondPcs = new PokerCards();
		PokerCards thirdPcs = new PokerCards();

		firstPcs.addTailCards(pcs.removeHeadCards(3));
		secondPcs.addTailCards(pcs.removeHeadCards(5));
		thirdPcs.addTailCards(pcs.removeTailCards(5));
//		logger.info("first["+firstPcs+"]second["+secondPcs+"]third["+thirdPcs+"]");

		PokerUnitCards firstPucs = normalPokerUnitCardsJudger.judge(firstPcs);
		PokerUnitCards secondPucs = normalPokerUnitCardsJudger.judge(secondPcs);
		PokerUnitCards thirdPucs = normalPokerUnitCardsJudger.judge(thirdPcs);
		logger.info("first["+firstPcs+"]second["+secondPcs+"]third["+thirdPcs+"]");
		
		firstPucs.sort();
		secondPucs.sort();
		thirdPucs.sort();

		if (this.pokerUnitCardsCompare.compare(firstPucs, secondPucs) >= 0) {
			action.setAttribute("legal", false);
			return;
		}
		if (this.pokerUnitCardsCompare.compare(secondPucs, thirdPucs) >= 0) {
			action.setAttribute("legal", false);
			return;
		}
		

		twpp.getThreeWaterPokerCards().setFirstPokerCards(firstPucs);
		twpp.getThreeWaterPokerCards().setSecondPokerCards(secondPucs);
		twpp.getThreeWaterPokerCards().setThirdPokerCards(thirdPucs);
		twpp.getThreeWaterPokerCards().setShooted(true);
		action.setAttribute("legal", true);
	}

}
