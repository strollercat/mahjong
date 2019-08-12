package com.nbcb.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.core.helper.WinLosePlayerStrategy;
import com.nbcb.web.dao.GameDictDao;

public class ChooseWinLosePlayerStrategy implements WinLosePlayerStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(ChooseWinLosePlayerStrategy.class);

	private Map<String, WinLosePlayerStrategy> mapStrategy = new HashMap<String, WinLosePlayerStrategy>();

	private long expireTime = 0;

	private Object lock = new Object();

	private WinLosePlayerStrategy currentStrategy;

	private GameDictDao gameDictDao;

	public void setGameDictDao(GameDictDao gameDictDao) {
		this.gameDictDao = gameDictDao;
	}

	public void setMapStrategy(Map<String, WinLosePlayerStrategy> mapStrategy) {
		this.mapStrategy = mapStrategy;
	}

	public void start() {
		this.chooseStrategy();
	}

	private void chooseStrategy() {
		String value = gameDictDao.selectValueByKey("winlose");
		if (StringUtils.isEmpty(value)) {
			currentStrategy = mapStrategy.get("int");
			this.expireTime = System.currentTimeMillis() + 5 * 60 * 1000;
			return;
		}
		currentStrategy = mapStrategy.get(value);
		if (currentStrategy == null) {
			currentStrategy = mapStrategy.get("int");
		}
		this.expireTime = System.currentTimeMillis() + 5 * 60 * 1000;
	}

	@Override
	public boolean isNeedWinPlayer(String account) {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() > this.expireTime) {
			synchronized (this.lock) {
				if (System.currentTimeMillis() > this.expireTime) {
					this.chooseStrategy();
				}
			}
		}
		logger.info("### currentStrategy[" + currentStrategy.getClass() + "]");
		return this.currentStrategy.isNeedWinPlayer(account);
	}

	@Override
	public boolean isNeedLosePlayer(String account) {
		// TODO Auto-generated method stub
		return this.currentStrategy.isNeedLosePlayer(account);
	}

	@Override
	public int needWinRatio(String account) {
		// TODO Auto-generated method stub
		return this.currentStrategy.needWinRatio(account);
	}

	@Override
	public int needLoseRatio(String account) {
		// TODO Auto-generated method stub
		return this.currentStrategy.needLoseRatio(account);
	}

}
