package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyOAuth2Token;
import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinSendOAuth2;
import com.nbcb.weixinapi.entity.WeixinSendOAuth2Code;
import com.nbcb.weixinapi.entity.WeixinSendOAuth2Refresh;
import com.nbcb.weixinapi.entity.WeixinSendUser;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiOAuth2 {
	public String getOAuth2DoorUrl(WeixinSendOAuth2 weixinSendOAuth2);

	public WeixinReplyOAuth2Token getOAuth2TokenByCode(WeixinSendOAuth2Code code)
			throws WeixinException;

	public WeixinReplyOAuth2Token refreshOAuth2Token(
			WeixinSendOAuth2Refresh weixinSendOAuth2Refresh)
			throws WeixinException;

	public WeixinReplyUser getOAuth2User(WeixinSendUser sendUser)
			throws WeixinException;

}
