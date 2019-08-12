package com.nbcb.majiang.hangzhou;

import com.nbcb.majiang.game.MajiangGameInfo;

public class HangzhouMajiangGameInfo extends MajiangGameInfo {
	private boolean baiBanBaida; // 白板白搭 翻财神
	private boolean sanLao; // 三老 平庄
	private boolean sanTan; // 不可吃三滩 三滩承包
	private boolean zimoHu; // 自摸胡 庄闲放冲
	private boolean baidaiKaoxiang; // 有财神必烤响
	private boolean pengTan; // 碰算滩
	private boolean baidaHaoqi; // baida可以算豪七

	public boolean isBaiBanBaida() {
		return baiBanBaida;
	}

	public void setBaiBanBaida(boolean baiBanBaida) {
		this.baiBanBaida = baiBanBaida;
	}

	public boolean isSanLao() {
		return sanLao;
	}

	public void setSanLao(boolean sanLao) {
		this.sanLao = sanLao;
	}

	public boolean isSanTan() {
		return sanTan;
	}

	public void setSanTan(boolean sanTan) {
		this.sanTan = sanTan;
	}

	public boolean isZimoHu() {
		return zimoHu;
	}

	public void setZimoHu(boolean zimoHu) {
		this.zimoHu = zimoHu;
	}

	public boolean isBaidaiKaoxiang() {
		return baidaiKaoxiang;
	}

	public void setBaidaiKaoxiang(boolean baidaiKaoxiang) {
		this.baidaiKaoxiang = baidaiKaoxiang;
	}

	public boolean isPengTan() {
		return pengTan;
	}

	public void setPengTan(boolean pengTan) {
		this.pengTan = pengTan;
	}

	public boolean isBaidaHaoqi() {
		return baidaHaoqi;
	}

	public void setBaidaHaoqi(boolean baidaHaoqi) {
		this.baidaHaoqi = baidaHaoqi;
	}

}
