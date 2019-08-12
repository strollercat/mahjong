package com.nbcb.weixinapi.impl;

import com.nbcb.weixinapi.WeixinApiPush;
import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinReplyManyPush;
import com.nbcb.weixinapi.entity.WeixinSendManyPushByGroup;
import com.nbcb.weixinapi.entity.WeixinSendManyPushByUsers;
import com.nbcb.weixinapi.entity.WeixinSendPushImage;
import com.nbcb.weixinapi.entity.WeixinSendPushNews;
import com.nbcb.weixinapi.entity.WeixinSendPushText;
import com.nbcb.weixinapi.entity.WeixinSendPushVoice;
import com.nbcb.weixinapi.exception.WeixinException;

public class WeixinApiPushImpl implements WeixinApiPush {

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	@Override
	public WeixinReplyCommon text(WeixinSendPushText text)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ text.getAccessToken();
		return weixinApiSupport.postWeixin(url, text, WeixinReplyCommon.class);
	}

	@Override
	public WeixinReplyCommon image(WeixinSendPushImage image)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ image.getAccessToken();
		return weixinApiSupport.postWeixin(url, image, WeixinReplyCommon.class);
	}

	@Override
	public WeixinReplyCommon voice(WeixinSendPushVoice voice)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ voice.getAccessToken();
		return weixinApiSupport.postWeixin(url, voice, WeixinReplyCommon.class);
	}

	// @Override
	// public WeixinReplyCommon video(WeixinSendPushVideo video)
	// throws WeixinException {
	// // TODO Auto-generated method stub
	// String url =
	// "http://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
	// + video.getAccessToken();
	// return weixinApiSupport.postWeixin(url, video, WeixinReplyCommon.class);
	// }

	// @Override
	// public WeixinReplyCommon music(WeixinSendPushMusic music)
	// throws WeixinException {
	// // TODO Auto-generated method stub
	// String url =
	// "http://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
	// + music.getAccessToken();
	// return weixinApiSupport.postWeixin(url, music, WeixinReplyCommon.class);
	// }

	@Override
	public WeixinReplyCommon news(WeixinSendPushNews pushNews)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ pushNews.getAccessToken();
		return weixinApiSupport.postWeixin(url, pushNews,
				WeixinReplyCommon.class);
	}

	@Override
	public WeixinReplyManyPush manyPushByGroup(
			WeixinSendManyPushByGroup weixinSendManyPushByGroup)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
				+ weixinSendManyPushByGroup.getAccessToken();
		return weixinApiSupport.postWeixin(url, weixinSendManyPushByGroup,
				WeixinReplyManyPush.class);
	}

	@Override
	public WeixinReplyManyPush manyPushByUsers(
			WeixinSendManyPushByUsers weixinSendManyPushByUsers)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="
				+ weixinSendManyPushByUsers.getAccessToken();
		return weixinApiSupport.postWeixin(url, weixinSendManyPushByUsers,
				WeixinReplyManyPush.class);
	}

}
