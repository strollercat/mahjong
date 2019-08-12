package com.nbcb.weixinapi.impl;

import com.nbcb.weixinapi.WeixinApiBase;
import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.entity.WeixinReplyAccessToken;
import com.nbcb.weixinapi.entity.WeixinReplyTicket;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinApiBaseImpl implements WeixinApiBase {

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	@Override
	public WeixinReplyAccessToken getAccessToken(String appid, String secret)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appid + "&secret=" + secret;
		return weixinApiSupport.postWeixin(url, null,
				WeixinReplyAccessToken.class);

	}

	@Override
	public WeixinReplyTicket getTicket(String accessToken)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ accessToken + "&type=jsapi";
		return weixinApiSupport.postWeixin(url, null, WeixinReplyTicket.class);
	}
}
