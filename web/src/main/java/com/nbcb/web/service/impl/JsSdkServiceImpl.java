package com.nbcb.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.common.util.CryptoUtil;
import com.nbcb.common.util.RandomUtil;
import com.nbcb.web.service.JsSdkService;
import com.nbcb.weixinapi.WeixinCachedApiBase;

public class JsSdkServiceImpl implements JsSdkService {

	private static final Logger logger = LoggerFactory.getLogger(JsSdkServiceImpl.class);

	private String appid;

	private WeixinCachedApiBase weixinCachedApiBase;

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setWeixinCachedApiBase(WeixinCachedApiBase weixinCachedApiBase) {
		this.weixinCachedApiBase = weixinCachedApiBase;
	}

	@Override
	public Response getJsSdk(String url) {
		// TODO Auto-generated method stub
		try {
			String ticket = weixinCachedApiBase.getCachedTicket(appid);
			String nonceStr = RandomUtil.getRandomString(16);
			String timeStamp = System.currentTimeMillis() / 1000 + "";
			String onSignString = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp
					+ "&url=" + url;
			String sign = CryptoUtil.sha1(onSignString.getBytes());
//			logger.info("### ticket nonceStr timeStamp url onSignString sign " + ticket + " " + nonceStr + " "
//					+ timeStamp + " " + url + " " + onSignString + " " + sign);
			Map body = new HashMap();
			body.put("appId", appid);
			body.put("timestamp", timeStamp);
			body.put("nonceStr", nonceStr);
			body.put("signature", sign);
			return ResponseFactory.newResponse("000000", "成功", body);
		} catch (Exception e) {
			logger.error("### 获取jssdk配置信息失败!!!");
			return ResponseFactory.newResponse("000001", "获取jssdk配置信息失败!!!");
		}

	}

}
