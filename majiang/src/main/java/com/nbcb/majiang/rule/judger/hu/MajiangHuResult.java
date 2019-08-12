package com.nbcb.majiang.rule.judger.hu;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.common.helper.AbstractAttributeAccessor;
import com.nbcb.core.server.Formatter;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangHuResult extends AbstractAttributeAccessor implements
		Formatter, Comparable {

	public static final String ZHONGMANUMBER = "ZHONGMANUMBER";

	/**
	 * 胡的玩家
	 */
	private MajiangPlayer mjPlayer;

	/**
	 * 哪一种胡 mofronthu mobackhu qiangganghu fangqianghu
	 */
	private int huType;

	/**
	 * 如果是qiangganghu或者fangqianghu，指的是被胡的人
	 */
	private MajiangPlayer mjLosePlayer;

	private MajiangHuCards mjHuCards;

	/**
	 * 最终的番数
	 */
	private int fan;

	/**
	 * 最终番数的详细信息
	 */
	private String details;

	/**
	 * 是否抛搭的牌型
	 */
	private boolean paoda;

	public boolean isPaoda() {
		return paoda;
	}

	public void setPaoda(boolean paoda) {
		this.paoda = paoda;
	}

	public MajiangPlayer getMjPlayer() {
		return mjPlayer;
	}

	public void setMjPlayer(MajiangPlayer mjPlayer) {
		this.mjPlayer = mjPlayer;
	}

	public int getHuType() {
		return huType;
	}

	public void setHuType(int huType) {
		this.huType = huType;
	}

	public MajiangHuCards getMjHuCards() {
		return mjHuCards;
	}

	public void setMjHuCards(MajiangHuCards mjHuCards) {
		this.mjHuCards = mjHuCards;
	}

	public int getFan() {
		return fan;
	}

	public void setFan(int fan) {
		this.fan = fan;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public MajiangPlayer getMjLosePlayer() {
		return mjLosePlayer;
	}

	public void setMjLosePlayer(MajiangPlayer mjLosePlayer) {
		this.mjLosePlayer = mjLosePlayer;
	}

	@Override
	public String toString() {
		String loseAccount = (mjLosePlayer == null ? null : mjLosePlayer
				.getAccount());
		return mjPlayer.getAccount() + " " + loseAccount + " "
				+ MajiangAction.majiangActions[huType] + " " + this.fan + " "
				+ this.details + " " + "paoda[" + this.isPaoda() + "]"
				+ mjHuCards;
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("huAccount", mjPlayer == null ? null : mjPlayer.getAccount());
		map.put("huType", huType);
		map.put("loseAccount",
				mjLosePlayer == null ? null : mjLosePlayer.getAccount());
		map.put("fan", fan);
		map.put("details", details);
		map.put("mjHuCards",
				mjHuCards == null ? null : mjHuCards.toNumberArray());
		map.put("mjHuCard", mjHuCards == null ? null : mjHuCards.getMjHuCard()
				.getNumber());
		return map;

	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		MajiangHuResult other = (MajiangHuResult) arg0;
		if (this.getFan() < other.getFan()) {
			return 1;
		} else if (this.getFan() == other.getFan()) {
			return 0;
		} else {
			return -1;
		}
	}
}
