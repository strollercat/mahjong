package com.nbcb.majiang.tiantai;

import com.nbcb.majiang.game.MajiangGameInfo;

public class TiantaiMajiangGameInfo extends MajiangGameInfo {

	private boolean hasBaida;

	private int maxFan;

	public boolean isHasBaida() {
		return hasBaida;
	}

	public void setHasBaida(boolean hasBaida) {
		this.hasBaida = hasBaida;
	}

	public int getMaxFan() {
		return maxFan;
	}

	public void setMaxFan(int maxFan) {
		this.maxFan = maxFan;
	}

}
