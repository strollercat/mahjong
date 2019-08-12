package com.nbcb.majiang.game;

import com.nbcb.core.game.GameInfo;

public class MajiangGameInfo extends GameInfo {

	// 什么圈
	private int quan;

	protected int zuan;

	public int getZuan() {
		return zuan;
	}

	public int getQuan() {
		return quan;
	}

	public void setQuan(int quan) {
		this.quan = quan;
	}

	public void setZuan(int zuan) {
		this.zuan = zuan;
	}

	@Override
	public String toString() {
		return "quan[" + this.quan + "]zuan[" + this.zuan + "] "
				+ super.toString();
	}

}
