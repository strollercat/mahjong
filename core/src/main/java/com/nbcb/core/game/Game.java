package com.nbcb.core.game;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.core.lifecycle.LifeCycle;
import com.nbcb.core.room.Room;
import com.nbcb.core.rule.Rule;
import com.nbcb.core.server.Formatter;
import com.nbcb.core.user.Players;

public interface Game extends LifeCycle, Players, Formatter {

	public int getUniqueId();

	public void setUniqueId(int id);

	public GameInfo getGameInfo();

	public Action getLastAction();

	public Actions getHistoryActions();

	public Actions getLegalActions();

	public void addAction(Action action);

	public void nextAction(Action action);

	public void setPlayerScores(PlayerScores playerScores);

	public PlayerScores getPlayerScores();

	public void setPlayers(Players players);

	public void setActions(Actions actions);

	public void setLegalActions(Actions actions);

	public void setRule(Rule rule);

	public void setAllCards(Cards allCards);

	public void setRoom(Room room);

	public Room getRoom();

	public Cards getAllCards();

}
