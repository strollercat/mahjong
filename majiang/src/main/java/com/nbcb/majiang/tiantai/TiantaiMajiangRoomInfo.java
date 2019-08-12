package com.nbcb.majiang.tiantai;

import com.nbcb.core.room.RoomInfo;

public class TiantaiMajiangRoomInfo extends RoomInfo {

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
