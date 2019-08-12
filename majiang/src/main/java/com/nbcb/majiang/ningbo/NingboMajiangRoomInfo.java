package com.nbcb.majiang.ningbo;

import com.nbcb.core.room.RoomInfo;

public class NingboMajiangRoomInfo extends RoomInfo {
	//是否胡碰清
	private boolean hunpengqing;
	//百搭个数
	private int baida;
	// 几台开胡
	private int startFan;
	
	//金华几台
	private int jinhua;
	
	//野花几台
	private int yehua;

	public int getStartFan() {
		return startFan;
	}

	public void setStartFan(int startFan) {
		this.startFan = startFan;
	}

	public boolean isHunpengqing() {
		return hunpengqing;
	}

	public void setHunpengqing(boolean hunpengqing) {
		this.hunpengqing = hunpengqing;
	}

	public int getBaida() {
		return baida;
	}

	public void setBaida(int baida) {
		this.baida = baida;
	}
	
	
	
	public int getJinhua() {
		return jinhua;
	}

	public void setJinhua(int jinhua) {
		this.jinhua = jinhua;
	}

	public int getYehua() {
		return yehua;
	}

	public void setYehua(int yehua) {
		this.yehua = yehua;
	}

	@Override
	public String toString(){
		return super.toString() + "hupengqing["+this.hunpengqing+"]"+"baida["+this.baida+"]"+"startFan["+this.startFan+"]jinhua["+this.jinhua+"]yehua["+this.yehua+"]";
	}

}
