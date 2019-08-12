package com.nbcb.poker.threewater;

import com.nbcb.core.card.Cards;
import com.nbcb.core.user.Player;
import com.nbcb.poker.action.PokerAction;

public class ThreeWaterPokerAction extends PokerAction {

	public ThreeWaterPokerAction(Player player, int type, Cards cards,
			boolean userAction) {
		super(player, type, cards, userAction);
		// TODO Auto-generated constructor stub
	}
	
	public static final  int ALLOCATE = 1;
	public static final  int SHOOT = 2;
	public static final  int COMPARE = 3;
	public static final  int COMPLETE = 4;
	
	public static String[] threeWaterPokerAction = new String[] { "", "ALLOCATE",
		"SHOOT", "COMPARE", "COMPLETE"};
	
	
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
		return "player[" + strPlayer + "] type["
				+ ThreeWaterPokerAction.threeWaterPokerAction[this.getType()] + " "
				+ this.getType() + " ] cards[" + str + "] isUserAction["
				+ this.isUserAction() + "] " + " Attribute[" + strAttribute
				+ "] ";
	}

}
