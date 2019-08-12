package com.nbcb.core.room;

import java.util.List;

import com.nbcb.core.game.Game;
import com.nbcb.core.server.Channel;
import com.nbcb.core.server.Formatter;
import com.nbcb.core.user.Player;
import com.nbcb.core.user.Players;

public interface Room extends Players, Formatter {

	/**
	 * 
	 * @param account
	 * @param dismiss
	 * @return dismiss的人数
	 */
	public int requestDismiss(String account, boolean dismiss);

	/**
	 * 获取所有的dismissPlayer
	 * 
	 * @param room
	 * @return
	 */
	public List<String> getDismissPlayers();

	/**
	 * 数据库主键ID
	 * 
	 * @return
	 */
	public int getUniqueId();

	public void setUniqueId(int uniqueId);

	/**
	 * 房间ID
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 创建者
	 * 
	 * @return
	 */
	public String getCreatePlayer();

	/**
	 * 获取房间销毁方式
	 * 
	 * @return
	 */
	public String getEndReason();

	/**
	 * 设置房间销毁方式
	 * 
	 * @param endReason
	 */
	public void setEndReason(String endReason);

	public void setCreatePlayer(String account);

	/**
	 * 房间创建时间
	 * 
	 * @param time
	 */
	public void setCreateTime(long time);

	public long getCreateTime();

	public Player addPlayer(String account);

	public void setChannel(Channel channel);

	public Channel getChannel();

	public Game currentGame();

	public Player playerReady(String account);

	public Player playerUnReady(String account);

	/**
	 * 当前房间玩到第几局游戏
	 * 
	 * @return
	 */
	public int getOrder();

	public boolean isGaming();

	public RoomInfo getRoomInfo();

	public void gameStart();

	public void gameStop(Game game);

	public int getPlayerScoreByAccount(String account);

	public boolean allPlayerReady();

}
