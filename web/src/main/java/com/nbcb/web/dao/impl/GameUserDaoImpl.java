package com.nbcb.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.common.util.BeanUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.Page;

public class GameUserDaoImpl implements GameUserDao {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public GameUser selectGameUserByAccount(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne(
				"game.selectGameUserByAccount", account);
	}

	@Override
	public int insertGameUser(GameUser gameUser) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("game.insertGameUser", gameUser);

	}

	@Override
	public int increaseWin(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("game.increaseWin", account);

	}

	@Override
	public int increaseLose(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("game.increaseLose", account);

	}

	@Override
	public int increasePing(String account) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("game.increasePing", account);

	}

	@Override
	public int addScore(GameUser gameUser) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("game.addScore", gameUser);

	}

	@Override
	public int addMoney(GameUser gameUser) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("game.addMoney", gameUser);

	}

	@Override
	public GameUser selectGameUserById(int id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("game.selectGameUserById", id);
	}

	@Override
	public int updateRecommendById(GameUser gameUser) {
		return this.sqlSessionTemplate.update("game.updateRecommendById",
				gameUser);
	}

	@Override
	public List<GameUser> selectRecommendGameUsersByPage(Page page) {
		return this.sqlSessionTemplate.selectList(
				"game.selectRecommendGameUsersByPage", page);
	}

	@Override
	public int selectTotalUsers() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("game.selectTotalUsers");
	}

	@Override
	public int selectTotalLiveUsers() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("game.selectTotalLiveUsers");
	}

	@Override
	public List<GameUser> selectGameUsersByPageAndScoredir(Page page,
			int scoreDir) {

		Map mapParam = BeanUtil.bean2Map(page);
		mapParam.put("scoreDir", scoreDir);
		return this.sqlSessionTemplate.selectList(
				"game.selectGameUsersByPageAndScoredir", mapParam);
	}

}
