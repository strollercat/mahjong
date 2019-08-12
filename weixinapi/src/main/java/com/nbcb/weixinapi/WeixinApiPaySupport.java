package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinSendPayParent;
import com.nbcb.weixinapi.exception.WeixinException;

public interface WeixinApiPaySupport {

	public <OUT> OUT nonCertExec(WeixinSendPayParent weixinSendPayParent,
			String uri, String name, Class<OUT> out) throws WeixinException;

}
