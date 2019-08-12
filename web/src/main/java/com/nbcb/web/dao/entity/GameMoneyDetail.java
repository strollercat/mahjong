package com.nbcb.web.dao.entity;

import java.util.Date;

public class GameMoneyDetail {
	private int id;
	private String openid;
	private int money;
	private String action;
	private String remark;
	private String relate;
	private Date time;

	public static final String PLAYGAMEACTION = "1";
	public static final String ONLINEBUYACTION = "2";
	public static final String AGENTBUYACTION = "3";
	public static final String AGENTSELLACTION = "4";
	public static final String SYSTEMSENDACTION = "5";

	public static final String PLAYGAMEREMARK = "玩游戏一场";
	public static final String ONLINEBUYREMARK = "在线购买";
	public static final String AGENTBUYREMARK = "代理购买";
	public static final String AGENTSELLREMARK = "代理出售";
	public static final String SYSTEMSENDREMARK = "系统赠送";

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRelate() {
		return relate;
	}

	public void setRelate(String relate) {
		this.relate = relate;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
