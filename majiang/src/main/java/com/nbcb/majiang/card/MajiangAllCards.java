package com.nbcb.majiang.card;

import com.nbcb.core.card.Card;

public class MajiangAllCards extends MajiangCards {

	protected void initCards() {
		int allCardsNumber = 144;
		Card card[] = new Card[allCardsNumber];
		for (int i = 0; i < allCardsNumber - 8; i++) {
			Card c = new MajiangCard(i, MajiangCard.legalTypes[(i) / 4]);
			this.addCard(i, c);
		}
		int legalKindsLen = MajiangCard.legalTypes.length;
		int j = legalKindsLen - 8;
		for (int i = allCardsNumber - 8; i < allCardsNumber; i++) {
			Card c = new MajiangCard(i, MajiangCard.legalTypes[j++]);
			this.addCard(i, c);
		}
		return;
	}

	
	public void start() {
		this.initCards();
	}

	public String toString() {
		return "allCards: " + super.toString();
	}
}
