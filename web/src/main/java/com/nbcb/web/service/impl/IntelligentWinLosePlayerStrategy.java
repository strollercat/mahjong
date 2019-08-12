package com.nbcb.web.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.helper.WinLosePlayerStrategy;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;

public class IntelligentWinLosePlayerStrategy implements WinLosePlayerStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(IntelligentWinLosePlayerStrategy.class);

	private GameUserDao gameUserDao;

	private Map<String, GameUser> map = new ConcurrentHashMap<String, GameUser>();

	public void setGameUserDao(GameUserDao gameUserDao) {
		this.gameUserDao = gameUserDao;
	}

	private boolean isNewUser(GameUser gameUser) {
		if (gameUser.getWin() + gameUser.getLose() + gameUser.getPing() <= 8) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isNeedWinPlayer(String account) {
		// TODO Auto-generated method stub
		GameUser gameUser = gameUserDao.selectGameUserByAccount(account);
		if (gameUser == null) {
			return false;
		}
		map.put(account, gameUser);

		if (this.isNewUser(gameUser)) {
			logger.info("### account[" + account + "] is new User");
			return true;
		}
		return gameUser.getScore() < -1300;
	}

	@Override
	public boolean isNeedLosePlayer(String account) {
		// TODO Auto-generated method stub
		GameUser gameUser = map.get(account);
		if (gameUser == null) {
			return false;
		}
		return gameUser.getScore() > 3500;
	}

	@Override
	public int needWinRatio(String account) {
		// TODO Auto-generated method stub
		// GameUser gameUser = map.get(account);
		// if (gameUser == null) {
		// return 0;
		// }
		// if (gameUser.getScore() < -3000) {
		// return 40;
		// // return 100;
		// }
		//
		// if (gameUser.getScore() < -2000) {
		// return 30;
		// // return 100;
		// }
		// return 0;
		return 30;
	}

	@Override
	public int needLoseRatio(String account) {
		// TODO Auto-generated method stub
		return 30;
		// return 100;
	}

}
