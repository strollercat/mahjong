package com.nbcb.weixinapi.entity;


/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinReplyCommon {
	
	private int errcode = 0;

	private String errmsg;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
