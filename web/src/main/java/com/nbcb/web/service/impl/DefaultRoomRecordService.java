package com.nbcb.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.web.dao.GameActionDao;
import com.nbcb.web.dao.GameDao;
import com.nbcb.web.dao.GameUserRoomDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.GameAction;
import com.nbcb.web.dao.entity.GameDaoEntity;
import com.nbcb.web.dao.entity.GameUserRoom;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.RoomRecordService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

public class DefaultRoomRecordService implements RoomRecordService {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultRoomRecordService.class);

	private GameUserRoomDao gameUserRoomDao;

	private RoomDao roomDao;

	private GameDao gameDao;

	private GameActionDao gameActionDao;

	private WeixinUserService weixinUserSerivce;

	public void setWeixinUserSerivce(WeixinUserService weixinUserSerivce) {
		this.weixinUserSerivce = weixinUserSerivce;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	public void setGameActionDao(GameActionDao gameActionDao) {
		this.gameActionDao = gameActionDao;
	}

	public void setGameUserRoomDao(GameUserRoomDao gameUserRoomDao) {
		this.gameUserRoomDao = gameUserRoomDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	protected Map codeRoomNotice(RoomDaoEntity roomDaoEntity, int order,
			String account) {
		try {
			Map mapRet = new HashMap();
			List listPlayers = new ArrayList();
			mapRet.put("order", order);
			List<Map> listUsers = (List) JsonUtil.decode(
					roomDaoEntity.getUsers(), ArrayList.class);
			for (int i = 0; i < listUsers.size(); i++) {
				Map mapUser = listUsers.get(i);
				String name = (String) mapUser.get("name");
				if (account.equals(name)) {
					int dir = ((Integer) mapUser.get("dir")).intValue();
					mapRet.put("author", dir);
					break;
				}
			}

			if (mapRet.get("author") == null) {
				mapRet.put("author", 0);
			}

			for (int i = 0; i < listUsers.size(); i++) {
				Map mapUser = listUsers.get(i);
				String name = (String) mapUser.get("name");
				WeixinReplyUser wru = weixinUserSerivce
						.getWeixinUserByOpenid(name);
				if (wru == null) {
					continue;
				}
				mapUser.put("url", wru.getHeadimgurl());
				mapUser.put("name", wru.getNickname());
			}
			mapRet.put("users", listUsers);
			mapRet.put("roomId", roomDaoEntity.getRoomid());
			mapRet.put("createPlayer", roomDaoEntity.getCreate_account());
			mapRet.put("createTime", roomDaoEntity.getCreate_time());
			mapRet.put("roomInfo", JsonUtil.decode(
					roomDaoEntity.getRoom_info(), HashMap.class));
			mapRet.put("code", "roomNotice");
			return mapRet;
		} catch (Exception e) {
			logger.error("### error", e);
			return null;
		}

	}

	protected Map formatAction(GameAction gameAction) {
		try {
			return (Map) JsonUtil.decode(gameAction.getAttribute(),
					HashMap.class);
		} catch (Exception e) {
			logger.error("### error", e);
			return null;
		}
	}

	@Override
	public List<List<Map>> queryRecordDataByRoomid(String account, int roomId) {
		// TODO Auto-generated method stub
		RoomDaoEntity roomDaoEntity = roomDao.selectRoomById(roomId);
		logger.info("### roomDaoEntity " + roomDaoEntity);
		if (roomDaoEntity == null) {
			return null;
		}
		if (roomDaoEntity.getId() == 0) {
			return null;
		}
		List<GameDaoEntity> listGame = gameDao.findGamesByRoomid(roomDaoEntity
				.getId());
		logger.info("### listGame " + listGame);
		if (listGame == null || listGame.size() == 0) {
			return null;
		}
		List<List<Map>> listRet = new ArrayList<List<Map>>();
		int order = 1;
		for (GameDaoEntity gameDaoEntity : listGame) {
			if (gameDaoEntity.getId() == 0) {
				continue;
			}
			List<Map> listGameMap = new ArrayList<Map>();
			listRet.add(listGameMap);
			listGameMap.add(this
					.codeRoomNotice(roomDaoEntity, order++, account));
			List<GameAction> listAction = gameActionDao
					.selectGameActionsByGameid(gameDaoEntity.getId());
			if (listAction == null || listAction.size() == 0) {
				continue;
			}
			for (GameAction gameAction : listAction) {
				listGameMap.add(this.formatAction(gameAction));
			}
		}
		return listRet;
	}

	@Override
	public RoomDaoEntity checkLastRoomResult(String account) {
		// TODO Auto-generated method stub
		GameUserRoom gameUserRoom = gameUserRoomDao
				.selectLastGameUserRoomByAccount(account);
		if (gameUserRoom == null) {
			return null;
		}
		int id = gameUserRoom.getRoomid();
		if (id == 0) {
			return null;
		}
		return roomDao.selectRoomById(id);
	}

	@Override
	public List<RoomDaoEntity> checkRoomResultsByTimeAndPageAndAccount(
			String account, Date endTime, Page page) {
		List<GameUserRoom> list = this.gameUserRoomDao
				.selectGameUserRoomsByAccountAndTimeAndPage(account, endTime,
						page);
		if (list == null || list.size() == 0) {
			return null;
		}
		List<RoomDaoEntity> listRet = new ArrayList<RoomDaoEntity>();
		for (GameUserRoom gur : list) {
			RoomDaoEntity rde = this.roomDao.selectRoomById(gur.getRoomid());
			if (rde == null) {
				continue;
			}
			listRet.add(rde);
		}
		return listRet;
	}

}
