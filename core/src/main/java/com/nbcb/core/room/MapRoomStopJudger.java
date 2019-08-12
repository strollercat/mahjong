package com.nbcb.core.room;

import java.util.Map;

public class MapRoomStopJudger implements RoomStopJudger {

	private RoomStopJudger juShuRoomStopJudger;

	private Map<String, RoomStopJudger> mapRoomStopjudger;

	public void setMapRoomStopjudger(
			Map<String, RoomStopJudger> mapRoomStopjudger) {
		this.mapRoomStopjudger = mapRoomStopjudger;
	}

	public void setJuShuRoomStopJudger(RoomStopJudger juShuRoomStopJudger) {
		this.juShuRoomStopJudger = juShuRoomStopJudger;
	}

	@Override
	public boolean mayStop(Room room) {
		// TODO Auto-generated method stub
		RoomInfo roomInfo = room.getRoomInfo();
		String name = roomInfo.getName();
		RoomStopJudger rsj = mapRoomStopjudger.get(name);
		if (rsj == null) {
			rsj = juShuRoomStopJudger;
		}
		return rsj.mayStop(room);

	}

}
