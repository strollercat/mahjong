package com.nbcb.web.dao;

import java.util.List;

import com.nbcb.web.dao.entity.GameDaoEntity;

public interface GameDao {
	
	public int insertGame(GameDaoEntity game);
	
	/**
	 * 更新用户以及结束时间
	 * @param game
	 * @return
	 */
	public int updateGameUsers(GameDaoEntity game);
	
	/**
	 * 根据room表的主键查询所有属于该房间的game总数
	 * @param id room表的主键
	 * @return
	 */
	public int findTotalGameByRoomId(int id);
	
	/**
	 * 根据room表的主键查询所有属于该房间的game
	 * @param id room表的主键
	 * @return
	 */
	public List<GameDaoEntity> findGamesByRoomid(int id);
	
	/**
	 * 根据room表的主键查询所有没有正常结束的Game
	 * @param id room表的主键
	 * @return
	 */
	public List<GameDaoEntity> findUnfinishGameByRoomId(int id);

}
