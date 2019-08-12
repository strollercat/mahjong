package com.nbcb.majiang.rule.judger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangMingGangActionJudger extends MajiangActionJudger {

	private static final Logger logger = LoggerFactory.getLogger(MajiangMingGangActionJudger.class);
	
	private boolean canBaida = false;
	
	public void setCanBaida(boolean canBaida) {
		this.canBaida = canBaida;
	}
	
	private MajiangAction mingGangJuger(MajiangPlayer mp,
			MajiangInnerCards mics, MajiangCard mjCard) {
		Card[] arrCards = mics.findCardsByType(mjCard.getType());
		if (arrCards != null && arrCards.length == 3) {
			Cards cards = new MajiangCards();
			cards.addTailCards(arrCards);
			return new MajiangAction(mp, MajiangAction.MINGGANG, cards, true);
		}
		return null;
	}

	@Override
	protected MajiangActions judgeInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		
//		logger.debug("### enter into the MajiangMingGangActionJudger ");
		
		MajiangActions mas = new MajiangActions();
		MajiangCard mjCard = (MajiangCard) mjAction.getCards().getTailCard();
		
		if(!this.canBaida){
			if (mjCard.isBaida()) {
				return null;
			}
		}
		
		MajiangPlayer mp = (MajiangPlayer) mjAction.getPlayer();
		MajiangPlayer nextMp = (MajiangPlayer) mjGame.nextPlayer(mp);
		MajiangAction ma = null;
		do {
			ma = this.mingGangJuger(nextMp, nextMp.getMajiangInnerCards(),
					mjCard);
			if (ma != null) {
				mas.addAction(ma);
			}
			nextMp = (MajiangPlayer) mjGame.nextPlayer(nextMp);
		} while (nextMp != mp);
		return mas.size() == 0 ? null : mas;
	}

}
