package com.nbcb.web.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.helper.WinLosePlayerStrategy;
import com.nbcb.web.dao.GameWinLoseDao;
import com.nbcb.web.dao.entity.GameWinLose;

public class DbWinLosePlayerStrategy implements WinLosePlayerStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(DbWinLosePlayerStrategy.class);

	private GameWinLoseDao gameWinLoseDao;

	private Map<String, GameWinLose> map = new ConcurrentHashMap<String, GameWinLose>();

	private long expireTime = 0;

	private Object lock = new Object();

	public void start() {
		this.queryGameWinLoseInner();
	}

	public void queryGameWinLoseInner() {
		List<GameWinLose> list = this.gameWinLoseDao.selectGameWinLoses();
		logger.info("### list [" + list + "]");
		if (list == null || list.size() == 0) {
			expireTime = System.currentTimeMillis() + 60 * 1000;
			return;
		}
		for (GameWinLose gwl : list) {
			map.put(gwl.getOpenid(), gwl);
		}
		expireTime = System.currentTimeMillis() + 60 * 1000;
	}

	public void setGameWinLoseDao(GameWinLoseDao gameWinLoseDao) {
		this.gameWinLoseDao = gameWinLoseDao;
	}

	@Override
	public boolean isNeedWinPlayer(String account) {
		// TODO Auto-generated method stub

		if (System.currentTimeMillis() > this.expireTime) {
			synchronized (this.lock) {
				if (System.currentTimeMillis() > this.expireTime) {
					this.queryGameWinLoseInner();
				}
			}
		}

		GameWinLose gameWinLose = map.get(account);
		if (gameWinLose == null) {
			return false;
		}

		if (gameWinLose.getWinlose().equals("1")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isNeedLosePlayer(String account) {
		// TODO Auto-generated method stub

		GameWinLose gameWinLose = map.get(account);
		if (gameWinLose == null) {
			return false;
		}

		if (gameWinLose.getWinlose().equals("0")) {
			return true;
		}
		return false;
	}

	@Override
	public int needWinRatio(String account) {

		GameWinLose gameWinLose = map.get(account);
		if (gameWinLose == null) {
			return 0;
		}
		return gameWinLose.getRatio();
	}

	@Override
	public int needLoseRatio(String account) {
		// TODO Auto-generated method stub
		GameWinLose gameWinLose = map.get(account);
		if (gameWinLose == null) {
			return 0;
		}
		return gameWinLose.getRatio();
	}

}
