package com.nbcb.weixinapi.impl;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.WeixinApiTemplate;
import com.nbcb.weixinapi.entity.WeixinReplyAddTemplate;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinReplyPushTemplates;
import com.nbcb.weixinapi.entity.WeixinReplyTemplate;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate;
import com.nbcb.weixinapi.exception.WeixinException;

public class WeixinApiTemplateImpl implements WeixinApiTemplate {

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	@Override
	public WeixinReplyCommon setIndustry(String accessToken,
			String industryId1, String industryId2) throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="
				+ accessToken;
		Map map = new HashMap();
		map.put("industry_id1", industryId1);
		map.put("industry_id2", industryId2);
		return weixinApiSupport.postWeixin(url, map, WeixinReplyCommon.class);
	}

	@Override
	public WeixinReplyPushTemplates queryAllTemplate(String accessToken)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token="
				+ accessToken;
		return weixinApiSupport.postWeixin(url, null,
				WeixinReplyPushTemplates.class);
	}

	@Override
	public WeixinReplyTemplate template(WeixinSendPushTemplate template)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ template.getAccessToken();
		return weixinApiSupport.postWeixin(url, template,
				WeixinReplyTemplate.class);
	}

	@Override
	public WeixinReplyAddTemplate addTemplate(String accessToken,
			String templateIdShort) throws WeixinException{
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="
				+ accessToken;
		Map map = new HashMap();
		map.put("template_id_short", templateIdShort);
		return weixinApiSupport.postWeixin(url, map,
				WeixinReplyAddTemplate.class);
	}

}
