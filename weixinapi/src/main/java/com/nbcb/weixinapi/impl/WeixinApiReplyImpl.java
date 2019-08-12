package com.nbcb.weixinapi.impl;

import com.nbcb.weixinapi.WeixinApiReply;
import com.nbcb.weixinapi.entity.WeixinSendNew;
import com.nbcb.weixinapi.entity.WeixinSendReplyImage;
import com.nbcb.weixinapi.entity.WeixinSendReplyNews;
import com.nbcb.weixinapi.entity.WeixinSendReplyText;
import com.nbcb.weixinapi.entity.WeixinSendReplyVoice;


/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinApiReplyImpl implements WeixinApiReply {

	@Override
	public String replyImage(WeixinSendReplyImage image) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("<xml>");
		getXmlCreateTime(sb);
		getXmlFromUser(sb, image.getFromUserName());
		getXmlToUser(sb, image.getToUserName());
		getXmlElement(sb, "MsgType", "image");
		sb.append("<Image>");
		getXmlElement(sb, "MediaId", image.getMediaId());
		sb.append("/Image>");
		sb.append("</xml>");
		return sb.toString();
	}

	@Override
	public String replyNews(WeixinSendReplyNews news) {
		// TODO Auto-generated method stub
		int size = news.getArticles().size();
		StringBuilder sb = new StringBuilder("<xml>");
		getXmlCreateTime(sb);
		getXmlFromUser(sb, news.getFromUserName());
		getXmlToUser(sb, news.getToUserName());
		getXmlElement(sb, "MsgType", "news");
		getXmlElement(sb, "ArticleCount", size);
		sb.append("<MsgType><![CDATA[news]]></MsgType>");
		sb.append("<Articles>");
		for (WeixinSendNew sendNew : news.getArticles()) {
			getNew(sb, sendNew);
		}
		sb.append("</Articles>");
		sb.append("</xml>");
		return sb.toString();
	}

	@Override
	public String replyVoice(WeixinSendReplyVoice voice) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("<xml>");
		getXmlCreateTime(sb);
		getXmlFromUser(sb, voice.getFromUserName());
		getXmlToUser(sb, voice.getToUserName());
		getXmlElement(sb, "MsgType", "voice");
		sb.append("<Voice>");
		getXmlElement(sb, "MediaId", voice.getMediaId());
		sb.append("/Voice>");
		sb.append("</xml>");
		return sb.toString();
	}

	@Override
	public String replyText(WeixinSendReplyText text) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("<xml>");
		getXmlCreateTime(sb);
		getXmlFromUser(sb, text.getFromUserName());
		getXmlToUser(sb, text.getToUserName());
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[" + text.getContent() + "]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}

	private void getXmlCreateTime(StringBuilder sb) {
		getXmlElement(sb, "CreateTime", (int) System.currentTimeMillis() / 1000);
	}

	private void getXmlFromUser(StringBuilder sb, String fromUser) {
		getXmlElement(sb, "FromUserName", fromUser);
	}

	private void getXmlToUser(StringBuilder sb, String toUser) {
		getXmlElement(sb, "ToUserName", toUser);
	}

	private void getXmlElement(StringBuilder sb, String key, String value) {
		sb.append("<").append(key).append(">");
		sb.append("<![CDATA[");
		sb.append(value);
		sb.append("]]>");
		sb.append("</");
		sb.append(key);
		sb.append(">");
	}

	private void getXmlElement(StringBuilder sb, String key, int value) {
		sb.append("<").append(key).append(">");
		sb.append(value);
		sb.append("</");
		sb.append(key);
		sb.append(">");
	}

	private void getNew(StringBuilder sb, WeixinSendNew sendNew) {
		sb.append("<item>");
		getXmlElement(sb, "Description", sendNew.getDescription());
		getXmlElement(sb, "PicUrl", sendNew.getPicurl());
		getXmlElement(sb, "Url", sendNew.getUrl());
		getXmlElement(sb, "Title", sendNew.getTitle());
		sb.append("</item>");
	}



}
