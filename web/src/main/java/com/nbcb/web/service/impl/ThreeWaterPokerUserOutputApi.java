package com.nbcb.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.room.Room;
import com.nbcb.core.user.Player;
import com.nbcb.poker.threewater.ThreeWaterPlayerScores;
import com.nbcb.poker.threewater.ThreeWaterPokerAction;
import com.nbcb.poker.threewater.ThreeWaterPokerCards;
import com.nbcb.poker.threewater.ThreeWaterPokerGame;
import com.nbcb.poker.threewater.ThreeWaterPokerPlayer;

public class ThreeWaterPokerUserOutputApi extends DefaultMajiangUserOutputApi {

	private List<Map> codeCards(ThreeWaterPokerGame twGame) {
		List<Map> listCards = new ArrayList();

		for (int i = 0; i < twGame.getActivePlayers(); i++) {
			ThreeWaterPokerPlayer twp = (ThreeWaterPokerPlayer) twGame
					.getPlayerByIndex(i);
			ThreeWaterPokerCards twpcs = twp.getThreeWaterPokerCards();

			Map mapCards = new HashMap();
			listCards.add(mapCards);

			mapCards.put("dir", twp.getPlayerOrder());
			mapCards.putAll(twpcs.format());

		}
		return listCards;

	}

	private Map codeScores(ThreeWaterPokerGame twGame) {
		ThreeWaterPlayerScores twPss = (ThreeWaterPlayerScores) twGame
				.getPlayerScores();

		Map retMap = twPss.format();

		String quanleida = (String) retMap.get("quanLeiDa");
		if(quanleida != null){
			retMap.put("quanLeiDa", twGame.getPlayerByAccount(quanleida)
					.getPlayerOrder());
		}
		

		List<Map> listGun1 = new ArrayList<Map>();
		List<Map> listGun = (List<Map>) retMap.get("guns");
		if(listGun!= null && listGun.size() > 0){
			for (Map tmpMap : listGun) {
				String gun = (String) tmpMap.get("gun");
				String guned = (String) tmpMap.get("guned");
				Map tmpMap1 = new HashMap();
				tmpMap1.put("gun", twGame.getPlayerByAccount(gun).getPlayerOrder());
				tmpMap1.put("guned", twGame.getPlayerByAccount(guned)
						.getPlayerOrder());
				listGun1.add(tmpMap1);
			}
			retMap.put("guns", listGun1);
		}
		

		List<Map> listScore = new ArrayList<Map>();
		PlayerScores pss = twGame.getPlayerScores();
		for (int i = 0; i < twGame.getActivePlayers(); i++) {

			Player player = twGame.getPlayerByIndex(i);
			Map map = new HashMap();
			map.put("dir", player.getPlayerOrder());
			map.put("score", pss.getScore(player.getAccount()));
			listScore.add(map);
		}
		retMap.put("scores", listScore);
		return retMap;

	}

	public Map codeGameComplete(ThreeWaterPokerGame twGame) {
		Map mapRet = new HashMap();
		mapRet.put("code", "result");
		mapRet.put("cards", this.codeCards(twGame));
		mapRet.putAll(this.codeScores(twGame));
		return mapRet;
	}

	private List getShooted(Game game) {
		List<Map> list = new ArrayList<Map>();

		for (int i = 0; i < game.getActivePlayers(); i++) {
			ThreeWaterPokerPlayer twpp = (ThreeWaterPokerPlayer) game
					.getPlayerByIndex(i);
			boolean shooted = twpp.getThreeWaterPokerCards().isShooted();
			Map map = new HashMap();
			map.put("dir", twpp.getPlayerOrder());
			map.put("shooted", shooted);
			list.add(map);
		}
		return list;
	}

	protected Map codeGameStart(Room room, Action action) {
		Game game = room.currentGame();

		Map mapRet = room.currentGame().format();
		mapRet.put("code", "gameStart");
		mapRet.put("leftJu", room.getOrder());
		mapRet.put("shooted", this.getShooted(game));

		return mapRet;
	}

	@Override
	protected Map codeGameNotice(Room room) {

		Game game = room.currentGame();
		Map mapRet = room.format();
		mapRet.put("code", "gameNotice");
		mapRet.put("leftJu", room.getOrder());
		mapRet.putAll(game.format());
		List<Map> list = new ArrayList<Map>();
		mapRet.put("shooted", this.getShooted(game));

		return mapRet;
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
			mapRet = this.codeGameNotice(room);
			mapRet = this.responseFilter.filter(player, mapRet, room);
			this.clientPusher.push(account, mapRet);
		}
	}

	@Override
	public void listen(Game game, Action action) {
		
		
		
		Map mapRet = null;
		Room room = game.getRoom();
		int type = action.getType();
		ThreeWaterPokerGame twGame = (ThreeWaterPokerGame) game;
		ThreeWaterPokerPlayer twPlayer = (ThreeWaterPokerPlayer) action
				.getPlayer();

		if (type == ThreeWaterPokerAction.ALLOCATE) {
			mapRet = this.codeGameStart(game.getRoom(), action);
		} else if (type == ThreeWaterPokerAction.SHOOT) {
			mapRet = new HashMap();

			mapRet.put("code", "threeWaterShoot");
			mapRet.put("dir", twPlayer.getPlayerOrder());
			mapRet.put("legal", true);
			boolean legal = (Boolean) action.getAttribute("legal");
			if (!legal) {
				mapRet.put("legal", false);
				this.clientPusher.push(action.getPlayer().getAccount(), mapRet);
				return;
			}
		} else if (type == ThreeWaterPokerAction.COMPLETE) {
			mapRet = this.codeGameComplete((ThreeWaterPokerGame)game);
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

		
	}

}
