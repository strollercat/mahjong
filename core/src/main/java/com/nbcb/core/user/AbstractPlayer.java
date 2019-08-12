package com.nbcb.core.user;

public abstract class AbstractPlayer extends AbstractUser implements Player {

	public AbstractPlayer(String account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	private boolean isReady = false;

	private int order;

	private String originIp = null;
	
	private Location location = null;

	public void ready() {
		isReady = true;
	}

	public boolean isReady() {
		return this.isReady;
	}

	public int getPlayerOrder() {
		return this.order;
	}

	public String toString() {
		return "player[" + this.getAccount() + "]" + " isReady[" + this.isReady
				+ "] ";
	}

	public void setPlayerOrder(int order) {
		this.order = order;
	}

	public void unReady() {
		this.isReady = false;
	}

	public String getOriginIp() {
		return this.originIp;
	}

	public void setOriginIp(String ip) {
		this.originIp = ip;
	}
	
	@Override
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Location getLocation(){
		return this.location;
	}

}
