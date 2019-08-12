package com.nbcb.web.service;

public interface GameUserIdService {

	public String encode(int id);

	public int decode(String encodeStr);

}
