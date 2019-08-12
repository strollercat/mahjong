package com.nbcb.web.dao.entity;

public class GameWinLose {
	private int id;
	private String openid;
	private String winlose;
	private int ratio;

	private int score;
	private String nickname;
	private String headimgurl;

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getWinlose() {
		return winlose;
	}

	public void setWinlose(String winlose) {
		this.winlose = winlose;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return this.id + " " + this.openid + " " + this.winlose + " "
				+ this.ratio + " " + this.score + " " + this.nickname;
	}
}
