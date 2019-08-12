package com.nbcb.majiang.action;

import com.nbcb.core.action.DefaultAction;
import com.nbcb.core.card.Cards;
import com.nbcb.core.user.Player;

public class MajiangAction extends DefaultAction {

	public MajiangAction(Player player, int type, Cards cards,
			boolean userAction) {
		super(player, type, cards, userAction);
		// TODO Auto-generated constructor stub
	}

	public static final String MJHURESULT = "MJHURESULT";
	public static final String CANNOTDA = "CANNOTDA";
	public static final String MAIMACARDS = "MAIMACARDS";

	public static String[] majiangActions = new String[] { "", "ALLOCATE",
			"MOFRONT", "MOBACK", "MOFRONTHU", "MOBACKHU", "FANGQIANGHU",
			"QIANGGANGHU", "DAHUA", "DANONHUA", "PENG", "CHI", "XIANGANG",
			"MINGGANG", "ANGANG", "HUANGPAI", "COMPLETE", "GEN" ,"MAIMA"};

	public static String[] majiangUserActions = new String[] { "NO", "USERDA",
			"USERPENG", "USERCHI", "USERGANG", "USERHU", "USERGEN" };

	public static final int ALLOCATE = 1;
	public static final int MOFRONT = 2; // card1 false
	public static final int MOBACK = 3; // card1 false
	public static final int MOFRONTHU = 4; // card1 true
	public static final int MOBACKHU = 5; // card1 true
	public static final int FANGQIANGHU = 6; // card1 true
	public static final int QIANGGANGHU = 7; // card1 true
	public static final int DAHUA = 8; // card1 false
	public static final int DANONHUA = 9; // null true
	public static final int PENG = 10; // card2 true
	public static final int CHI = 11; // card2 true
	public static final int XIANGANG = 12; // card1 true
	public static final int MINGGANG = 13; // card3 true
	public static final int ANGANG = 14; // card4 true
	public static final int HUANGPAI = 15;
	public static final int COMPLETE = 16;
	public static final int GEN = 17;

	public static final int MAIMA = 18;// 红中麻将特有

	public static final int NO = 100;
	public static final int USERDA = 101; // 送一张牌
	public static final int USERPENG = 102;
	public static final int USERCHI = 103; // 送两张牌
	public static final int USERGANG = 104; // 送一张牌
	public static final int USERHU = 105;
	public static final int USERGEN = 106;

	public int translateType() {
		if (this.type == DANONHUA) {
			return USERDA;
		} else if (this.type == PENG) {
			return USERPENG;
		} else if (this.type == CHI) {
			return USERCHI;
		} else if (this.type == XIANGANG || this.type == MINGGANG
				|| this.type == ANGANG) {
			return USERGANG;
		} else if (this.type == MOFRONTHU || this.type == MOBACKHU
				|| this.type == FANGQIANGHU || this.type == QIANGGANGHU) {
			return USERHU;
		} else if (this.type == GEN) {
			return USERGEN;
		}
		return this.type;
	}

	@Override
	public String toString() {
		String str = "";
		Cards cards = this.getCards();
		if (cards != null && cards.size() != 0) {
			for (int i = 0; i < cards.size(); i++) {
				str += cards.getCard(i);
			}
		} else {
			str = null;
		}
		String strPlayer = (this.getPlayer() == null ? null : this.getPlayer()
				.getPlayerOrder() + "");
		String strAttribute = "";
		for (String key : this.keySet()) {
			strAttribute += (key + "[" + this.getAttribute(key) + "] ");
		}
		if (this.getType() >= 100) {
			return "player[" + strPlayer + "] type["
					+ MajiangAction.majiangUserActions[this.getType() - 100]
					+ " " + this.getType() + " ] cards[" + str
					+ "] isUserAction[" + "] " + this.isUserAction()
					+ " Attribute[" + strAttribute + "]";
		}

		return "player[" + strPlayer + "] type["
				+ MajiangAction.majiangActions[this.getType()] + " "
				+ this.getType() + " ] cards[" + str + "] isUserAction["
				+ this.isUserAction() + "] " + " Attribute[" + strAttribute
				+ "] ";
	}

}
