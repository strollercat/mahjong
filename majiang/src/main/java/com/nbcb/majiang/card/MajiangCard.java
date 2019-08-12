package com.nbcb.majiang.card;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.DefaultCard;

public class MajiangCard extends DefaultCard {

	private boolean baida;

	public MajiangCard(int number, String type) {
		super(number, type);
		this.baida = false;
	}

	public int getFirstNumber() {
		if (this.type.length() != 2) {
			return -199;
		}
		return Integer.parseInt(this.type.substring(0, 1));
	}

	public String getSecondType() {
		if (this.type.length() != 2) {
			return "";
		}
		return this.type.substring(1, 2);
	}

	public boolean isBaida() {
		return baida;
	}

	public void setBaida(boolean baida) {
		this.baida = baida;
	}

	public static final int TIAO = 0;
	public static final int DONG = 1;
	public static final int WAN = 2;
	public static final int DNXB = 3;
	public static final int ZFB = 4;
	public static final int HUA = 5;

	public static final String[] legalTypes = new String[] { "1万", "2万", "3万",
			"4万", "5万", "6万", "7万", "8万", "9万", "1洞", "2洞", "3洞", "4洞", "5洞",
			"6洞", "7洞", "8洞", "9洞", "1条", "2条", "3条", "4条", "5条", "6条", "7条",
			"8条", "9条", "东", "南", "西", "北", "中", "发", "白", "春", "夏", "秋", "冬",
			"梅", "兰", "菊", "竹" };

	public String nextType() {

		if (type.equals("春")) {
			return "夏";
		}
		if (type.equals("夏")) {
			return "秋";
		}
		if (type.equals("秋")) {
			return "冬";
		}
		if (type.equals("冬")) {
			return "春";
		}

		if (type.equals("梅")) {
			return "兰";
		}
		if (type.equals("兰")) {
			return "菊";
		}
		if (type.equals("菊")) {
			return "竹";
		}
		if (type.equals("竹")) {
			return "梅";
		}

		if (type.equals("东")) {
			return "南";
		}
		if (type.equals("南")) {
			return "西";
		}
		if (type.equals("西")) {
			return "北";
		}
		if (type.equals("北")) {
			return "东";
		}
		if (type.equals("中")) {
			return "发";
		}
		if (type.equals("发")) {
			return "白";
		}
		if (type.equals("白")) {
			return "中";
		}

		String number = type.substring(0, 1);
		String unit = type.substring(1, 2);
		if (number.equals("9")) {
			number = "1";
		} else {
			number = "" + (Integer.parseInt(number) + 1);
		}
		return number + unit;
	}

	protected void checkType(String type) {
		for (String str : legalTypes) {
			if (type.equals(str)) {
				return;
			}
		}
		throw new IllegalArgumentException("[" + type + "] is illegal");
	}

	public boolean isUnit(int unit) {
		// TODO Auto-generated method stub
		if (unit == MajiangCard.DNXB) {
			if (this.type.equals("东")) {
				return true;
			}
			if (this.type.equals("南")) {
				return true;
			}
			if (this.type.equals("西")) {
				return true;
			}
			if (this.type.equals("北")) {
				return true;
			}
			return false;
		}
		if (unit == MajiangCard.ZFB) {
			if (this.type.equals("中")) {
				return true;
			}
			if (this.type.equals("发")) {
				return true;
			}
			if (this.type.equals("白")) {
				return true;
			}
			return false;
		}

		if (unit == MajiangCard.HUA) {
			if (this.type.equals("春")) {
				return true;
			}
			if (this.type.equals("夏")) {
				return true;
			}
			if (this.type.equals("秋")) {
				return true;
			}
			if (this.type.equals("冬")) {
				return true;
			}
			if (this.type.equals("梅")) {
				return true;
			}
			if (this.type.equals("兰")) {
				return true;
			}
			if (this.type.equals("菊")) {
				return true;
			}
			if (this.type.equals("竹")) {
				return true;
			}
			return false;
		}
		if (this.type.length() != 2) {
			return false;
		}
		if (unit == MajiangCard.DONG) {
			String tmpUnit = this.type.substring(1, 2);
			if (tmpUnit.equals("洞")) {
				return true;
			}
			return false;
		}
		if (unit == MajiangCard.TIAO) {
			String tmpUnit = this.type.substring(1, 2);
			if (tmpUnit.equals("条")) {
				return true;
			}
			return false;
		}
		if (unit == MajiangCard.WAN) {
			String tmpUnit = this.type.substring(1, 2);
			if (tmpUnit.equals("万")) {
				return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[" + number + "." + type + "." + baida + "]";
	}

	@Override
	public int compareTo(Card card) {

		if (card instanceof MajiangCard) {
			MajiangCard mcOther = (MajiangCard) card;
			if (this.baida && !mcOther.baida) {
				return -1;
			}
			if (!this.baida && mcOther.baida) {
				return 1;
			}
			return super.compareTo(card);
		}
		return 1;
	}

}
