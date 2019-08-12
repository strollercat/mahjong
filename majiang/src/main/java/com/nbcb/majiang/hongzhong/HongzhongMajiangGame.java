package com.nbcb.majiang.hongzhong;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.core.game.GameInfo;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.game.MajiangGame;

public class HongzhongMajiangGame extends MajiangGame {

	private Map<String, Object> mapDaHongzhong = new HashMap<String,Object>();

	public void putDaHongzhong(String account) {
		this.mapDaHongzhong.put(account, new Object());
	}

	public boolean daHongzhong(String account) {
		return this.mapDaHongzhong.get(account) != null;
	}

	public HongzhongMajiangGame(GameInfo gameInfo) {
		super(gameInfo);
	}

	@Override
	public MajiangCard getBaida() {
		return (MajiangCard) this.getAllCards().findCardByType("中");
	}

}
