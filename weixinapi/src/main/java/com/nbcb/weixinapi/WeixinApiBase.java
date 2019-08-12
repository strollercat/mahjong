package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyAccessToken;
import com.nbcb.weixinapi.entity.WeixinReplyTicket;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 从腾讯获取token以及ticket
 * 注意，不要直接调用这两个函数
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiBase {

	public WeixinReplyAccessToken getAccessToken(String appid,
			String secret)throws WeixinException;
	
	public WeixinReplyTicket getTicket(String accessToken)throws WeixinException;
}
