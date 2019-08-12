package com.nbcb.weixinapi;

import java.util.Map;

public interface WeixinApiPaySign {

	public String getSign(Map<String, Object> map/** 里面不能有sign **/
	, String appid);

	public boolean validateSign(Map<String, Object> map, String appid,
			String sign);
}
