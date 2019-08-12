package com.nbcb.majiang.card;

import java.util.Map;

import com.nbcb.core.card.Card;

public class MajiangAnPengUnitCards extends MajiangUnitCards {
	

	public MajiangAnPengUnitCards(Card card1, Card card2, Card card3) {
		this.addTailCard(card1);
		this.addTailCard(card2);
		this.addTailCard(card3);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "暗碰";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		return null;
	}
}
