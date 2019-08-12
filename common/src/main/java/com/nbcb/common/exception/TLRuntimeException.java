package com.nbcb.common.exception;

public class TLRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 12345670L;
	
	private String errCode;
	private String errMsg;

	
	public TLRuntimeException(String errCode, String errMsg){
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
