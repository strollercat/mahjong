package com.nbcb.core.io;

import com.nbcb.core.room.Room;

public interface MessageListener {

	/**
	 * 给单个用户推送消息
	 * @param account
	 * @param message
	 */
	public void listen(String account, String message);
	/**
	 * 给房间内所有用户推送消息
	 * @param room
	 * @param message
	 */
	public void listen(Room room, String message);

	public String getPlaceHolderAccount(String account);

	/**
	 * 
	 * @param account 待推送的用户
	 * @param message
	 * @return
	 */
	public String translateMessage(String account,String message);
}
