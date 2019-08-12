package com.nbcb.majiang.rule.judger;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangChiActionJudger extends MajiangActionJudger {

	private boolean canBaida = false;
	

	public void setCanBaida(boolean canBaida) {
		this.canBaida = canBaida;
	}

	private MajiangAction findChiMajiangAction(MajiangPlayer mp,
			MajiangInnerCards mics, MajiangCard mjCard, String str1, String str2) {
		Card card1 = mics.findCardByType(str1);
		Card card2 = mics.findCardByType(str2);

		if (card1 != null && card2 != null) {
			if (((MajiangCard) card1).isBaida()
					|| ((MajiangCard) card2).isBaida()) {
				return null;
			}
			Cards mjCards = new MajiangCards();
			// mjCards.addTailCard(mjCard);
			mjCards.addTailCard(card1);
			mjCards.addTailCard(card2);
			return new MajiangAction(mp, MajiangAction.CHI, mjCards, true);
		}
		return null;
	}

	@Override
	protected MajiangActions judgeInner(MajiangGame mjGame,
			MajiangAction mjAction) {

		MajiangCard mjCard = (MajiangCard) mjAction.getCards().getTailCard();
		
		if(!this.canBaida){
			if (mjCard.isBaida()) {
				return null;
			}
		}
		
		
		MajiangPlayer mp = (MajiangPlayer) mjGame.nextPlayer(mjAction
				.getPlayer());
		MajiangInnerCards mics = mp.getMajiangInnerCards();
		if (mjCard.isUnit(MajiangCard.DNXB)) {
			return null;
		}
		if (mjCard.isUnit(MajiangCard.ZFB)) {
			return null;
		}

		MajiangActions mas = new MajiangActions();

		int number = Integer.parseInt(mjCard.getType().substring(0, 1));

		String unit = mjCard.getType().substring(1, 2);
		// System.out.println("number unit " + number + " " + unit);
		int numMin2 = number - 2;
		int numMin1 = number - 1;
		int numAdd1 = number + 1;
		int numAdd2 = number + 2;
		String strMin2 = numMin2 + unit;
		String strMin1 = numMin1 + unit;
		String strAdd2 = numAdd2 + unit;
		String strAdd1 = numAdd1 + unit;

		MajiangAction ma = this.findChiMajiangAction(mp, mics, mjCard, strMin2,
				strMin1);
		if (ma != null) {
			mas.addAction(ma);
		}

		ma = this.findChiMajiangAction(mp, mics, mjCard, strAdd1, strAdd2);
		if (ma != null) {
			mas.addAction(ma);
		}
		ma = this.findChiMajiangAction(mp, mics, mjCard, strMin1, strAdd1);
		if (ma != null) {
			mas.addAction(ma);
		}
		return mas.size() == 0 ? null : mas;
	}

}
