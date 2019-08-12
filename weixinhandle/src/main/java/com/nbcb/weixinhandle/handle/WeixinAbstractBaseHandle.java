package com.nbcb.weixinhandle.handle;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.weixinapi.entity.WeixinAccount;
import com.nbcb.weixinhandle.entity.Context;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public abstract class WeixinAbstractBaseHandle implements WeixinHandle {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinAbstractBaseHandle.class);

	private WeixinAccount weixinAccount;

	public WeixinAccount getWeixinAccount() {
		return weixinAccount;
	}

	public void setWeixinAccount(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	public final String handle(Context context) {
		logger.debug("### WeixinAbstractBaseMessage handle!!!");
		preHandle(context);
		String msgType = context.getString("MsgType");
		logger.debug("### msgtype is " + msgType);
		if (msgType.equals("text")) {
			return textHandle(context);
		}

		if (msgType.equals("image")) {
			return imageHandle(context);
		}

		if (msgType.equals("voice")) {
			return voiceHandle(context);
		}

		if (msgType.equals("video")) {
			return videoHandle(context);
		}

		if (msgType.equals("shortvideo")) {
			return shortVideoHandle(context);
		}

		if (msgType.equals("location")) {
			return locationHandle(context);
		}

		if (msgType.equals("link")) {
			return linkHandle(context);
		}

		if (msgType.equals("event")) {
			return eventHandle(context);
		}
		logger.debug("### no handle function defined,msgType is " + msgType);
		afterHandle(context);
		return "";
	}

	private final String eventHandle(Context context) {
		String event = context.getString("Event");
		String eventKey = context.getString("EventKey");
		String ticket = context.getString("Ticket");
		logger.debug("### event handle ,event eventKey ticket ---- " + event + " "
				+ eventKey+" "+ticket);
		if (event.equals("subscribe")) {
			if (!StringUtils.hasText(ticket)) {
				return eventSubscribeHandle(context);
			} else {
				return eventSubscribeScanHandle(context);
			}
		}
		if (event.equals("unsubscribe")) {
			return eventUnsubscribeHandle(context);
		}
		if (event.equals("LOCATION")) {
			return eventLocationHandle(context);
		}
		if (event.equals("view")) {
			return eventViewHandle(context);
		}
		if (event.equals("CLICK")) {
			return eventClickHandle(context);
		}
		if (event.equals("SCAN")) {
			return eventSubscribeScanHandle(context);
		}
		if (event.equals("TEMPLATESENDJOBFINISH")) {
			return templateCallBackHandle(context);
		}
		logger.debug("### no event handle function defined,event is " + event);
		return "";

	}

	protected final String eventClickHandle(Context context) {

		String eventKey = context.getString("EventKey");
		logger.debug("### event click handle,eventKey: " + eventKey);

		Class clazz = WeixinAbstractBaseHandle.this.getClass();
		String methodName = "eventClick_" + eventKey + "_Handle";
		logger.debug("### clickHandle method name :" + methodName);
		Method method;
		try {
			method = clazz.getDeclaredMethod(methodName,
					new Class[] { Context.class });
			if (method.getReturnType() != String.class) {
				logger.error("### can not find the proper event click method,methodname "
						+ methodName);
				return "";
			}
			return (String) method.invoke(this, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("###  event click method invoke error", e);
			return "";
		}
	}

	abstract protected void preHandle(Context context);

	abstract protected void afterHandle(Context context);

	// 文本消息
	abstract protected String textHandle(Context context);

	// 图片消息
	abstract protected String imageHandle(Context context);

	// 语音消息
	abstract protected String voiceHandle(Context context);

	// 视频消息
	abstract protected String videoHandle(Context context);

	// 短视频消息
	abstract protected String shortVideoHandle(Context context);

	// 用户主动上传地理位置消息
	abstract protected String locationHandle(Context context);

	// 用户发送文章链接消息
	abstract protected String linkHandle(Context context);

	// 关注消息
	abstract protected String eventSubscribeHandle(Context context);

	// 取消关注消息
	abstract protected String eventUnsubscribeHandle(Context context);

	// 已关注情况下扫描二维码消息
	abstract protected String eventSubscribeScanHandle(Context context);

	// 未关注情况下扫描二维码消息
	abstract protected String eventUnsubscribeScanHandle(Context context);

	// 用户进入应用上传的地理位置消息或者每隔5秒上传地理位置消息
	abstract protected String eventLocationHandle(Context context);

	// 菜单VIEW消息
	abstract protected String eventViewHandle(Context context);

	// 模板推送给腾讯成功后的回调接口
	abstract protected String templateCallBackHandle(Context context);

}
