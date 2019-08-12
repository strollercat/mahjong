package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinReplyMedia;
import com.nbcb.weixinapi.entity.WeixinSendMedia;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinApiMedia {

	public WeixinReplyMedia uploadPermanentMedia(WeixinSendMedia weixinSendMedia)
			throws WeixinException;

	public WeixinReplyCommon deletePermanentMedia(String accessToken,
			String mediaId) throws WeixinException;
	
	public WeixinReplyMedia uploadTemporarytMedia(WeixinSendMedia weixinSendMedia)
			throws WeixinException;

}
