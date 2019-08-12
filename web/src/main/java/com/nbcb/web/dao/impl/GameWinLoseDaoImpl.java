package com.nbcb.web.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.web.dao.GameWinLoseDao;
import com.nbcb.web.dao.entity.GameWinLose;
import com.nbcb.web.dao.entity.Page;

public class GameWinLoseDaoImpl implements GameWinLoseDao {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public GameWinLose selectGameWinLoseByOpenid(String openid) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne(
				"winlose.selectGameWinLoseByOpenid", openid);
	}

	@Override
	public List<GameWinLose> selectGameWinLoses() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("winlose.selectGameWinLoses");
	}

	@Override
	public List<GameWinLose> selectGameWinLosesByPage(Page page) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList(
				"winlose.selectGameWinLosesByPage", page);
	}

	@Override
	public int insertGameWinLose(GameWinLose gameWinLose) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("winlose.insertGameWinLose",
				gameWinLose);
	}

	@Override
	public int deleteGameWinLoseByOpenid(String openid) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("winlose.deleteGameWinLoseByOpenid",
				openid);
	}

	@Override
	public int updateGameWinLoseByOpenid(GameWinLose gameWinLose) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update(
				"winlose.updateGameWinLoseByOpenid", gameWinLose);
	}

}
