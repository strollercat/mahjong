package com.nbcb.weixinhandle.pay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.common.util.RandomUtil;
import com.nbcb.weixinapi.WeixinApiPay;
import com.nbcb.weixinapi.WeixinApiPaySign;
import com.nbcb.weixinapi.entity.WeixinAccount;
import com.nbcb.weixinapi.entity.WeixinReplyUnifiedOrder;
import com.nbcb.weixinapi.entity.WeixinSendUnifiedOrder;
import com.nbcb.weixinhandle.entity.CommonArgs;
import com.nbcb.weixinhandle.entity.Context;

public abstract class WeixinAbstractBasePayHandle implements WeixinPayHandle {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinAbstractBasePayHandle.class);

	private WeixinApiPay weixinApiPay;

	private WeixinAccount weixinAccount;

	private WeixinApiPaySign weixinApiPaySign;

	public void setWeixinApiPaySign(WeixinApiPaySign weixinApiPaySign) {
		this.weixinApiPaySign = weixinApiPaySign;
	}

	public void setWeixinApiPay(WeixinApiPay weixinApiPay) {
		this.weixinApiPay = weixinApiPay;
	}

	public void setWeixinAccount(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	@Override
	public String handle(Context context) {
		// TODO Auto-generated method stub
		return String.format(CommonArgs.PAYRETURNARGS, "SUCCESS", "OK");
	}

	private String getRemoteIp(Context context) {
		HttpServletRequest request = context.getRequest();
		String remoteIp = request.getHeader("x-forwarded-for");
		if (!StringUtils.hasText(remoteIp)) {
			return "127.0.0.1";
		}
		if (remoteIp.contains(",")) {
			try {
				String[] ips = remoteIp.split(",");
				remoteIp = ips[ips.length - 1];
			} catch (Exception e) {
				logger.error("### remote ip split error,", e);
				remoteIp = "127.0.0.1";
			}
		}
		return remoteIp;
	}

	@Override
	public String scanPayHandle(Context context) {
		// TODO Auto-generated method stub
		logger.info("### coming to the WeixinAbstractBasePayHandle");
		String appid = context.getString("appid");
		String mchId = context.getString("mch_id");
		String productId = context.getString("product_id");
		String openid = context.getString("openid");

		WeixinSendUnifiedOrder w = new WeixinSendUnifiedOrder();
		w.setAppid(appid);
		w.setMch_id(mchId);
		w.setBody(this.generateBody(productId));
		w.setOut_trade_no(this.generateTradeNo(productId));
		w.setTotal_fee(this.generateTotalFee(productId));
		w.setSpbill_create_ip(this.getRemoteIp(context));
		w.setNotify_url(this.generateUrl());
		w.setTrade_type("NATIVE");
		w.setOpenid(openid);

		WeixinReplyUnifiedOrder weixinReplyUnifiedOrder = null;
		try {
			weixinReplyUnifiedOrder = weixinApiPay.unifiedOrder(w);
			Map mapRet = new HashMap();
			mapRet.put("return_code", "SUCCESS");
			mapRet.put("appid", appid);
			mapRet.put("mch_id", mchId);
			mapRet.put("prepay_id", weixinReplyUnifiedOrder.getPrepay_id());
			mapRet.put("result_code", "SUCCESS");
			mapRet.put("nonce_str", RandomUtil.getRandomString(32));
			mapRet.put("sign", weixinApiPaySign.getSign(mapRet, appid));
			return this.toXmlString(mapRet);
		} catch (Exception e) {
			return String.format(CommonArgs.PAYRETURNARGS, "FAIL",
					"systemerror");
		}
	}

	private String toXmlString(Map<String, String> map) {
		String result = "<xml>";
		for (String key : map.keySet()) {
			String value = map.get(key);
			String xml = "<" + key + ">" + value + "</" + key + ">";
			result += xml;
		}
		result += "</xml>";
		return result;
	}

	public abstract String generateTradeNo(String productId);

	public abstract String generateTotalFee(String productId);

	public abstract String generateBody(String productId);

	public abstract String generateUrl();

}
