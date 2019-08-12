package com.nbcb.weixinapi.entity;

import java.util.Date;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinAccount {
	private int id;

	private String account;

	private String appid;

	private String secret;

	private String name;

	private String account_type;

	private volatile String access_token; // 保证一个线程修改了变量对另外一个线程立即可见

	private volatile Date token_expire_time;

	private volatile String ticket;

	private volatile Date ticket_expire_time;

	private String asyn_flag;

	private String description;

	private String keyinfo;

	private String token;

	private String xiaoi_channel;

	private String mach_id;

	private String pay_secret;
	
	private String mach_name;
	
	
	

	public String getMach_name() {
		return mach_name;
	}

	public void setMach_name(String mach_name) {
		this.mach_name = mach_name;
	}

	public String getMach_id() {
		return mach_id;
	}

	public void setMach_id(String mach_id) {
		this.mach_id = mach_id;
	}

	public String getPay_secret() {
		return pay_secret;
	}

	public void setPay_secret(String pay_secret) {
		this.pay_secret = pay_secret;
	}

	public String getXiaoi_channel() {
		return xiaoi_channel;
	}

	public void setXiaoi_channel(String xiaoi_channel) {
		this.xiaoi_channel = xiaoi_channel;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKeyinfo() {
		return keyinfo;
	}

	public void setKeyinfo(String keyinfo) {
		this.keyinfo = keyinfo;
	}

	private String remark;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Date getToken_expire_time() {
		return token_expire_time;
	}

	public void setToken_expire_time(Date token_expire_time) {
		this.token_expire_time = token_expire_time;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getTicket_expire_time() {
		return ticket_expire_time;
	}

	public void setTicket_expire_time(Date ticket_expire_time) {
		this.ticket_expire_time = ticket_expire_time;
	}

	public String getAsyn_flag() {
		return asyn_flag;
	}

	public void setAsyn_flag(String asyn_flag) {
		this.asyn_flag = asyn_flag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return id + " " + keyinfo + " " + token + " " + account + " " + appid
				+ " " + secret + " " + name + " " + account_type + " "
				+ access_token + " " + token_expire_time + " " + ticket + " "
				+ ticket_expire_time + " " + asyn_flag;
	}
}
