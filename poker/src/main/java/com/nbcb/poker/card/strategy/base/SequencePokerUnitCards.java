package com.nbcb.poker.card.strategy.base;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class SequencePokerUnitCards extends PokerUnitCards {

	private int sequenceMin;

	private int sequenceMax;

	public int getSequenceMin() {
		return sequenceMin;
	}

	public int getSequenceMax() {
		return sequenceMax;
	}

	public SequencePokerUnitCards(PokerCards pokerCards, int sequenceMin,
			int sequenceMax) {
		super(pokerCards);
		this.sequenceMin = sequenceMin;
		this.sequenceMax = sequenceMax;

	}

	@Override
	public String toString() {
		return "min[" + this.sequenceMin + "]max[" + this.sequenceMax + "]"
				+ super.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "sequencePucs";
	}

}
