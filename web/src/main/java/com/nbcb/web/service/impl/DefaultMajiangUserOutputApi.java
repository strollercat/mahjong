package com.nbcb.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.io.RoomUserOutputApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;
import com.nbcb.web.dao.GameActionDao;
import com.nbcb.web.dao.entity.GameAction;

public class DefaultMajiangUserOutputApi extends RoomUserOutputApi {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultMajiangUserOutputApi.class);

	private GameActionDao gameActionDao;

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setGameActionDao(GameActionDao gameActionDao) {
		this.gameActionDao = gameActionDao;
	}

	private boolean normalConnect(Room room) {
		return false;

		// Game game = room.currentGame();
		// if (room.getOrder() != 1) {
		// logger.info("roomOrder[" + room.getOrder() + "]");
		// return false;
		// }
		// Actions actions = game.getHistoryActions();
		// int size = actions.size();
		// Action action;
		// for (int i = 0; i < size; i++) {
		// action = actions.getAction(i);
		// if (action.getType() == MajiangAction.DANONHUA
		// || action.getType() == MajiangAction.ANGANG) {
		// return false;
		// }
		// }
		// return true;
	}

	protected Map codeGameNotice(Room room) {
		// TODO Auto-generated method stub
		Game game = room.currentGame();
		Map mapRet = room.format();
		mapRet.put("code", "gameNotice");
		mapRet.put("leftJu", room.getOrder());
		mapRet.putAll(game.format());

		mapRet.put("arrow", -1);
		Actions historyActions = game.getHistoryActions();
		for (int i = historyActions.size() - 1; i >= 0; i--) {
			Action tmpAction = historyActions.getAction(i);
			if (tmpAction.getType() == MajiangAction.DANONHUA) {
				// logger.info("### found tmpAction["+tmpAction.getPlayer().getAccount()+"]i["+i+"] ");
				mapRet.put("arrow", tmpAction.getPlayer().getPlayerOrder());
				break;
			}
		}

		mapRet.put("middleArrow", -1);
		Action[] nextDaAction = game.getLegalActions().getActionsByType(
				MajiangAction.DANONHUA);
		if (nextDaAction != null && nextDaAction.length > 0) {
			mapRet.put("middleArrow", nextDaAction[0].getPlayer()
					.getPlayerOrder());
		}

		return mapRet;
	}

	private void sendManaged(MajiangGame mjGame) {
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			Player tmpPlayer = mjGame.getPlayerByIndex(i);
			if (mjGame.isPlayerManagerd(tmpPlayer.getAccount())) {
				Map map = new HashMap();
				map.put("code", "managed");
				map.put("dir", tmpPlayer.getPlayerOrder());
				map.put("managed", true);
				for (int j = 0; j < mjGame.getActivePlayers(); j++) {
					this.clientPusher.push(mjGame.getPlayerByIndex(j)
							.getAccount(), map);
				}
			}
		}
	}

	@Override
	public void gameConnect(String account) {
		// TODO Auto-generated method stub

		Room room = server.getRoomByAccount(account);
		Player player = server.getPlayer(account);

		synchronized (room.currentGame()) {
			Map mapRet = this.codeOnline(player);

			for (int i = 0; i < room.getActivePlayers(); i++) {
				if (room.getPlayerByIndex(i).getAccount()
						.equals(player.getAccount())) {
					continue;
				}
				Map mapInner = new HashMap();
				mapInner.putAll(mapRet);
				mapInner = this.responseFilter.filter(player, mapInner, room);
				this.clientPusher.push(room.getPlayerByIndex(i).getAccount(),
						mapInner);
			}

			if (this.normalConnect(room)) { // 正常连接
				mapRet = this.codeRoomNotice(room);
				mapRet = this.responseFilter.filter(player, mapRet, room);
				this.clientPusher.push(account, mapRet);

				mapRet = this.codeGameStart(room);
				mapRet = this.responseFilter.filter(player, mapRet, room);
				this.clientPusher.push(account, mapRet);
			} else { // 断线重连
				mapRet = this.codeGameNotice(room);
				mapRet = this.responseFilter.filter(player, mapRet, room);
				this.clientPusher.push(account, mapRet);
			}
			List<Map> listNext = this.codeNext(room.currentGame());
			if (listNext == null || listNext.size() == 0) {
				return;
			}
			for (int i = 0; i < listNext.size(); i++) {
				Map mapNext = listNext.get(i);
				int dir = (Integer) mapNext.get("dir");
				if (account.equals(room.getPlayerByIndex(dir).getAccount())) {
					this.clientPusher.push(room.getPlayerByIndex(dir)
							.getAccount(), mapNext);
				}
			}
		}
		if (room.currentGame() != null) {
			this.sendManaged((MajiangGame) room.currentGame());
		}

	}

	private void processInnerHuCard(MajiangHuResult mjHu,
			List<Map<String, Object>> cards) {
		Player huPlayer = mjHu.getMjPlayer();
		Map huCards = cards.get(huPlayer.getPlayerOrder());
		int[] icard = (int[]) huCards.get("i");
		int huCard = mjHu.getMjHuCards().getMjHuCard().getNumber();
		if (icard.length % 3 != 2) {
			int[] anotherCards = new int[icard.length + 1];
			System.arraycopy(icard, 0, anotherCards, 0, icard.length);
			anotherCards[icard.length] = huCard;
			huCards.put("i", anotherCards);
		} else {
			int[] anotherCards = new int[icard.length];
			int index = 0;
			for (int i = 0; i < icard.length; i++) {
				if (icard[i] != huCard) {
					anotherCards[index++] = icard[i];
				}
			}
			anotherCards[index++] = huCard;
			huCards.put("i", anotherCards);
		}
	}

	private Map codeResult(Game game) {
		MajiangGame mjGame = (MajiangGame) game;
		Map mapRet = new HashMap();
		mapRet.put("code", "result");

		Map mapGame = game.format();
		List<Map<String, Object>> cards = (List<Map<String, Object>>) mapGame
				.get("cards");
		mapRet.put("rs", cards);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).remove("o");
		}
		MajiangHuResults mjHus = mjGame.getMajiangHuResults();
		if (mjHus.getMajiangHuResult(0).getHuType() != MajiangAction.HUANGPAI) {
			for (int i = 0; i < mjHus.size(); i++) {
				MajiangHuResult mjHu = mjHus.getMajiangHuResult(i);
				Player huPlayer = mjHu.getMjPlayer();
				cards.get(huPlayer.getPlayerOrder()).put("hu",
						mjHu.getDetails());
				this.processInnerHuCard(mjHu, cards);
			}
		}

		PlayerScores pss = mjGame.getPlayerScores();
		for (int i = 0; i < game.getActivePlayers(); i++) {
			Player player = game.getPlayerByIndex(i);
			cards.get(i).put("score", pss.getScore(player.getAccount()));
		}
		return mapRet;
	}

	static class NextAction {

		private String a;
		private List<int[]> cns = null;
		private int cn = -1;

		public NextAction(String a) {
			this.a = a;
		}

		public NextAction(String a, int cn) {
			this.a = a;
			this.cn = cn;
		}

		public NextAction(String a, List<int[]> cns) {
			this.a = a;
			this.cns = cns;
		}

		public Map format() {
			Map mapRet = new HashMap();
			mapRet.put("a", this.a);
			if (this.cns == null && this.cn == -1) {
				return mapRet;
			} else if (this.cns != null && this.cn == -1) {
				mapRet.put("cns", cns);
				return mapRet;
			} else if (this.cn != -1 && this.cns == null) {
				mapRet.put("cns", this.cn);
			}
			return mapRet;
		}
	}

	private MajiangActions getUserAction(Action[] actions) {
		MajiangActions mas = new MajiangActions();
		for (int i = 0; i < actions.length; i++) {
			if (actions[i].isUserAction()) {
				mas.addAction(actions[i]);
			}
		}
		if (mas.size() == 0) {
			return null;
		}
		return mas;
	}

	private Action[] findHuActions(MajiangActions mas) {
		List<Action> list = new ArrayList<Action>();
		for (int i = 0; i < mas.size(); i++) {
			Action a = mas.getAction(i);
			if (a.getType() == MajiangAction.MOFRONTHU
					|| a.getType() == MajiangAction.MOBACKHU
					|| a.getType() == MajiangAction.FANGQIANGHU
					|| a.getType() == MajiangAction.QIANGGANGHU) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Action[] actionRet = new Action[list.size()];
		list.toArray(actionRet);
		return actionRet;
	}

	private Action[] mergeActionArry(Action[] a1, Action[] a2) {
		if (a1 == null || a1.length == 0) {
			return a2;
		}
		if (a2 == null || a2.length == 0) {
			return a1;
		}

		Action[] actionRet = new Action[a1.length + a2.length];
		System.arraycopy(a1, 0, actionRet, 0, a1.length);
		System.arraycopy(a2, 0, actionRet, a1.length, a2.length);
		return actionRet;
	}

	private int[] addTo4Item(int[] ints) {
		if (ints.length == 4) {
			return ints;
		}
		List<Integer> list = new ArrayList();
		if (ints.length < 4) {
			list.add(ints[0]);
			list.add(ints[0]);
			list.add(ints[0]);
			list.add(ints[0]);
		}
		int intRet[] = new int[4];
		for (int i = 0; i < 4; i++) {
			intRet[i] = list.get(i);
		}
		return intRet;
	}

	private List<Map> codeNext(Game game) {

		Actions nextActions = game.getLegalActions();
		if (nextActions == null || nextActions.size() == 0) {
			return null;
		}
		List<Map> listRet = new ArrayList<Map>();
		Action[] nextGenAction = nextActions
				.getActionsByType(MajiangAction.GEN);
		if (nextGenAction != null && nextGenAction.length > 0) {// 有跟的，只送跟不送其他
			Actions realNextActions = new MajiangActions();
			for (int i = 0; i < nextGenAction.length; i++) {
				realNextActions.addAction(nextGenAction[i]);
			}
			nextActions = realNextActions;
		}
		for (int i = 0; i < game.getActivePlayers(); i++) {
			Action[] naPlayer = nextActions.getActionsByPlayer(game
					.getPlayerByIndex(i).getAccount());
			if (naPlayer == null || naPlayer.length == 0) {
				continue;
			}
			MajiangActions mas = this.getUserAction(naPlayer);
			if (mas == null || mas.size() == 0) {
				continue;
			}
			Map mapRet = new HashMap();
			mapRet.put("code", "next");
			mapRet.put("dir", game.getPlayerByIndex(i).getPlayerOrder());
			List<Map> asList = new ArrayList<Map>();
			mapRet.put("as", asList);

			Action[] genAction = mas.getActionsByType(MajiangAction.GEN);
			if (genAction != null && genAction.length > 0) {
				asList.add(new NextAction("gen").format());
			}
			Action[] daAction = mas.getActionsByType(MajiangAction.DANONHUA);
			if (daAction != null && daAction.length > 0) {
				int[] canNotDa = (int[]) (daAction[0]
						.getAttribute(MajiangAction.CANNOTDA));
				if (null == canNotDa) {
					asList.add(new NextAction("da").format());
				} else {
					List<int[]> listCanNotDa = new ArrayList();
					listCanNotDa.add(canNotDa);
					asList.add(new NextAction("da", listCanNotDa).format());
				}
			}
			Action[] pengAction = mas.getActionsByType(MajiangAction.PENG);
			if (pengAction != null && pengAction.length > 0) {
				asList.add(new NextAction("peng").format());
			}
			Action[] mingGangAction = mas
					.getActionsByType(MajiangAction.MINGGANG);
			if (mingGangAction != null && mingGangAction.length > 0) {
				asList.add(new NextAction("gang", mingGangAction[0].getCards()
						.getHeadCard().getNumber()).format());
			}
			Action[] xianGangAction = mas
					.getActionsByType(MajiangAction.XIANGANG);
			Action[] anGangAction = mas.getActionsByType(MajiangAction.ANGANG);
			anGangAction = this.mergeActionArry(xianGangAction, anGangAction);
			if (anGangAction != null && anGangAction.length > 0) {
				if (anGangAction.length == 1) {
					asList.add(new NextAction("gang", anGangAction[0]
							.getCards().getHeadCard().getNumber()).format());
				} else {
					List<int[]> listCard = new ArrayList<int[]>();
					for (int j = 0; j < anGangAction.length; j++) {
						listCard.add(this.addTo4Item(anGangAction[j].getCards()
								.toNumberArray()));
					}
					asList.add(new NextAction("gang", listCard).format());
				}
			}
			Action[] huAction = this.findHuActions(mas);
			if (huAction != null && huAction.length > 0) {
				asList.add(new NextAction("hu").format());
			}
			Action[] chiAction = mas.getActionsByType(MajiangAction.CHI);
			if (chiAction != null && chiAction.length > 0) {
				List<int[]> listCard = new ArrayList<int[]>();
				for (int j = 0; j < chiAction.length; j++) {
					int[] intCards = new int[3];
					Actions histroyActions = game.getHistoryActions();
					intCards[0] = histroyActions
							.getAction(histroyActions.size() - 1).getCards()
							.getHeadCard().getNumber();
					intCards[1] = chiAction[j].getCards().toNumberArray()[0];
					intCards[2] = chiAction[j].getCards().toNumberArray()[1];
					listCard.add(intCards);
				}
				asList.add(new NextAction("chi", listCard).format());
			}
			listRet.add(mapRet);
		}
		return listRet;
	}

	protected Map codeGameStart(Room room) {
		Map mapRet = room.currentGame().format();
		mapRet.put("code", "gameStart");
		mapRet.put("leftJu", room.getOrder());

		return mapRet;
	}

	protected void asynInsertPushAction(final Game game, final Action action,
			final Map mapPush) {
		this.taskExecutor.submit(new Runnable() {
			public void run() {
				try {
					GameAction ga = new GameAction();
					ga.setAccount(action.getPlayer() == null ? null : action
							.getPlayer().getAccount());
					ga.setAction(action.getType());
					ga.setAction_time(new Date());
					ga.setActionindex(0);
					ga.setCards(action.getCards() == null ? "" : JsonUtil
							.encode(action.getCards().toNumberArray()));
					ga.setGameid(game.getUniqueId());
					ga.setUseraction(action.isUserAction() ? 1 : 0);
					ga.setAttribute(JsonUtil.encode(mapPush));
					gameActionDao.insertGameAction(ga);
				} catch (Exception e) {
					logger.error("### error", e);
				}
			}
		});
	}

	private int[] ajustInnerOrder(int[] numberArr, int huNumber) {
		if (numberArr == null || numberArr.length == 0) {
			return null;
		}
		if (numberArr.length % 3 != 2) {
			return numberArr;
		}
		int[] retNumber = new int[numberArr.length];
		int index = 0;
		boolean contain = false;
		for (int i = 0; i < retNumber.length; i++) {
			if (numberArr[i] == huNumber) {
				contain = true;
				continue;
			}
			retNumber[index++] = numberArr[i];
		}
		if (contain) {
			retNumber[numberArr.length - 1] = huNumber;
		}
		return retNumber;
	}

	private List<Map<String, Object>> getMajiangHuCards(MajiangGame mjGame) {
		if (mjGame.getMajiangHuResults().getMajiangHuResult(0).getHuType() == MajiangAction.HUANGPAI) {
			return null;
		}
		try {
			List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < mjGame.getMajiangHuResults().size(); i++) {
				MajiangPlayer tmpPlayer = mjGame.getMajiangHuResults()
						.getMajiangHuResult(i).getMjPlayer();
				if (tmpPlayer != null) {
					Map tmpMap = new HashMap<String, Object>();
					tmpMap.put("dir", tmpPlayer.getPlayerOrder());
					tmpMap.put("ics", this.ajustInnerOrder(tmpPlayer
							.getMajiangInnerCards().toNumberArray(), mjGame
							.getMajiangHuResults().getMajiangHuResult(i)
							.getMjHuCards().getMjHuCard().getNumber()));
					tmpList.add(tmpMap);
				}
			}
			return tmpList.size() == 0 ? null : tmpList;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void listen(Game game, Action action) {
		// TODO Auto-generated method stub
		Map mapRet = null;
		MajiangUnitCards mucs;
		Room room = game.getRoom();
		int type = action.getType();
		MajiangGame mjGame = (MajiangGame) game;
		MajiangPlayer mjPlayer = (MajiangPlayer) action.getPlayer();

		if (type == MajiangAction.ALLOCATE) {
			mapRet = this.codeGameStart(game.getRoom());
			mapRet.put("hua", action.getAttribute("HUATAISHUALL"));
		} else if (type == MajiangAction.MOBACK
				|| type == MajiangAction.MOFRONT) {
			mapRet = new HashMap();
			mapRet.put("code", "mo");
			mapRet.put("dir", action.getPlayer().getPlayerOrder());
			mapRet.put("card", action.getCards().getHeadCard().getNumber());
		} else if (type == MajiangAction.DANONHUA) {
			mapRet = new HashMap();
			mapRet.put("code", "da");
			mapRet.put("dir", action.getPlayer().getPlayerOrder());
			mapRet.put("card", action.getCards().getHeadCard().getNumber());
		} else if (type == MajiangAction.DAHUA) {
			mapRet = new HashMap();
			mapRet.put("code", "dahua");
			mapRet.put("dir", action.getPlayer().getPlayerOrder());
			mapRet.put("card", action.getCards().getHeadCard().getNumber());

			Integer huaTaiShu = (Integer) action.getAttribute("HUATAISHU");
			if (huaTaiShu != null) {
				mapRet.put("tai", huaTaiShu);
			}
		} else if (type == MajiangAction.MOFRONTHU
				|| type == MajiangAction.MOBACKHU
				|| type == MajiangAction.FANGQIANGHU
				|| type == MajiangAction.QIANGGANGHU) {
			mapRet = new HashMap();
			mapRet.put("code", "hu");
			mapRet.put("dir", action.getPlayer().getPlayerOrder());
		} else if (type == MajiangAction.CHI || type == MajiangAction.PENG
				|| type == MajiangAction.ANGANG
				|| type == MajiangAction.XIANGANG
				|| type == MajiangAction.MINGGANG) {
			mapRet = new HashMap();
			mapRet.put("code", "chipenggang");
			mapRet.put("dir", action.getPlayer().getPlayerOrder());
			mucs = mjPlayer.getMajiangMiddleCards().getLatestMajiangUnitCards();
			mapRet.put("i", action.getCards().toNumberArray());
			mapRet.putAll(mucs.format());
		} else if (type == MajiangAction.NO) {
			Object obj = ((MajiangAction) action).getAttribute("bugen");
			if (obj == null) { // 是过而不是不跟
				mapRet = new HashMap();
				mapRet.put("code", "no");
				mapRet.put("dir", action.getPlayer().getPlayerOrder());
			} else {
				mapRet = new HashMap();
				mapRet.put("code", "gen");
				mapRet.put("dir", (Integer) obj);
				mapRet.put("gen", false);
			}
		} else if (type == MajiangAction.COMPLETE) {
			mapRet = this.codeResult(game);
		} else if (type == MajiangAction.GEN) {
			mapRet = new HashMap();
			mapRet.put("code", "gen");
			mapRet.put("dir", action.getPlayer().getPlayerOrder());
			mapRet.put("gen", true);
		} else if (type == MajiangAction.MAIMA) {
			mapRet = new HashMap();
			mapRet.put("code", "maima");
			mapRet.put("cards", action.getAttribute(MajiangAction.MAIMACARDS));

			List<Map<String, Object>> mjHuCards = this
					.getMajiangHuCards(mjGame);
			if (mjHuCards != null) {
				mapRet.put("hucards", mjHuCards);
			}
		}

		this.asynInsertPushAction(game, action, mapRet);

		if (mapRet != null) {
			Map filterMap;
			for (int i = 0; i < game.getActivePlayers(); i++) {
				Player player = game.getPlayerByIndex(i);
				filterMap = responseFilter.filter(player, mapRet, room);
				if (filterMap != null) {
					this.clientPusher.push(player.getAccount(), filterMap);
				}
			}
		}

		if (game.getLegalActions() == null) { // game stop
			Map mapRoomStart = this.codeRoomStart(game.getRoom());
			for (int i = 0; i < game.getActivePlayers(); i++) {
				this.clientPusher.push(game.getPlayerByIndex(i).getAccount(),
						mapRoomStart);
			}
			return;
		}

		if (null != ((MajiangAction) action).getAttribute("NOTNEXT")) {
			return;
		}
		List<Map> listNext = this.codeNext(mjGame);
		if (listNext == null || listNext.size() == 0) {
			return;
		}
		for (int i = 0; i < listNext.size(); i++) {
			Map mapNext = listNext.get(i);
			int dir = (Integer) mapNext.get("dir");
			this.clientPusher.push(mjGame.getPlayerByIndex(dir).getAccount(),
					mapNext);
		}
	}

}
