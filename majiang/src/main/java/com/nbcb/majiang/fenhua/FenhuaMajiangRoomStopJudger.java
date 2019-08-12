package com.nbcb.majiang.fenhua;

import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomStopJudger;

public class FenhuaMajiangRoomStopJudger implements RoomStopJudger {

	@Override
	public boolean mayStop(Room room) {
		// TODO Auto-generated method stub
		FenhuaMajiangRoomInfo roomInfo = (FenhuaMajiangRoomInfo) room
				.getRoomInfo();
		if (roomInfo.isPinghu()) {
			return room.getOrder() == room.getRoomInfo().getTotalJu();
		} else {
			for (int i = 0; i < room.getActivePlayers(); i++) {
				int score = room.getPlayerScoreByAccount(room.getPlayerByIndex(
						i).getAccount());
				if (score <= 0) {
					return true;
				}
			}
			return false;
		}
	}

}
