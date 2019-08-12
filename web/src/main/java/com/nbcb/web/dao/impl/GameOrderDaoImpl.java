package com.nbcb.web.dao.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.web.dao.GameOrderDao;
import com.nbcb.web.dao.entity.TimeRange;

public class GameOrderDaoImpl implements GameOrderDao {
	
	private static final Logger logger = LoggerFactory.getLogger(GameOrderDaoImpl.class);


	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int insertGameOrder(Map map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("game.insertGameOrder", map);
	}

	@Override
	public Map selectGameOrderByTradeNo(String outTradeNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectGameOrderByTradeNo",
				outTradeNo);
	}

	@Override
	public int getTotalPayByTimeRange(TimeRange timeRange) {
		// TODO Auto-generated method stub

		
		
		Map mapParam = new HashMap();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		mapParam.put("startTime", sdf.format(timeRange.getStartTime()));
		mapParam.put("endTime", sdf.format(timeRange.getEndTime()));
		logger.info("### getTotalPayByTimeRange mapParam["+mapParam+"]");
		Integer intRet = this.sqlSessionTemplate.selectOne(
				"game.getTotalPayByTimeRange", mapParam);
		if (intRet == null) {
			return 0;
		}
		return intRet.intValue();
	}

}
