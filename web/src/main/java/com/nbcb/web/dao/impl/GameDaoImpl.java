package com.nbcb.web.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.web.dao.GameDao;
import com.nbcb.web.dao.entity.GameDaoEntity;

public class GameDaoImpl implements GameDao {



	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int insertGame(GameDaoEntity game) {
		return this.sqlSessionTemplate.insert("game.insertGame", game);
	}

	@Override
	public int updateGameUsers(GameDaoEntity game) {
		return this.sqlSessionTemplate.update("game.updateGameUsers", game);
	}

	@Override
	public int findTotalGameByRoomId(int id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("game.findTotalGameByRoomId",id);
	}

	@Override
	public List<GameDaoEntity> findUnfinishGameByRoomId(int id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("game.findUnfinishGameByRoomId",id);
	}
	
	
	/**
	 * 根据room表的主键查询所有属于该房间的game
	 * @param id room表的主键
	 * @return
	 */
	public List<GameDaoEntity> findGamesByRoomid(int id){
		return this.sqlSessionTemplate.selectList("game.findGamesByRoomid",id);
	}

}
