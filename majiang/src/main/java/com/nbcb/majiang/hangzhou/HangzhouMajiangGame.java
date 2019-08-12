package com.nbcb.majiang.hangzhou;

import java.util.Map;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.user.MajiangPlayer;

public class HangzhouMajiangGame extends MajiangGame {

	private MajiangPlayer baidaPlayer;
	private int baidaTimes;
	private int piaocaiGangTimes;

	public int getPiaocaiGangTimes() {
		return piaocaiGangTimes;
	}

	public void setPiaocaiGangTimes(int piaocaiGangTimes) {
		this.piaocaiGangTimes = piaocaiGangTimes;
	}

	private int laoShu;

	public HangzhouMajiangGame(MajiangGameInfo gameInfo) {
		super(gameInfo);
		// TODO Auto-generated constructor stub
	}

	public int getLaoShu() {
		return laoShu;
	}

	public void setLaoShu(int laoShu) {
		this.laoShu = laoShu;
	}

	public MajiangCard getBaida() {
		HangzhouMajiangGameInfo gf = (HangzhouMajiangGameInfo) this
				.getGameInfo();
		if (gf.isBaiBanBaida()) {
			return (MajiangCard) this.getAllCards().findCardByType("白");
		} else {
			return super.getBaida();
		}

	}

	public MajiangPlayer getBaidaPlayer() {
		return baidaPlayer;
	}

	public void setBaidaPlayer(MajiangPlayer baidaPlayer) {
		this.baidaPlayer = baidaPlayer;
	}

	public int getBaidaTimes() {
		return baidaTimes;
	}

	public void setBaidaTimes(int baidaTimes) {
		this.baidaTimes = baidaTimes;
	}

	@Override
	public Map<String, Object> format() {
		MajiangCards mcs = (MajiangCards) this.allCards;
		Map<String, Object> mapRet = super.format();
		mapRet.put("laoShu", this.laoShu);
		return mapRet;
	}

}
