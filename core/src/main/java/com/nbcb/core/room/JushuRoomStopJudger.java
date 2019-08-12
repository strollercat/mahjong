package com.nbcb.core.room;

public class JushuRoomStopJudger implements RoomStopJudger {

	@Override
	public boolean mayStop(Room room) {
		// TODO Auto-generated method stub
		return room.getOrder() == room.getRoomInfo().getTotalJu();
	}

}
