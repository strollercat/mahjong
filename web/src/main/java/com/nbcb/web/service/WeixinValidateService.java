package com.nbcb.web.service;

public interface WeixinValidateService {
	
	public boolean validate(String token,String signature,String timestamp,String nonce);
	
	
}
