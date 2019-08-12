package com.nbcb.majiang.fenhua;

import com.nbcb.core.room.RoomInfo;

public class FenhuaMajiangRoomInfo extends RoomInfo {

	// 平胡或者冲刺
	private boolean pinghu;
	
	// 冲刺的钱
	private int chongciMoney;

	public boolean isPinghu() {
		return pinghu;
	}

	public void setPinghu(boolean pinghu) {
		this.pinghu = pinghu;
	}

	public int getChongciMoney() {
		return chongciMoney;
	}

	public void setChongciMoney(int chongciMoney) {
		this.chongciMoney = chongciMoney;
	}
	
	

}
