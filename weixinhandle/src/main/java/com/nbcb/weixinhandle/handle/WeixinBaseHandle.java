package com.nbcb.weixinhandle.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.weixinhandle.entity.Context;

/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinBaseHandle extends WeixinAbstractBaseHandle {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinAbstractBaseHandle.class);

	@Override
	protected void preHandle(Context context) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void afterHandle(Context context) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String textHandle(Context context) {
		// TODO Auto-generated method stub
		String content = context.getString("Content");
		logger.debug("### textHandle,content " + content);
		return "";
	}

	@Override
	protected String imageHandle(Context context) {
		// TODO Auto-generated method stub
		String picUrl = context.getString("PicUrl");
		String mediaId = context.getString("MediaId");
		logger.debug("### imageHandle,picUrl mediaId " + picUrl + "　" + mediaId);
		return "";
	}

	@Override
	protected String voiceHandle(Context context) {
		// TODO Auto-generated method stub
		String mediaId = context.getString("MediaId");
		String format = context.getString("Format");
		String Recognition = context.getString("Recognition");
		logger.debug("### voiceHandle,mediaId format Recognition " + mediaId
				+ "　" + format + " " + Recognition);
		return "";
	}

	@Override
	protected String videoHandle(Context context) {
		// TODO Auto-generated method stub
		String mediaId = context.getString("MediaId");
		String thumbMediaId = context.getString("ThumbMediaId");
		logger.debug("### videoHandle,mediaId thumbMediaId  " + mediaId + "　"
				+ thumbMediaId + " ");
		return "";
	}

	@Override
	protected String shortVideoHandle(Context context) {
		// TODO Auto-generated method stub
		String mediaId = context.getString("MediaId");
		String thumbMediaId = context.getString("ThumbMediaId");
		logger.debug("### shortVideoHandle,mediaId thumbMediaId  " + mediaId
				+ "　" + thumbMediaId + " ");
		return "";
	}

	@Override
	protected String locationHandle(Context context) {
		// TODO Auto-generated method stub

		String locationX = context.getString("Location_X");
		String locationY = context.getString("Location_Y");
		String scale = context.getString("Scale");
		String label = context.getString("Label");
		logger.debug("### locationHandle,locationX locationY scale label"
				+ locationX + "　" + locationY + " " + scale + " " + label);
		return "";
	}

	@Override
	protected String linkHandle(Context context) {
		// TODO Auto-generated method stub
		String title = context.getString("Title");
		String description = context.getString("Description");
		String url = context.getString("Url");
		logger.debug("### linkHandle title,description url" + title + "　"
				+ description + " " + url);
		return "";
	}

	@Override
	protected String eventSubscribeHandle(Context context) {
		// TODO Auto-generated method stub
		logger.debug("### eventSubscribeHandle");
		return "";
	}

	@Override
	protected String eventUnsubscribeHandle(Context context) {
		// TODO Auto-generated method stub
		logger.debug("### eventUnsubscribeHandle  ");
		return "";
	}

	@Override
	protected String eventSubscribeScanHandle(Context context) {
		// TODO Auto-generated method stub
		String eventKey = context.getString("EventKey");
		String ticket = context.getString("Ticket");
		logger.debug("### eventSubscribeScanHandle eventKey ticket " + eventKey
				+ " " + ticket);
		return "";
	}

	@Override
	protected String eventUnsubscribeScanHandle(Context context) {
		// TODO Auto-generated method stub
		String eventKey = context.getString("EventKey");
		String ticket = context.getString("Ticket");
		logger.debug("### eventUnsubscribeScanHandle eventKey ticket "
				+ eventKey + " " + ticket);
		return "";
	}

	@Override
	protected String eventLocationHandle(Context context) {
		// TODO Auto-generated method stub
		String latitude = context.getString("Latitude");
		String longitude = context.getString("Longitude");
		String precision = context.getString("Precision");
		logger.debug("### eventLocationHandle latitude,longitude precision"
				+ latitude + "　" + longitude + " " + precision);
		return "";
	}

	@Override
	protected String eventViewHandle(Context context) {
		// TODO Auto-generated method stub
		String eventKey = context.getString("EventKey");
		logger.debug("### eventViewHandle eventKey  " + eventKey + " ");
		return "";
	}

	@Override
	protected String templateCallBackHandle(Context context) {
		// TODO Auto-generated method stub
		String status = context.getString("Status");
		String msgId = context.getString("MsgID");
		logger.debug("### templateCallBackHandle status msgId " + status + " "
				+ msgId);
		return null;
	}

}
