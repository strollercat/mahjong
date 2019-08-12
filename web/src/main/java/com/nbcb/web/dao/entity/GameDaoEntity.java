package com.nbcb.web.dao.entity;

import java.util.Date;

public class GameDaoEntity {
	private int id;
	private Date create_time;
	private Date end_time;
	private String users;
	private String game_info;
	private int roomid;

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getGame_info() {
		return game_info;
	}

	public void setGame_info(String game_info) {
		this.game_info = game_info;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	
	@Override
	public String toString(){
		return this.id+" "+this.create_time+" "+this.end_time+" "+this.users+" "+this.game_info+" "+this.roomid;
	}

}
