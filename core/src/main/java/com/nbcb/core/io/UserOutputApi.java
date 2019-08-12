package com.nbcb.core.io;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomAction;
import com.nbcb.core.user.Player;

public interface UserOutputApi {

	
	/**
	 * 用户进入房间后的一些通知动作
	 * @param player
	 * @param room
	 * @param roomAction
	 */
	public void listen(Player player, Room room, RoomAction roomAction);

	/**
	 * 
	 * @param game
	 * @param action
	 */
	public void listen(Game game, Action action);

	public void connect(String account);

	public void disconnect(String account);
}
