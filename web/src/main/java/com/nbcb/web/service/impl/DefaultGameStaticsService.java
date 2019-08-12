package com.nbcb.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.GameOrderDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.dao.entity.TimeRange;
import com.nbcb.web.service.GameStaticsService;

public class DefaultGameStaticsService implements GameStaticsService {

	private RoomDao roomDao;

	private GameMoneyDetailDao gameMoneyDetailDao;

	private GameOrderDao gameOrderDao;

	public void setGameOrderDao(GameOrderDao gameOrderDao) {
		this.gameOrderDao = gameOrderDao;
	}

	public void setGameMoneyDetailDao(GameMoneyDetailDao gameMoneyDetailDao) {
		this.gameMoneyDetailDao = gameMoneyDetailDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	@Override
	public Map getTotalRoomsAndMoneyConsumedByRecommmendAndTimeRange(
			int recommend, TimeRange timeRange) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("r4", roomDao.totalRoomsByTimesAndJuAndRecommend(timeRange, 4,
				recommend));
		map.put("r8", roomDao.totalRoomsByTimesAndJuAndRecommend(timeRange, 8,
				recommend));
		map.put("r12", roomDao.totalRoomsByTimesAndJuAndRecommend(timeRange,
				12, recommend));
		map.put("r16", roomDao.totalRoomsByTimesAndJuAndRecommend(timeRange,
				16, recommend));
		map.put("totalMoney", roomDao.totalMoneyConsumedByTimesAndRecommend(timeRange, recommend));
		return map;

	}

	@Override
	public int getTotalRoomsByTimeRange(TimeRange timeRange) {
		// TODO Auto-generated method stub
		return this.roomDao.totalRoomsOfTimes(timeRange);
	}

	@Override
	public int getTotalMoneyConsumedByTimeRange(TimeRange timeRange) {
		// TODO Auto-generated method stub
		int money = this.gameMoneyDetailDao.totalMoneyByActionAndTimeRange(
				GameMoneyDetail.PLAYGAMEACTION, timeRange);
		return -money;
	}

	@Override
	public int getTotalPayByTimeRange(TimeRange timeRange) {
		return this.gameOrderDao.getTotalPayByTimeRange(timeRange);
	}

	@Override
	public List<RoomDaoEntity> selectRoomsByRecommendByTimeAndPage(
			int recommend, Date endTime, Page page) {
		// TODO Auto-generated method stub
		return this.roomDao.selectRoomsByRecommendByTimeAndPage(recommend,
				endTime, page);
	}

	@Override
	public List<RoomDaoEntity> selectRoomsByTimeAndPage(Date endTime, Page page) {
		return this.roomDao.selectRoomsByTimeAndPage(endTime, page);
	}
}
