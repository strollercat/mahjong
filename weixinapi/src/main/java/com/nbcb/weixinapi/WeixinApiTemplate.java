package com.nbcb.weixinapi;

import com.nbcb.weixinapi.entity.WeixinReplyAddTemplate;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinReplyPushTemplates;
import com.nbcb.weixinapi.entity.WeixinReplyTemplate;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate;
import com.nbcb.weixinapi.exception.WeixinException;

public interface WeixinApiTemplate {

	public WeixinReplyCommon setIndustry(String accessToken,
			String industryId1, String industryId2) throws WeixinException;

	public WeixinReplyAddTemplate addTemplate(String accessToken,
			String templateIdShort) throws WeixinException;

	public WeixinReplyPushTemplates queryAllTemplate(String accessToken)
			throws WeixinException;

	public WeixinReplyTemplate template(WeixinSendPushTemplate template)
			throws WeixinException;

}
