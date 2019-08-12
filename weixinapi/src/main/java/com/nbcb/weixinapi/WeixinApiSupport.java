package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiSupport {

	public <IN, OUT extends WeixinReplyCommon> OUT postWeixin(String uri, IN in,
			Class<OUT> clz)throws WeixinException;
	



}
