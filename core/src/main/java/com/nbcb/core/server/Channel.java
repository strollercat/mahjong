package com.nbcb.core.server;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.rule.Rule;
import com.nbcb.core.user.Player;
import com.nbcb.core.user.Players;

public interface Channel {

	// create room
	public Room createRoom(String account, RoomInfo roomInfo);

	public Player createPlayer(String account);

	public Actions createActions();

	public Game createGame(RoomInfo roomInfo,Game lastGame,Room room);

	public Players createPlayers(int size);

	public Rule getRule(RoomInfo roomInfo);

	public Cards createAllCards(RoomInfo roomInfo);

	public Server getServer();


}
