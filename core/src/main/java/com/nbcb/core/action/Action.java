package com.nbcb.core.action;

import com.nbcb.common.helper.AttributeAccessor;
import com.nbcb.core.card.Cards;
import com.nbcb.core.user.Player;

public interface Action extends AttributeAccessor{

	public Player getPlayer();

	public int getType();

	public Cards getCards();

	public boolean isUserAction();
	
	public void changeType(int type);
	
	public void changeCards(Cards cards);

}
