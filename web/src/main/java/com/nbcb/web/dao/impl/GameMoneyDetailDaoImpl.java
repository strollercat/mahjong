package com.nbcb.web.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.common.util.BeanUtil;
import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.TimeRange;

public class GameMoneyDetailDaoImpl implements GameMoneyDetailDao {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int insertGameMoneyDetail(GameMoneyDetail gameMoneyDetail) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("game.insertGameMoneyDetail",
				gameMoneyDetail);
	}

	@Override
	public List<GameMoneyDetail> selectGameMoneyDetailsByAccount(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList(
				"game.selectGameMoneyDetailsByAccount", account);
	}

	@Override
	public int totalMoneyByActionAndTimeRange(String action, TimeRange timeRange) {
		// TODO Auto-generated method stub
		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(timeRange));
		mapParam.put("action", action);
		Integer intRet = this.sqlSessionTemplate.selectOne(
				"game.totalMoneyByActionAndTimeRange", mapParam);

		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();
	}

	@Override
	public int totalMoneyByActionAccountAndTimeRange(String account,
			String action, TimeRange timeRange) {
		Map mapParam = new HashMap();
		mapParam.putAll(BeanUtil.bean2Map(timeRange));
		mapParam.put("action", action);
		mapParam.put("account", account);
		Integer intRet = this.sqlSessionTemplate.selectOne(
				"game.totalMoneyByActionAccountAndTimeRange", mapParam);

		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();

	}

	@Override
	public List<GameMoneyDetail> getMoneyDetailsByActionAccountAndPage(
			String account, String action, Date endTime, Page page) {
		Map mapParam = new HashMap();
		mapParam.put("account", account);
		mapParam.put("action", action);
		mapParam.putAll(BeanUtil.bean2Map(page));
		mapParam.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList(
				"game.getMoneyDetailsByActionAccountAndPage", mapParam);
	}
}
