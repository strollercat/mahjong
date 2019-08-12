package com.nbcb.web.db;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomAction;
import com.nbcb.core.room.RoomListener;
import com.nbcb.core.room.RoomStopJudger;
import com.nbcb.core.user.Player;
import com.nbcb.web.dao.GameUserRoomDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.GameUserRoom;
import com.nbcb.web.dao.entity.RoomDaoEntity;

public class DatabaseRoomListener implements RoomListener {

	private static final Logger logger = LoggerFactory
			.getLogger(DatabaseRoomListener.class);

	private RoomDao roomDao;

	private GameUserRoomDao gameUserRoomDao;

	private ThreadPoolTaskExecutor taskExecutor;

	private RoomStopJudger roomStopJudger;

	public void setRoomStopJudger(RoomStopJudger roomStopJudger) {
		this.roomStopJudger = roomStopJudger;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setGameUserRoomDao(GameUserRoomDao gameUserRoomDao) {
		this.gameUserRoomDao = gameUserRoomDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	private void roomDestroy(final Player player, final Room room,
			final RoomAction roomAction) {
		taskExecutor.submit(new Runnable() {
			public void run() {
				try {
					logger.info("###  房间结束roomId[" + room.getId() + "]");
					logger.info("### 开始更新房间的用户信息");
					RoomDaoEntity roomDaoEntity = new RoomDaoEntity();
					roomDaoEntity.setId(room.getUniqueId());
					roomDaoEntity.setUsers(JsonUtil.encode(room.format().get(
							"users")));
					roomDaoEntity.setEnd_time(new Date(System
							.currentTimeMillis()));
					roomDaoEntity.setEnd_reason(room.getEndReason());
					roomDaoEntity.setEnd_ju(room.getOrder());
					roomDao.updateRoomUsers(roomDaoEntity);

					/**
					 * 说明房间游戏已经开始，打过了几局!
					 */
					if (room.getOrder() > 0) {
						for (int i = 0; i < room.getActivePlayers(); i++) {
							GameUserRoom gameUserRoom = new GameUserRoom();
							gameUserRoom.setAccount(room.getPlayerByIndex(i)
									.getAccount());
							gameUserRoom.setOvertime(new Date());
							gameUserRoom.setRoomid(room.getUniqueId());
							gameUserRoomDao.insertGameUserRoom(gameUserRoom);
						}
					}
				} catch (Exception e) {
					logger.error("### error,", e);
				}
			}
		});
	}

	@Override
	public void listen(Player player, Room room, RoomAction roomAction) {
		// TODO Auto-generated method stub
		if (roomAction == RoomAction.DESTROY) {
			this.roomDestroy(player, room, roomAction);
		}
	}
}
