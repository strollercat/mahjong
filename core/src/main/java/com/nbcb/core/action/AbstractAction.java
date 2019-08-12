package com.nbcb.core.action;

import com.nbcb.common.helper.AbstractAttributeAccessor;
import com.nbcb.core.card.Cards;
import com.nbcb.core.user.Player;

public abstract class AbstractAction extends AbstractAttributeAccessor
		implements Action {

	private Player player;
	protected int type;
	private Cards cards;
	private boolean userAction;

	public AbstractAction(Player player, int type, Cards cards,
			boolean userAction) {
		this.player = player;
		this.type = type;
		this.cards = cards;
		this.userAction = userAction;
	}

	@Override
	public boolean isUserAction() {
		return this.userAction;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public Cards getCards() {
		return cards;
	}

	@Override
	public String toString() {
		String str = "";
		if (cards != null && cards.size() != 0) {
			for (int i = 0; i < cards.size(); i++) {
				str += cards.getCard(i);
			}
		}
		return "player[" + player.getPlayerOrder() + "] type[" + type
				+ "] cards[" + str + "] isUserAction[" + userAction + "] ";
	}

	@Override
	public void changeType(int type) {
		this.type = type;
	}

	@Override
	public void changeCards(Cards cards) {
		this.cards = cards;
	}

}
