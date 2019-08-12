package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyQueryOrder;
import com.nbcb.weixinapi.entity.WeixinReplyUnifiedOrder;
import com.nbcb.weixinapi.entity.WeixinSendQueryOrder;
import com.nbcb.weixinapi.entity.WeixinSendUnifiedOrder;
import com.nbcb.weixinapi.exception.WeixinException;

public interface WeixinApiPay {
	public WeixinReplyUnifiedOrder unifiedOrder(
			WeixinSendUnifiedOrder weixinSendUnifiedOrder)
			throws WeixinException;

	public WeixinReplyQueryOrder queryOrder(
			WeixinSendQueryOrder weixinSendQueryOrder) throws WeixinException;


}
