package com.nbcb.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.room.Room;
import com.nbcb.core.server.Server;
import com.nbcb.web.dao.GameDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.GameDaoEntity;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.CheckRoomGameService;

public class DefaultCheckRoomGameService implements CheckRoomGameService {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultCheckRoomGameService.class);

	private RoomDao roomDao;

	private GameDao gameDao;

	private Server server;

	private int roomTimeOut; // 秒

	private int gameTimeOut;

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	public void setRoomTimeOut(int roomTimeOut) {
		this.roomTimeOut = roomTimeOut;
	}

	public void setGameTimeOut(int gameTimeOut) {
		this.gameTimeOut = gameTimeOut;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	public int getRoomTimeOut() {
		return roomTimeOut;
	}

	public int getGameTimeOut() {
		return gameTimeOut;
	}

	@Override
	public void service() {
		// TODO Auto-generated method stub
		this.serviceRoom();
		this.serviceGame();
	}

	private void serviceRoom() {
//		logger.info("### start to scan timeout room");

		List<RoomDaoEntity> listRoom = this.findUnstartRoom();
		if (listRoom == null || listRoom.size() == 0) {
//			logger.info("### 没有未开始的房间!");
			return;
		}
		for (RoomDaoEntity rde : listRoom) {
			long startTime = rde.getCreate_time().getTime();
			if (startTime + this.roomTimeOut * 1000 < System
					.currentTimeMillis()) {
				logger.info("### room[" + rde + "]超时了!");
				Room room = server.getRoom(rde.getRoomid());
				if (room == null) {
					logger.info("###  内存中不存在房间信息!");
					rde.setUsers("");
					rde.setEnd_time(new Date());
					rde.setEnd_reason("房间超时了");
					this.roomDao.updateRoomUsers(rde);
				} else {
					server.destroyRoom(rde.getRoomid(),"房间超时了");
				}
			}
		}
	}

	private void serviceGame() {
//		logger.info("### start to scan timeout game");
		List<RoomDaoEntity> listRoom = this.findUnfinishRoom();
		if (listRoom == null || listRoom.size() == 0) {
//			logger.info("### 没有未结束的房间");
			return;
		}
		for (RoomDaoEntity rde : listRoom) {
			long startTime = rde.getCreate_time().getTime();
			if (startTime + this.gameTimeOut * 1000 < System
					.currentTimeMillis()) {
				logger.info("### room[" + rde + "]超时了!!");
				Room room = server.getRoom(rde.getRoomid());
				if (room == null) {
					logger.info("###  内存中不存在房间信息!");
					rde.setUsers("");
					rde.setEnd_time(new Date());
					rde.setEnd_reason("游戏超时了");
					this.roomDao.updateRoomUsers(rde);
				} else {
					server.destroyRoom(rde.getRoomid(),"游戏超时了");
				}
				List<GameDaoEntity> listGame = this.gameDao
						.findUnfinishGameByRoomId(rde.getId());
				if (listGame == null) {
					return;
				}
				for (GameDaoEntity gde : listGame) {
					gde.setEnd_time(new Date());
					gde.setUsers("");
					gameDao.updateGameUsers(gde);
				}
			}
		}
	}

	@Override
	public List<RoomDaoEntity> findUnstartRoomByCreateAccount(String account) {
		// TODO Auto-generated method stub
		List<RoomDaoEntity> listUnfinish = roomDao
				.selectUnfinishRoomByCreateAccount(account);
		if (listUnfinish == null || listUnfinish.size() == 0) {
			return null;
		}
		List<RoomDaoEntity> listRet = new ArrayList<RoomDaoEntity>();
		for (RoomDaoEntity rde : listUnfinish) {
			int size = gameDao.findTotalGameByRoomId(rde.getId());
			if (size == 0) {
				listRet.add(rde);
			}
		}
		return listRet;
	}

	@Override
	public List<RoomDaoEntity> findUnstartRoom() {
		// TODO Auto-generated method stub
		List<RoomDaoEntity> listUnfinish = roomDao.selectUnfinishRoom();
		if (listUnfinish == null || listUnfinish.size() == 0) {
			return null;
		}
		List<RoomDaoEntity> listRet = new ArrayList<RoomDaoEntity>();
		for (RoomDaoEntity rde : listUnfinish) {
			int size = gameDao.findTotalGameByRoomId(rde.getId());
			if (size == 0) {
				listRet.add(rde);
			}
		}
		return listRet;
	}

	@Override
	public List<RoomDaoEntity> findUnfinishRoom() {
		// TODO Auto-generated method stub
		return roomDao.selectUnfinishRoom();
	}

}
