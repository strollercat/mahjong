package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinSendMenus;
import com.nbcb.weixinapi.exception.WeixinException;


public interface WeixinApiMenu {
	public WeixinReplyCommon createMenu(WeixinSendMenus wsms)throws WeixinException;
}
