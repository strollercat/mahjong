package com.nbcb.core.card;

public abstract class AbstractCard implements Card {

	protected int number;

	protected String type;

	public AbstractCard(int number, String type) {
		this.number = number;
		this.type = type;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public int compareTo(Card card) {
		if (this.number < card.getNumber()) {
			return -1;
		} else {
			return 1;
		}
	}

	@Override
	public String toString() {
		return "[" + number + "." + type + "]";
	}

}
