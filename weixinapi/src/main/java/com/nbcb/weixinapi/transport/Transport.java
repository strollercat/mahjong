package com.nbcb.weixinapi.transport;

import com.nbcb.weixinapi.exception.WeixinException;

public interface Transport {

	public Object submit(Object object) throws WeixinException;
}
