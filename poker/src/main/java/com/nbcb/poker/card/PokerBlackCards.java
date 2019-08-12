package com.nbcb.poker.card;

import com.nbcb.common.util.RandomUtil;

public class PokerBlackCards extends PokerCards {

	private PokerAllCards pokerAllCards;

	public PokerBlackCards(PokerAllCards pokerAllCards) {
		this.pokerAllCards = pokerAllCards;
		this.wash();
	}

	public void wash() {
		int start = 0;
		int end = pokerAllCards.size();
		int[] randoms = RandomUtil.getRandom(start, end - 1);
		for (int i = 0; i < randoms.length; i++) {
			this.addTailCard(pokerAllCards.getCard(randoms[i]));
		}
		
//		this.addTailCards(pokerAllCards.toArray());
	}
}
