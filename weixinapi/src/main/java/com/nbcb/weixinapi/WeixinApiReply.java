package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinSendReplyImage;
import com.nbcb.weixinapi.entity.WeixinSendReplyNews;
import com.nbcb.weixinapi.entity.WeixinSendReplyText;
import com.nbcb.weixinapi.entity.WeixinSendReplyVoice;


/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiReply {
	public String replyText(WeixinSendReplyText text);

	public String replyImage(WeixinSendReplyImage image);

	public String replyNews(WeixinSendReplyNews news);

	public String replyVoice(WeixinSendReplyVoice voice);
}
