package com.nbcb.weixinapi.exception;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinException extends Exception {

	private int errcode;
	private String errmsg;

	public WeixinException(int errcode, String errmsg) {
		super("[errcode] " + errcode + " [errmsg] " + errmsg);
		this.errcode = errcode;
		this.errmsg = errmsg;
	}
	public WeixinException(int errcode,String errmsg,Exception e){
		super(e);
		this.errcode = errcode;
		this.errmsg = errmsg;
	}
	public int getErrcode() {
		return errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
}
