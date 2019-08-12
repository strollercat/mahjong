package com.nbcb.core.room;

import com.nbcb.core.user.Player;

public interface RoomListener {
	public void listen(Player player,Room room,RoomAction roomAction);
}
