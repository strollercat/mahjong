package com.nbcb.majiang.ningbo;

import com.nbcb.majiang.game.MajiangGameInfo;

public class NingboMajiangGameInfo extends MajiangGameInfo {

	// 白搭个数
	private int baida;

	// 几台开胡
	private int startFan;

	// 是否对对胡清一色混一色才可以胡
	private boolean hupengqing;

	// 金花台数
	private int jinHua;

	// 野花台数
	private int yeHua;

	public int getJinHua() {
		return jinHua;
	}

	public void setJinHua(int jinHua) {
		this.jinHua = jinHua;
	}

	public int getYeHua() {
		return yeHua;
	}

	public void setBaida(int baida) {
		this.baida = baida;
	}

	public void setStartFan(int startFan) {
		this.startFan = startFan;
	}

	public void setHupengqing(boolean hupengqing) {
		this.hupengqing = hupengqing;
	}

	public void setYeHua(int yeHua) {
		this.yeHua = yeHua;
	}

	public int getBaida() {
		return baida;
	}

	public int getStartFan() {
		return startFan;
	}

	public boolean isHupengqing() {
		return hupengqing;
	}

	@Override
	public String toString() {
		return "jinhua[" + this.jinHua + "]yehua[" + this.yeHua + "]baida["
				+ this.baida + "] startFan[" + this.startFan + "] hupengqing["
				+ this.hupengqing + "]" + super.toString();
	}
}
