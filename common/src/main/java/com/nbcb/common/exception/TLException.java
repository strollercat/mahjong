package com.nbcb.common.exception;

public class TLException extends Exception {
	
	private static final long serialVersionUID = 12345679L;
	
	private String errCode;
	private String errMsg;

	public TLException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

}
