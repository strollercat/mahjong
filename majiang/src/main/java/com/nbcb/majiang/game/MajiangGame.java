package com.nbcb.majiang.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.game.DefaultGame;
import com.nbcb.core.game.GameInfo;
import com.nbcb.core.game.GameResult;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangGame extends DefaultGame {

	private static final Logger logger = LoggerFactory
			.getLogger(MajiangGame.class);

	protected MajiangCard baida;

	private MajiangHuResults mjHuResults = new MajiangHuResults();

	private MajiangBlackCards majiangBlackCards; // 未摸的牌

	private Map<String, Object> mapManaged = new ConcurrentHashMap<String, Object>();

	private Map<String, Long> mapLastWaitUserActionTime = new ConcurrentHashMap<String, Long>();

	public void putLastWaitUserActionTime(String account, Long time) {
		this.mapLastWaitUserActionTime.put(account, time);
	}

	public long getLastWaitUserActionTimeByAccount(String account) {
		return this.mapLastWaitUserActionTime.get(account);
	}

	public boolean isPlayerManagerd(String account) {
		return mapManaged.get(account) != null;
	}

	public void putPlayerManage(String account, boolean managed) {
		if (managed) {
			this.mapManaged.put(account, new Object());
		} else {
			this.mapManaged.remove(account);
		}
	}

	public void addMajiangHuResult(MajiangHuResult mjHuResult) {
		mjHuResults.addMajiangHuResult(mjHuResult);
	}

	public MajiangHuResults getMajiangHuResults() {
		return this.mjHuResults;
	}

	public MajiangCard getBaida() {
		return baida;
	}

	public void setBaida(MajiangCard baida) {
		this.baida = baida;
	}

	public MajiangGame(GameInfo gameInfo) {
		super(gameInfo);

		// /**
		// * 初始化 autoda的初始等待时间为无限大,这样就不会一开始就超时了
		// */
		// for (int i = 0; i < this.getActivePlayers(); i++) {
		// this.putLastWaitUserActionTime(this.getPlayerByIndex(i)
		// .getAccount(), System.currentTimeMillis() + 2 * 60 * 60
		// * 1000);
		// }

	}

	public MajiangBlackCards getMajiangBlackCards() {
		return majiangBlackCards;
	}

	public void start() {
		super.start();
		majiangBlackCards = new MajiangBlackCards(
				(MajiangAllCards) this.allCards);
		for (int i = 0; i < this.players.getActivePlayers(); i++) {
			MajiangPlayer mp = (MajiangPlayer) this.players.getPlayerByIndex(i);
			mp.getMajiangInnerCards().clear();
			mp.getMajiangMiddleCards().clear();
			mp.getMajiangOutterCards().clear();
		}
		MajiangAction mjAction = new MajiangAction(null,
				MajiangAction.ALLOCATE, null, false);
		rule.next(this, mjAction);
	}

	public MajiangPlayer getDealer() {
		// TODO Auto-generated method stub
		MajiangGameInfo mjGameInfo = (MajiangGameInfo) this.gameInfo;
		return (MajiangPlayer) players.getPlayerByOrder(mjGameInfo.getZuan());
	}

	@Override
	public String toString() {
		String str = "--------------------start---------------------\r\n";
		str += "### gameInfo:" + getGameInfo() + " roomIdForQuery["
				+ this.getRoom().getId() + "]";
		str += "\r\n";
		str += "### playerScores:" + this.playerScores;
		str += "\r\n";
		str += "### blackCards:" + this.getMajiangBlackCards();
		str += "\r\n";
		str += "### baida:" + this.getBaida();
		str += "\r\n";
		str += players;
		str += "---------------------end----------------------\r\n";
		return str;
	}

	public void stop() {
		GameResult gmResult = new GameResult(mjHuResults, this.playerScores);
		this.getRoom().gameStop(this);
	}

	protected List<Map<String, Object>> formatCards() {
		List<Map<String, Object>> cards = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < this.players.getActivePlayers(); i++) {
			MajiangPlayer player = (MajiangPlayer) this.players
					.getPlayerByIndex(i);
			String account = player.getAccount();
			Map<String, Object> card = new HashMap<String, Object>();
			card.put("dir", player.getPlayerOrder());
			card.put("i", player.getMajiangInnerCards().toNumberArray());
			card.put("m", player.getMajiangMiddleCards().format().get("m"));
			card.put("o", player.getMajiangOutterCards().toNumberArray());
			cards.add(card);
		}
		return cards;
	}

	protected int getZuan() {
		MajiangGameInfo mjGameInfo = (MajiangGameInfo) this.gameInfo;
		return mjGameInfo.getZuan();
	}

	protected int getQuan() {
		MajiangGameInfo mjGameInfo = (MajiangGameInfo) this.gameInfo;
		return mjGameInfo.getQuan();
	}

	@Override
	public Map<String, Object> format() {
		MajiangCards mcs = (MajiangCards) this.allCards;
		Map<String, Object> mapRet = new HashMap<String, Object>();
		mapRet.put("baidas", mcs.baidaCards().toNumberArray());
		mapRet.put("leftCard", this.majiangBlackCards.size());
		mapRet.put("whiteCard", this.getBaida() == null?null:this.getBaida().getNumber());
		mapRet.put("east", this.getZuan());
		mapRet.put("quan", this.getQuan());
		mapRet.put("cards", this.formatCards());
		return mapRet;
	}
}
