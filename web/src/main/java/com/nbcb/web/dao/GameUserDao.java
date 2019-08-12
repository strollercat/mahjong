package com.nbcb.web.dao;

import java.util.List;

import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.Page;

public interface GameUserDao {
	
	public GameUser selectGameUserById(int id);

	public GameUser selectGameUserByAccount(String account);
	
	public List<GameUser> selectRecommendGameUsersByPage(Page page);
	
	public int selectTotalUsers();
	
	public int selectTotalLiveUsers();

	public int insertGameUser(GameUser gameUser);

	public int increaseWin(String account);

	public int increaseLose(String account);

	public int increasePing(String account);

	public int addScore(GameUser gameUser);

	public int addMoney(GameUser gameUser);

	public int updateRecommendById(GameUser gameUser);
	
	public List<GameUser> selectGameUsersByPageAndScoredir(Page page,int scoreDir);
}
