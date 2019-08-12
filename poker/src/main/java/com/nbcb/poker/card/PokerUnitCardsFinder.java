package com.nbcb.poker.card;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public interface PokerUnitCardsFinder {
	public List<PokerUnitCards> find(PokerCards pokerCards,int size);
}
