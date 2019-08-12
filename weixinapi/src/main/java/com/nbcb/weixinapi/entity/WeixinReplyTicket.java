package com.nbcb.weixinapi.entity;

public class WeixinReplyTicket extends WeixinReplyCommon {
	private String ticket;
	private int expires_in;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	} 
}
