package com.nbcb.web.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.common.util.BeanUtil;
import com.nbcb.web.dao.GameUserRoomDao;
import com.nbcb.web.dao.entity.GameUserRoom;
import com.nbcb.web.dao.entity.Page;

public class GameUserRoomDaoImpl implements GameUserRoomDao {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int insertGameUserRoom(GameUserRoom gameUserRoom) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("game.insertGameUserRoom",
				gameUserRoom);
	}

	@Override
	public GameUserRoom selectLastGameUserRoomByAccount(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne(
				"game.selectLastGameUserRoomByAccount", account);
	}

	@Override
	public List<GameUserRoom> selectGameUserRoomsByAccountAndTimeAndPage(
			String account, Date endTime,Page page) {

		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(page));
		mapParam.put("account", account);
		mapParam.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(
				"game.selectGameUserRoomsByAccountAndTimeAndPage", mapParam);
	}

}
