package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinReplyManyPush;
import com.nbcb.weixinapi.entity.WeixinSendManyPushByGroup;
import com.nbcb.weixinapi.entity.WeixinSendManyPushByUsers;
import com.nbcb.weixinapi.entity.WeixinSendPushImage;
import com.nbcb.weixinapi.entity.WeixinSendPushNews;
import com.nbcb.weixinapi.entity.WeixinSendPushText;
import com.nbcb.weixinapi.entity.WeixinSendPushVoice;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiPush {
	public WeixinReplyCommon text(WeixinSendPushText text)
			throws WeixinException;

	public WeixinReplyCommon image(WeixinSendPushImage image)
			throws WeixinException;

	public WeixinReplyCommon voice(WeixinSendPushVoice voice)
			throws WeixinException;

	public WeixinReplyCommon news(WeixinSendPushNews pushNews)
			throws WeixinException;


	public WeixinReplyManyPush manyPushByGroup(
			WeixinSendManyPushByGroup weixinSendManyPushByGroup)
			throws WeixinException;

	public WeixinReplyManyPush manyPushByUsers(
			WeixinSendManyPushByUsers weixinSendManyPushByUsers)
			throws WeixinException;
}
