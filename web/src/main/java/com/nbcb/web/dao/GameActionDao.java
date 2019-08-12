package com.nbcb.web.dao;

import java.util.List;

import com.nbcb.web.dao.entity.GameAction;

public interface GameActionDao {

	public int insertGameAction(GameAction gameAction);

	public List<GameAction> selectGameActionsByGameid(int gameId);
}
