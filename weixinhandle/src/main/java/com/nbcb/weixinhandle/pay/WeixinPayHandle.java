package com.nbcb.weixinhandle.pay;

import com.nbcb.weixinapi.entity.WeixinAccount;
import com.nbcb.weixinhandle.entity.Context;



public interface WeixinPayHandle {

	public void setWeixinAccount(WeixinAccount weixinAccount);

	public String handle(Context context);

	public String scanPayHandle(Context context);
}
