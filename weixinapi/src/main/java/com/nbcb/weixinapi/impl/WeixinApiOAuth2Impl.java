package com.nbcb.weixinapi.impl;

import java.net.URLEncoder;

import com.nbcb.weixinapi.WeixinApiOAuth2;
import com.nbcb.weixinapi.WeixinApiSupport;
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
public class WeixinApiOAuth2Impl implements WeixinApiOAuth2 {

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	@Override
	public String getOAuth2DoorUrl(WeixinSendOAuth2 weixinSendOAuth2) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
				.append(weixinSendOAuth2.getAppid()).append("&redirect_uri=")
				.append(URLEncoder.encode(weixinSendOAuth2.getRedirect_uri())).append("&response_type=")
				.append(weixinSendOAuth2.getResponse_type()).append("&scope=").append(weixinSendOAuth2.getScope())
				.append("#wechat_redirect");
		return sb.toString();
	}

	@Override
	public WeixinReplyOAuth2Token getOAuth2TokenByCode(WeixinSendOAuth2Code code) throws WeixinException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		sb.append(code.getAppid()).append("&secret=").append(code.getSecret()).append("&code=").append(code.getCode())
				.append("&grant_type=").append(code.getGrant_type());

		return weixinApiSupport.postWeixin(sb.toString(), null, WeixinReplyOAuth2Token.class);
	}

	@Override
	public WeixinReplyOAuth2Token refreshOAuth2Token(WeixinSendOAuth2Refresh weixinSendOAuth2Refresh)
			throws WeixinException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=");
		sb.append(weixinSendOAuth2Refresh.getAppid()).append("&grant_type=")
				.append(weixinSendOAuth2Refresh.getGrant_type()).append("&refresh_token=")
				.append(weixinSendOAuth2Refresh.getRefresh_token());

		return weixinApiSupport.postWeixin(sb.toString(), null, WeixinReplyOAuth2Token.class);
	}

	@Override
	public WeixinReplyUser getOAuth2User(WeixinSendUser sendUser) throws WeixinException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?access_token=");
		sb.append(sendUser.getAccessToken()).append("&openid=").append(sendUser.getOpenId()).append("&lang=")
				.append(WeixinSendUser.ZH_LANG);
		return weixinApiSupport.postWeixin(sb.toString(), null, WeixinReplyUser.class);
	}

}
