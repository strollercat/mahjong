package com.nbcb.weixinhandle.handle;

import java.util.List;

import com.nbcb.weixinapi.entity.WeixinSendNew;
import com.nbcb.weixinapi.entity.WeixinSendPushImage;
import com.nbcb.weixinapi.entity.WeixinSendPushNews;
import com.nbcb.weixinapi.entity.WeixinSendPushText;
import com.nbcb.weixinapi.entity.WeixinSendPushVoice;
import com.nbcb.weixinapi.entity.WeixinSendReplyImage;
import com.nbcb.weixinapi.entity.WeixinSendReplyNews;
import com.nbcb.weixinapi.entity.WeixinSendReplyText;
import com.nbcb.weixinapi.entity.WeixinSendReplyVoice;
import com.nbcb.weixinapi.exception.WeixinException;
import com.nbcb.weixinhandle.entity.Context;






/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSimplifyApiHandle extends WeixinApiHandle {

	public String replyText(Context context, String content) {
		String toUserName = context.getString("ToUserName");
		String fromUserName = context.getString("FromUserName");
		WeixinSendReplyText wsrt = new WeixinSendReplyText();
		wsrt.setContent(content);
		wsrt.setFromUserName(toUserName);
		wsrt.setToUserName(fromUserName);
		return this.getWeixinApiReply().replyText(wsrt);
	}

	public String replyImage(Context context, String mediaId) {
		String toUserName = context.getString("ToUserName");
		String fromUserName = context.getString("FromUserName");
		WeixinSendReplyImage wsri = new WeixinSendReplyImage();
		wsri.setFromUserName(toUserName);
		wsri.setToUserName(fromUserName);
		wsri.setMediaId(mediaId);
		return this.getWeixinApiReply().replyImage(wsri);
	}

	public String replyVoice(Context context, String mediaId) {
		String toUserName = context.getString("ToUserName");
		String fromUserName = context.getString("FromUserName");
		WeixinSendReplyVoice wsrv = new WeixinSendReplyVoice();
		wsrv.setFromUserName(toUserName);
		wsrv.setToUserName(fromUserName);
		wsrv.setMediaId(mediaId);
		return this.getWeixinApiReply().replyVoice(wsrv);
	}

	public String replyNews(Context context, List<WeixinSendNew> weixinSendNews) {
		String toUserName = context.getString("ToUserName");
		String fromUserName = context.getString("FromUserName");
		WeixinSendReplyNews wsrns = new WeixinSendReplyNews();
		if (weixinSendNews != null) {
			for (WeixinSendNew weixinSendNew : weixinSendNews) {
				wsrns.addNew(weixinSendNew);
			}
		}
		wsrns.setFromUserName(toUserName);
		wsrns.setToUserName(fromUserName);
		return this.getWeixinApiReply().replyNews(wsrns);
	}

	public String replyNew(Context context, WeixinSendNew weixinSendNew) {
		String toUserName = context.getString("ToUserName");
		String fromUserName = context.getString("FromUserName");
		WeixinSendReplyNews wsrns = new WeixinSendReplyNews();
		wsrns.addNew(weixinSendNew);
		wsrns.setFromUserName(toUserName);
		wsrns.setToUserName(fromUserName);
		return this.getWeixinApiReply().replyNews(wsrns);
	}

	public String replyNew(Context context, String title, String url,
			String picurl, String description) {
		WeixinSendNew wsn = new WeixinSendNew();
		wsn.setTitle(title);
		wsn.setUrl(url);
		wsn.setPicurl(picurl);
		wsn.setDescription(description);
		return this.replyNew(context, wsn);
	}

	public void pushText(Context context, String content)
			throws WeixinException {

		String fromUserName = context.getString("FromUserName");
		String appid = this.getWeixinAccount().getAppid();
		String secret = this.getWeixinAccount().getSecret();
		String accessToken = this.getWeixinCachedApiBase()
				.getCachedAccessToken(appid);

		WeixinSendPushText wspt = new WeixinSendPushText();
		wspt.setAccessToken(accessToken);
		wspt.setContent(content);
		wspt.setTouser(fromUserName);
		this.getWeixinApiPush().text(wspt);
	}

	public void pushImage(Context context, String mediaId)
			throws WeixinException {
		String fromUserName = context.getString("FromUserName");
		String appid = this.getWeixinAccount().getAppid();
		String secret = this.getWeixinAccount().getSecret();
		String accessToken = this.getWeixinCachedApiBase()
				.getCachedAccessToken(appid);
		WeixinSendPushImage wspi = new WeixinSendPushImage();
		wspi.setAccessToken(accessToken);
		wspi.setTouser(fromUserName);
		wspi.setMediaId(mediaId);
		this.getWeixinApiPush().image(wspi);
	}

	public void pushVoice(Context context, String mediaId)
			throws WeixinException {
		String fromUserName = context.getString("FromUserName");
		String appid = this.getWeixinAccount().getAppid();
		String secret = this.getWeixinAccount().getSecret();
		String accessToken = this.getWeixinCachedApiBase()
				.getCachedAccessToken(appid);
		WeixinSendPushVoice wspv = new WeixinSendPushVoice();
		wspv.setAccessToken(accessToken);
		wspv.setTouser(fromUserName);
		wspv.setMediaId(mediaId);
		this.getWeixinApiPush().voice(wspv);
	}

	public void pushNews(Context context, List<WeixinSendNew> weixinSendNews)
			throws WeixinException {
		String fromUserName = context.getString("FromUserName");
		String appid = this.getWeixinAccount().getAppid();
		String secret = this.getWeixinAccount().getSecret();
		String accessToken = this.getWeixinCachedApiBase()
				.getCachedAccessToken(appid);
		WeixinSendPushNews wspns = new WeixinSendPushNews();
		wspns.setAccessToken(accessToken);
		wspns.setTouser(fromUserName);
		if (weixinSendNews != null) {
			for (WeixinSendNew weixinSendNew : weixinSendNews) {
				wspns.addNew(weixinSendNew);
			}
		}
		this.getWeixinApiPush().news(wspns);
	}

	public void pushNew(Context context, WeixinSendNew weixinSendNew)
			throws WeixinException {
		String fromUserName = context.getString("FromUserName");
		String appid = this.getWeixinAccount().getAppid();
		String secret = this.getWeixinAccount().getSecret();
		String accessToken = this.getWeixinCachedApiBase()
				.getCachedAccessToken(appid);
		WeixinSendPushNews wspns = new WeixinSendPushNews();
		wspns.setAccessToken(accessToken);
		wspns.setTouser(fromUserName);
		wspns.addNew(weixinSendNew);
		this.getWeixinApiPush().news(wspns);
	}

	public void pushNew(Context context, String title, String url,
			String picurl, String description) throws WeixinException {
		WeixinSendNew wsn = new WeixinSendNew();
		wsn.setTitle(title);
		wsn.setUrl(url);
		wsn.setPicurl(picurl);
		wsn.setDescription(description);
		this.pushNew(context, wsn);
	}
}
