package com.nbcb.poker.card;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.DefaultCard;

public class PokerCard extends DefaultCard {

	public PokerCard(int number, String type) {
		super(number, type);
	}

	public static enum Color {
		HEITAO, HONGTAO, CAOHUA, FANGKUAI, SMALLKING, BIGKING
	}

	@Override
	public int compareTo(Card card) {
		PokerCard otherPc = (PokerCard) card;
		int pokerNumber1 = this.getPokerNumber();
		int pokerNumber2 = otherPc.getPokerNumber();
		if (pokerNumber1 == pokerNumber2) {
			return this.getPokerColor().compareTo(otherPc.getPokerColor());
		} else if (pokerNumber1 < pokerNumber2) {
			return -1;
		} else {
			return 1;
		}
	}

	public Color getPokerColor() {
		String types[] = this.type.split("_");
		if (Integer.parseInt(types[1]) >= 18 || Integer.parseInt(types[1]) <= 0) {
			throw new RuntimeException("poker card type[" + type + "] illegal");
		}
		if (types[1].equals("16")) {
			return Color.SMALLKING;
		} else if (types[1].equals("17")) {
			return Color.BIGKING;
		}
		if (types[0].equals("0")) {
			return Color.HEITAO;
		} else if (types[0].equals("1")) {
			return Color.HONGTAO;
		} else if (types[0].equals("2")) {
			return Color.CAOHUA;
		} else if (types[0].equals("3")) {
			return Color.FANGKUAI;
		} else {
			throw new RuntimeException("poker card type[" + type + "] illegal");
		}

	}

	public int getPokerNumber() {
		String types[] = this.type.split("_");
		return Integer.parseInt(types[1]);
	}

	public boolean isPokerKing() {
		return this.isPokerBigKing() || this.isPokerSmallKing();
	}

	public boolean isPokerBigKing() {
		return this.getPokerNumber() == 17;
	}

	public boolean isPokerSmallKing() {
		return this.getPokerNumber() == 16;
	}

	public static int getSmallKingPokerNumber() {
		return 16;
	}

	public static int getBigKingPokerNumber() {
		return 17;
	}

	@Override
	public String toString() {
		return "["+this.number+" "+this.getPokerColor()+" "+this.getPokerNumber()+"]";
	}
}
