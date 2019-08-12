package com.nbcb.web.dao.entity;

public class GameUser {
	private int id;
	private String openid;
	private int win;
	private int lose;
	private int ping;
	private int score;
	private int money;
	private boolean recommend;
	private int rec_room;
	private int rec_money;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public int getRec_room() {
		return rec_room;
	}

	public void setRec_room(int rec_room) {
		this.rec_room = rec_room;
	}

	public int getRec_money() {
		return rec_money;
	}

	public void setRec_money(int rec_money) {
		this.rec_money = rec_money;
	}

}
