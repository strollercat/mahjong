package com.nbcb.web.dao.entity;

import java.util.Date;

public class RoomDaoEntity {
	private int id;
	private String roomid;
	private String create_account;
	private Date create_time;
	private String room_info;
	private String recommend;
	private String name;
	private int total_ju;
	private int player_num;
	private Date end_time;
	private String users;
	private String end_reason;
	private int time_range;
	private int end_ju;
	private int cost_money = 0;

	public int getCost_money() {
		return cost_money;
	}

	public void setCost_money(int cost_money) {
		this.cost_money = cost_money;
	}

	public int getEnd_ju() {
		return end_ju;
	}

	public void setEnd_ju(int end_ju) {
		this.end_ju = end_ju;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal_ju() {
		return total_ju;
	}

	public void setTotal_ju(int total_ju) {
		this.total_ju = total_ju;
	}

	public int getPlayer_num() {
		return player_num;
	}

	public void setPlayer_num(int player_num) {
		this.player_num = player_num;
	}

	public String getEnd_reason() {
		return end_reason;
	}

	public void setEnd_reason(String end_reason) {
		this.end_reason = end_reason;
	}

	public int getTime_range() {
		return time_range;
	}

	public void setTime_range(int time_range) {
		this.time_range = time_range;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getCreate_account() {
		return create_account;
	}

	public void setCreate_account(String create_account) {
		this.create_account = create_account;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getRoom_info() {
		return room_info;
	}

	public void setRoom_info(String room_info) {
		this.room_info = room_info;
	}

	@Override
	public String toString() {
		return this.id + " " + this.roomid + " " + this.create_account + " "
				+ this.create_time + " " + this.end_time + " " + this.users
				+ " " + this.room_info + " " + this.recommend;
	}
}
