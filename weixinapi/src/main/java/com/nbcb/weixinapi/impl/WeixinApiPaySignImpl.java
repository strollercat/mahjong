package com.nbcb.weixinapi.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.nbcb.common.util.Md5Util;
import com.nbcb.weixinapi.WeixinApiPaySign;
import com.nbcb.weixinapi.WeixinCachedApiBase;

public class WeixinApiPaySignImpl implements WeixinApiPaySign {

	private WeixinCachedApiBase weixinCachedApiBase;

	public void setWeixinCachedApiBase(WeixinCachedApiBase weixinCachedApiBase) {
		this.weixinCachedApiBase = weixinCachedApiBase;
	}

	private Map<String, Object> removeEmptyKey(Map<String, Object> map) {
		Map<String, Object> mapRet = new HashMap();
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			if (StringUtils.hasText(value)) {
				mapRet.put(key, value);
			}
		}
		return mapRet;
	}

	private String getOrderString(Map<String, Object> map, String appid) {
		map = this.removeEmptyKey(map);
		StringBuffer result = new StringBuffer();
		String strs[] = new String[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			strs[i++] = key;
		}
		Arrays.sort(strs);
		for (i = 0; i < strs.length; i++) {
			if (i == 0) {
				result.append(strs[i]).append("=").append(map.get(strs[i]));
				continue;
			}
			result.append("&").append(strs[i]).append("=")
					.append(map.get(strs[i]));
		}
		String paySecret = weixinCachedApiBase.getWeixinAccountByAppid(appid)
				.getPay_secret();
//		String paySecret = "jkdfjikmnf9837458jfhvundjew91234";
		result.append("&key=").append(paySecret);
		return result.toString();
	}

	@Override
	public String getSign(Map<String, Object> map, String appid) {
		// TODO Auto-generated method stub
		String orderString = this.getOrderString(map, appid);
		if (orderString == null) {
			return null;
		}
		return Md5Util.MD5(orderString).toUpperCase();
	}

	@Override
	public boolean validateSign(Map<String, Object> map, String appid,
			String sign) {
		// TODO Auto-generated method stub
		String calSign = this.getSign(map, appid);
		if (sign.equals(calSign)) {
			return true;
		}
		return false;
	}

}
