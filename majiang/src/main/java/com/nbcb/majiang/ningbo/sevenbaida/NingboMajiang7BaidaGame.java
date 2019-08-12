package com.nbcb.majiang.ningbo.sevenbaida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nbcb.core.game.GameInfo;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.ningbo.NingboMajiangGame;

public class NingboMajiang7BaidaGame extends NingboMajiangGame {

	private Map<String, Boolean> mapGenPlayer = new HashMap<String, Boolean>();

	private boolean isBao = false;

	public boolean isBao() {
		return isBao;
	}

	public void setBao(boolean isBao) {
		this.isBao = isBao;
	}

	public Object getGenByAccount(String account) {
		return mapGenPlayer.get(account);
	}

	public void putGen(String account, boolean isGen) {
		mapGenPlayer.put(account, isGen);
	}

	public int genSize() {
		return mapGenPlayer.keySet().size();
	}

	@Override
	public Player nextPlayer(Player player) {
		// TODO Auto-generated method stub
		if (!this.isBao) {
			return super.nextPlayer(player);
		}
		Player nextPlayer = player;
		while (true) {
			nextPlayer = super.nextPlayer(nextPlayer);
			if (Boolean.TRUE.equals(this.mapGenPlayer.get(nextPlayer
					.getAccount()))) {
				return nextPlayer;
			}
			if (nextPlayer == player) {
				break;
			}
		}
		return null;
	}

	public NingboMajiang7BaidaGame(GameInfo gameInfo) {
		super(gameInfo);
	}

	private List<Map> formatGen() {
		List<Map> listRet = new ArrayList<Map>();
		Set<String> keySet = this.mapGenPlayer.keySet();
		for (String key : keySet) {
			Map tmpMap = new HashMap();
			tmpMap.put("dir", this.getPlayerByAccount(key).getPlayerOrder());
			tmpMap.put("gen", this.mapGenPlayer.get(key));
			listRet.add(tmpMap);
		}
		return listRet;
	}

	@Override
	public Map<String, Object> format() {
		Map mapRet = super.format();
		mapRet.put("isBao", this.isBao);
		if (this.isBao) {
			mapRet.put("genlist", this.formatGen());
		}
		return mapRet;
	}

	public String toString() {
		String str = "--------------------start---------------------\r\n";
		str += "### gameInfo:" + getGameInfo();
		str += "\r\n";
		str += "### gen: isBao [" + this.isBao + "] genPlayer ["
				+ this.mapGenPlayer + "]";
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

}
