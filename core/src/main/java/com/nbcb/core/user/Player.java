package com.nbcb.core.user;


public interface Player extends User {

	/**
	 * 准备
	 */
	public void ready();
	
	public void unReady();

	public boolean isReady();
	
	/**
	 * 获取座位号
	 * @return
	 */
	public int getPlayerOrder();
	
	public void setPlayerOrder(int order);
	
	public String getOriginIp();
	
	public void setOriginIp(String ip);
	
	public void setLocation(Location location);
	
	public Location getLocation();

}
