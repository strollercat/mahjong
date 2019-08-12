package com.nbcb.web.service.impl;

import com.nbcb.web.service.GameUserIdService;

public class DefaultGameUserIdService implements GameUserIdService {

	private String get0Str(int size) {
		String str = "";
		for (int i = 0; i < size; i++) {
			str += "0";
		}
		return str;
	}

	@Override
	public String encode(int id) {
		// TODO Auto-generated method stub
		String str = String.valueOf(id);
		if (str.length() < 6) {
			str = this.get0Str(6 - str.length()) + str;
		}
		return str;
	}

	@Override
	public int decode(String encodeStr) {
		// TODO Auto-generated method stub
		return Integer.parseInt(encodeStr);
	}

}
