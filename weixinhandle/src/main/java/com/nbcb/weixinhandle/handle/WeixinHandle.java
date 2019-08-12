package com.nbcb.weixinhandle.handle;

import com.nbcb.weixinapi.entity.WeixinAccount;
import com.nbcb.weixinhandle.entity.Context;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinHandle {

	public void setWeixinAccount(WeixinAccount weixinAccount);

	public WeixinAccount getWeixinAccount();

	public String handle(Context context);
}
