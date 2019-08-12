package com.nbcb.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcb.common.util.InputStreamUtil;
import com.nbcb.common.util.XmlUtil;
import com.nbcb.web.service.OrderNotifyService;
import com.nbcb.weixinapi.WeixinApiPaySign;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class WeixinPayCallbackController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinPayCallbackController.class);

	@Autowired
	private OrderNotifyService orderNotifyService;

	@Autowired
	private WeixinApiPaySign weixinApiPaySign;

	private String getWeixinPayResponse(String errCode, String errMsg) {
		return "<xml><return_code><![CDATA[" + errCode
				+ "]]></return_code><return_msg><![CDATA[" + errMsg
				+ "]]></return_msg></xml>";
	}

	private String getWeixinPaySuccessResponse() {
		return this.getWeixinPayResponse("SUCCESS", "OK");
	}

	private String getWeixinPayFailResponse(String failReason) {
		return this.getWeixinPayResponse("FAIL", failReason);
	}

	@RequestMapping(value = "weixinPayCallback")
	@ResponseBody
	public String weixinPayCallback(HttpServletRequest request) {
		Map dataMap = null;
		try {
			byte[] byteReq = InputStreamUtil.readStream(
					request.getInputStream(), request.getContentLength());
			String strReq = new String(byteReq, "utf-8");
			logger.info("###   strReq["+strReq+"]");
			dataMap = XmlUtil.decode(strReq);
		} catch (Exception e) {
			logger.error("### error", e);
			return this.getWeixinPayFailResponse("格式错误!!!");
		}
		if (dataMap == null) {
			logger.error("### the dataMap is null,this is illegal request!!");
			return this.getWeixinPayFailResponse("格式错误!!!");
		}
		logger.info("### weixinPayCallback body infomation is " + dataMap);
		String return_code = (String) dataMap.get("return_code");
		String return_msg = (String) dataMap.get("return_msg");
		if (!StringUtils.hasText(return_code)) {
			logger.error("### the return_code is null,this is illegal request!!");
			return this.getWeixinPayFailResponse("格式错误!!!");
		}
		if (!"SUCCESS".equals(return_code)) {
			logger.error("### the return_code is not SUCCESS,this is illegal request!!");
			return this.getWeixinPayFailResponse("return_code is not SUCCESS");
		}

		String sign = (String) dataMap.get("sign");
		String appid = (String) dataMap.get("appid");
		dataMap.remove("sign");
		if (!weixinApiPaySign.validateSign(dataMap, appid, sign)) {
			logger.error("### 参数签名验证失败!");
			return this.getWeixinPayFailResponse("参数签名验证失败");
		}

		if (!"SUCCESS".equals(dataMap.get("result_code"))) {
			logger.error("### result_code  is not SUCCESS ");
			return this.getWeixinPayFailResponse("result_code  is not SUCCESS");
		}
		if (orderNotifyService.orderNotify(dataMap)) {
			return this.getWeixinPaySuccessResponse();
		} else {
			return this.getWeixinPayFailResponse("process fail");
		}

	}
}
