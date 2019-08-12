package com.nbcb.web.service.impl;

import java.util.Map;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.core.io.UserOutputApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomAction;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.Player;

public class RouterUserOutputApi implements UserOutputApi{
	
	private UserOutputApi majiangUserOutputApi;
	
	private Map<String ,UserOutputApi> mapApi;
	
	private Server server;
	
	
	
	
	
	
	
	public void setServer(Server server) {
		this.server = server;
	}

	public void setMajiangUserOutputApi(UserOutputApi majiangUserOutputApi) {
		this.majiangUserOutputApi = majiangUserOutputApi;
	}

	public void setMapApi(Map<String, UserOutputApi> mapApi) {
		this.mapApi = mapApi;
	}

	@Override
	public void listen(Player player, Room room, RoomAction roomAction) {
		if(room == null){
			this.majiangUserOutputApi.listen(player, room, roomAction);
			return ;
		}
		UserOutputApi uoa = this.mapApi.get(room.getRoomInfo().getName());
		if(uoa == null){
			uoa = this.majiangUserOutputApi;
		}
		uoa.listen(player, room, roomAction);
		
	}

	@Override
	public void listen(Game game, Action action) {
		Room room = game.getRoom();
		
		if(room == null){
			this.majiangUserOutputApi.listen(game, action);
			return ;
		}
		UserOutputApi uoa = this.mapApi.get(room.getRoomInfo().getName());
		if(uoa == null){
			uoa = this.majiangUserOutputApi;
		}
		uoa.listen(game, action);
	}

	@Override
	public void connect(String account) {
		Room room = server.getRoomByAccount(account);
		if(room  == null ){
			this.majiangUserOutputApi.connect(account);
			return ;
		}
		UserOutputApi uoa = this.mapApi.get(room.getRoomInfo().getName());
		if(uoa == null){
			uoa = this.majiangUserOutputApi;
		}
		uoa.connect(account);
	}

	@Override
	public void disconnect(String account) {
		Room room = server.getRoomByAccount(account);
		if(room  == null){
			this.majiangUserOutputApi.disconnect(account);
			return ;
		}
		UserOutputApi uoa = this.mapApi.get(room.getRoomInfo().getName());
		if(uoa == null){
			uoa = this.majiangUserOutputApi;
		}
		uoa.disconnect(account);
		
	}

}
