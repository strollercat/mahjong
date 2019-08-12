package com.nbcb.web.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.BeanUtil;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.dao.entity.TimeRange;

public class RoomDaoImpl implements RoomDao {

	private static final Logger logger = LoggerFactory
			.getLogger(RoomDaoImpl.class);

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int insertRoom(RoomDaoEntity room) {
		return this.sqlSessionTemplate.insert("game.insertRoom", room);
	}

	@Override
	public int updateRoomUsers(RoomDaoEntity room) {
		return this.sqlSessionTemplate.update("game.updateRoomUsers", room);
	}

	@Override
	public RoomDaoEntity selectLastRoomByRoomId(String roomId) {
		return this.sqlSessionTemplate.selectOne("game.selectLastRoomByRoomId",
				roomId);
	}

	@Override
	public List<RoomDaoEntity> selectUnfinishRoomByCreateAccount(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList(
				"game.selectUnfinishRoomByCreateAccount", account);
	}

	@Override
	public List<RoomDaoEntity> selectUnfinishRoom() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("game.selectUnfinishRoom");

	}

	@Override
	public RoomDaoEntity selectRoomById(int id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("game.selectRoomById", id);
	}

	@Override
	public int totalRoomsOfTimes(TimeRange timeRange) {
		// TODO Auto-generated method stub
		Integer intRet = this.sqlSessionTemplate.selectOne(
				"game.totalRoomsOfTimes", timeRange);
		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();
	}

	@Override
	public int totalRoomsByTimesAndJuAndRecommend(TimeRange timeRange, int ju,
			int recommend) {
		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(timeRange));
		mapParam.put("ju", ju);
		mapParam.put("recommend", recommend);
		Integer intRet = this.sqlSessionTemplate.selectOne(
				"game.totalRoomsByTimesAndJuAndRecommend", mapParam);
		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();
	}

	@Override
	public List<RoomDaoEntity> selectRoomsByRecommendByTimeAndPage(
			int recommend, Date endTime, Page page) {
		// TODO Auto-generated method stub
		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(page));
		mapParam.put("recommend", recommend);
		mapParam.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(
				"game.selectRoomsByRecommendByTimeAndPage", mapParam);
	}

	@Override
	public int updateRoomCostMoney(RoomDaoEntity room) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateRoomCostMoney", room);
	}

	@Override
	public List<RoomDaoEntity> selectRoomsByTimeAndPage(Date endTime, Page page) {
		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(page));
		mapParam.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(
				"game.selectRoomsByTimeAndPage", mapParam);
	}

	@Override
	public int totalMoneyConsumedByTimesAndRecommend(TimeRange timeRange,
			int recommend) {
		// TODO Auto-generated method stub
		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(timeRange));
		mapParam.put("recommend", recommend);

		Integer intRet = this.sqlSessionTemplate.selectOne(
				"game.totalMoneyConsumedByTimesAndRecommend", mapParam);
		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();

	}

	public List<RoomDaoEntity> selectRoomsByCreateAccountByTimeAndPage(
			String createAccount, Date endTime, Page page) {

		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(page));
		mapParam.put("createAccount", createAccount);
		mapParam.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(
				"game.selectRoomsByCreateAccountByTimeAndPage", mapParam);

	}

}
