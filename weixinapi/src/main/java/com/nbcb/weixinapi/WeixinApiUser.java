package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinReplyUsers;
import com.nbcb.weixinapi.entity.WeixinSendUser;
import com.nbcb.weixinapi.entity.WeixinSendUsers;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiUser {
	public WeixinReplyUser getUserById(WeixinSendUser sendUser)
			throws WeixinException;

	public WeixinReplyUsers getUsers(WeixinSendUsers sendUsers)
			throws WeixinException;
}
