package com.nbcb.weixinapi.impl;

import org.springframework.util.StringUtils;

import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.WeixinApiUser;
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
public class WeixinApiUserImpl implements WeixinApiUser {

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	@Override
	public WeixinReplyUser getUserById(WeixinSendUser sendUser)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ sendUser.getAccessToken()
				+ "&openid="
				+ sendUser.getOpenId()
				+ "&lang=" + sendUser.getLang();
		return weixinApiSupport.postWeixin(url, null, WeixinReplyUser.class);
	}

	@Override
	public WeixinReplyUsers getUsers(WeixinSendUsers sendUsers)
			throws WeixinException {
		// TODO Auto-generated method stub
		String nextOpenid = sendUsers.getNext_openid();
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="
				+ sendUsers.getAccessToken();
		if (StringUtils.hasText(nextOpenid)) {
			url = url + "&next_openid=" + nextOpenid;
		}
		return weixinApiSupport.postWeixin(url, null, WeixinReplyUsers.class);
	}

}
