package com.nbcb.web.db;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.action.Action;
import com.nbcb.core.action.ActionListener;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.web.dao.GameDao;
import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.GameDaoEntity;
import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.MoneyStrategyService;

public class DatabaseActionListener implements ActionListener {

	private static final Logger logger = LoggerFactory
			.getLogger(DatabaseActionListener.class);

	private GameDao gameDao;

	private GameUserDao gameUserDao;

	private MoneyStrategyService moneyStrategyService;

	private GameMoneyDetailDao gameMoneyDetailDao;

	private RoomDao roomDao;

	protected ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setGameMoneyDetailDao(GameMoneyDetailDao gameMoneyDetailDao) {
		this.gameMoneyDetailDao = gameMoneyDetailDao;
	}

	public void setGameUserDao(GameUserDao gameUserDao) {
		this.gameUserDao = gameUserDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	public void setMoneyStrategyService(
			MoneyStrategyService moneyStrategyService) {
		this.moneyStrategyService = moneyStrategyService;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	protected void allocateAction(Game game, Action action) {
		try {
			GameDaoEntity gameDaoEntity = new GameDaoEntity();
			gameDaoEntity.setCreate_time(new Date(System.currentTimeMillis()));
			gameDaoEntity.setGame_info(JsonUtil.encode(game.getGameInfo()));
			gameDaoEntity.setRoomid(game.getRoom().getUniqueId());
			gameDao.insertGame(gameDaoEntity);
			game.setUniqueId(gameDaoEntity.getId());

			if (game.getRoom().getOrder() == 1) { // 第一局开始，把钱扣掉
				logger.info("### 是第一局游戏，开始扣钱");

				String account = game.getRoom().getCreatePlayer();
				int money = moneyStrategyService.needMoney(game.getRoom()
						.getRoomInfo());
				logger.info("### 开始扣[" + account + "]的钱[" + money + "]！！！");
				moneyStrategyService.costMoney(account, money);

				GameMoneyDetail gameMoneyDetail = new GameMoneyDetail();
				gameMoneyDetail.setMoney(-money);
				gameMoneyDetail.setOpenid(account);
				gameMoneyDetail.setAction(GameMoneyDetail.PLAYGAMEACTION);
				gameMoneyDetail.setRemark(GameMoneyDetail.PLAYGAMEREMARK);
				gameMoneyDetail.setTime(new Date());
				gameMoneyDetail.setRelate(game.getRoom().getUniqueId()+"");
				gameMoneyDetailDao.insertGameMoneyDetail(gameMoneyDetail);

				logger.info("### roomId[" + game.getRoom().getId() + "]共消耗钻石["
						+ money + "]");
				RoomDaoEntity tmpRde = new RoomDaoEntity();
				tmpRde.setId(game.getRoom().getUniqueId());
				tmpRde.setCost_money(money);
				this.roomDao.updateRoomCostMoney(tmpRde);
			}
		} catch (Exception e) {
			logger.error("### ,error", e);
		}
	}

	protected void completeAction(Game game, Action action) {
		try {
//			MajiangGame mjGame = (MajiangGame) game;

			GameDaoEntity gameDaoEntity = new GameDaoEntity();
			gameDaoEntity.setId(game.getUniqueId());
			gameDaoEntity.setUsers(JsonUtil.encode(game.getPlayerScores()
					.format()));
			gameDaoEntity.setEnd_time(new Date(System.currentTimeMillis()));
			gameDao.updateGameUsers(gameDaoEntity);

			logger.info("### 开始更新用户胜率与积分信息");
			PlayerScores pss = game.getPlayerScores();
			Set<String> accountSet = pss.accountSet();
			for (String account : accountSet) {
				int score = pss.getScore(account);
				if (score > 0) {
					gameUserDao.increaseWin(account);
				} else if (score == 0) {
					gameUserDao.increasePing(account);
				} else if (score < 0) {
					gameUserDao.increaseLose(account);
				}
				GameUser gameUser = new GameUser();
				gameUser.setOpenid(account);
				gameUser.setScore(score);
				gameUserDao.addScore(gameUser);
			}
		} catch (Exception e) {
			logger.error("###,error", e);
		}
	}

	@Override
	public void listen(Game game, Action action) {
		// TODO Auto-generated method stub
		final Game fGame = game;
		final Action fAction = action;
		if (action.getType() == MajiangAction.COMPLETE) {
			taskExecutor.submit(new Runnable() {
				public void run() {
					DatabaseActionListener.this.completeAction(fGame, fAction);
				}
			});
		} else if (action.getType() == MajiangAction.ALLOCATE) {
			taskExecutor.submit(new Runnable() {
				public void run() {
					DatabaseActionListener.this.allocateAction(fGame, fAction);
				}
			});
		}
	}
}
