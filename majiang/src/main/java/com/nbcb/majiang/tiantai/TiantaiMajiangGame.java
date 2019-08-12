package com.nbcb.majiang.tiantai;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.game.MajiangGame;

public class TiantaiMajiangGame extends MajiangGame {

	public TiantaiMajiangGame(TiantaiMajiangGameInfo gameInfo) {
		super(gameInfo);
		// TODO Auto-generated constructor stub
	}

	public MajiangCard getBaida() {
		return (MajiangCard) this.getAllCards().findCardByType("白");
	}
}
