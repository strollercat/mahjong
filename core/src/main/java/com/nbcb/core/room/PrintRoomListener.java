package com.nbcb.core.room;

import com.nbcb.core.user.Player;

public class PrintRoomListener implements RoomListener {

	@Override
	public void listen(Player player, Room room, RoomAction roomAction) {
		// TODO Auto-generated method stub
		System.out.println(room);
	}

	

}
