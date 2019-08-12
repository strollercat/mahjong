package com.nbcb.majiang.rule.judger;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuStrategy;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangMoHuActionJudger extends MajiangActionJudger {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangMoHuActionJudger.class);

	private boolean moFront;

	public void setMoFront(boolean moFront) {
		this.moFront = moFront;
	}

	private MajiangHuStrategy huStrategy;

	public void setHuStrategy(MajiangHuStrategy huStrategy) {
		this.huStrategy = huStrategy;
	}

	@Override
	protected MajiangActions judgeInner(MajiangGame mjGame,
			MajiangAction mjAction) {

		MajiangPlayer mjPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangCard mjCard = (MajiangCard) mjAction.getCards().getTailCard();
		MajiangActions mas = new MajiangActions();

		logger.debug("### mjPlayer " + mjPlayer.getAccount());
		MajiangCards mjCards = new MajiangCards();
		mjCards.addTailCards(mjPlayer.getMajiangInnerCards().toArray());
		List<MajiangHuCards> listMjHuCards = huStrategy.findLegalHuCards(mjGame,
				mjPlayer.getMajiangMiddleCards().getListUnitCards(), mjCards,
				mjCard);
		if (listMjHuCards == null || listMjHuCards.size() == 0) {
			return null;
		}

		int huType = (this.moFront ? MajiangAction.MOFRONTHU
				: MajiangAction.MOBACKHU);
		List<MajiangHuResult> listHuResult = huStrategy.calHuResultFan(mjGame,
				mjPlayer, huType, listMjHuCards);

		MajiangHuResult mjHuResult = huStrategy.findLegalHuResult(mjGame,
				listHuResult);
		if (mjHuResult == null) {
			return null;
		}

		// logger.debug("### find the mjHuResult " + mjHuResult);
		mjHuResult.setMjLosePlayer(null);
		logger.debug("###  find mjHuResult " + mjHuResult);
		MajiangCards mjResultCards = new MajiangCards();
		mjResultCards.addTailCard(mjCard);
		MajiangAction mjRetAction = new MajiangAction(mjPlayer,
				huType, mjResultCards, true);
		mjRetAction.setAttribute(MajiangAction.MJHURESULT, mjHuResult);
		mas.addAction(mjRetAction);

		return mas.size() == 0 ? null : mas;
	}
}
