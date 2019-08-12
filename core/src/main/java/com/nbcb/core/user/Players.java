package com.nbcb.core.user;

public interface Players {

	/**
	 * 获取总的player的人数
	 * @return
	 */
	public int getActivePlayers();
	/**
	 * 
	 * 根据数组索引获取Player
	 * @param i
	 * @return
	 */
	public Player getPlayerByIndex(int i);

	public Player addPlayer(Player player);
	
	public Player leavePlayer(String account);
	
	public Player getPlayerByAccount(String account);
	/**
	 * 根据座位号选取Player
	 * @param order
	 * @return
	 */
	public Player getPlayerByOrder(int order);
	/**
	 * 按照按时针座位号的顺序获取下一个player
	 * @param player
	 * @return
	 */
	public Player nextPlayer(Player player);
	
	public String [] playerAccounts();
	
	/**
	 * 把座位号和索引号保持一致
	 */
	public void ajustPlayerIndex();
	
}
