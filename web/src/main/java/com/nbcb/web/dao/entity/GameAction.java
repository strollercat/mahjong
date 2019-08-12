package com.nbcb.web.dao.entity;

import java.util.Date;

public class GameAction {
	private int id;
	private String account;
	private int action;
	private String cards;
	private int useraction;
	private String attribute;
	private int actionindex;
	private int gameid;
	private Date action_time;

	public int getActionindex() {
		return actionindex;
	}

	public void setActionindex(int actionindex) {
		this.actionindex = actionindex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	public int getUseraction() {
		return useraction;
	}

	public void setUseraction(int useraction) {
		this.useraction = useraction;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public Date getAction_time() {
		return action_time;
	}

	public void setAction_time(Date action_time) {
		this.action_time = action_time;
	}

}
