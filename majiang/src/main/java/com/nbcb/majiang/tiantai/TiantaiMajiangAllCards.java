package com.nbcb.majiang.tiantai;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangCard;

public class TiantaiMajiangAllCards extends MajiangAllCards {

	public void start() {
		this.initCards();
		Card[] cards = this.getCardsByUnit(MajiangCard.TIAO);
		this.removeCards(cards);
	}
}
