package com.nbcb.weixinapi.impl;

import com.nbcb.weixinapi.WeixinApiMenu;
import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinSendMenus;
import com.nbcb.weixinapi.exception.WeixinException;



public class WeixinApiMenuImpl implements WeixinApiMenu {

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	@Override
	public WeixinReplyCommon createMenu(WeixinSendMenus wsms)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ wsms.getAccessToken();
		return weixinApiSupport.postWeixin(url, wsms, WeixinReplyCommon.class);
	}

}
