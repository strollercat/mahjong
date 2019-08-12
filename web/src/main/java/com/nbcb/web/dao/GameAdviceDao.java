package com.nbcb.web.dao;

import java.util.List;

import com.nbcb.web.dao.entity.GameAdvice;

public interface GameAdviceDao {

	public int insertGameAdvice(GameAdvice gameAdvice);

	public List<GameAdvice> seletGameAdvicesByAccount(String account);
}
