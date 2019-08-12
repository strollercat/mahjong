package com.nbcb.poker.card;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.core.card.Card;
import com.nbcb.core.server.Formatter;

public abstract class PokerUnitCards extends PokerCards implements Formatter {

	public PokerUnitCards(PokerCards pokerCards) {
		this.addTailCards(pokerCards.toArray());
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Card c : cards) {
			sb.append(c);
		}
		return "pucs[" + this.getName() + " " + sb.toString() + "]";
	}

	abstract public String getName();

	public Map format() {
		Map map = new HashMap();
		map.put("name", this.getName());
		map.put("cns", this.toNumberArray());
		return map;
	}

}
