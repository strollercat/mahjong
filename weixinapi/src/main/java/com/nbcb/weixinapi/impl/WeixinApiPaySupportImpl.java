package com.nbcb.weixinapi.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.BeanUtil;
import com.nbcb.common.util.RandomUtil;
import com.nbcb.common.util.XmlUtil;
import com.nbcb.weixinapi.WeixinApiPaySign;
import com.nbcb.weixinapi.WeixinApiPaySupport;
import com.nbcb.weixinapi.entity.WeixinSendPayParent;
import com.nbcb.weixinapi.exception.WeixinException;
import com.nbcb.weixinapi.transport.HttpClientRequest;
import com.nbcb.weixinapi.transport.HttpClientResponse;
import com.nbcb.weixinapi.transport.Transport;

public class WeixinApiPaySupportImpl implements WeixinApiPaySupport {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinApiPaySupportImpl.class);

	private String encode;

	private Transport transport;


	private WeixinApiPaySign weixinApiPaySign;


	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public void setWeixinApiPaySign(WeixinApiPaySign weixinApiPaySign) {
		this.weixinApiPaySign = weixinApiPaySign;
	}

	@Override
	public <OUT> OUT nonCertExec(WeixinSendPayParent weixinSendPayParent,
			String uri, String name, Class<OUT> out) throws WeixinException {
		// TODO Auto-generated method stub
		HttpClientRequest request = null;
		HttpClientResponse response = null;
		String body = this.getBody(weixinSendPayParent,
				weixinSendPayParent.getAppid());
		try {
			logger.info("### 交易" + name + " request to the tx body " + body
					+ "   uri " + uri);
			request = new HttpClientRequest(HttpClientRequest.POSTMETHOD, uri,
					null, body.getBytes(encode));
			response = (HttpClientResponse) transport.submit(request);
			if (response.getState() != 200) {
				throw new WeixinException(999998, uri + "### 交易" + name
						+ " 调用腾讯接口返回状态码不等于200 ,状态码：" + response.getState());
			}
			byte[] byteBody = response.getBody();
			Map mapRet = (Map) XmlUtil.decode(new String(byteBody, encode));
			logger.info("### response from the tx mapRet " + mapRet);
			if ("FAIL".equals(mapRet.get("return_code"))) {
				throw new WeixinException(999997, "### 交易" + name + "返回报文校验异常 "
						+ mapRet.get("return_msg"));
			}
			String sign = (String) mapRet.get("sign");
			mapRet.remove("sign");
			boolean validateRet = weixinApiPaySign.validateSign(mapRet,
					weixinSendPayParent.getAppid(), sign);
			logger.debug("### 交易" + name + "返回报文校验 验证签名 " + validateRet);
			if (!validateRet) {
				throw new WeixinException(999998, "### 交易" + name
						+ " 返回报文校验 验证签名异常");
			}
			if ("FAIL".equals(mapRet.get("result_code"))) {
				throw new WeixinException(999997, "### 交易" + name
						+ "result_code异常 " + mapRet.get("err_code") + " "
						+ mapRet.get("err_code_des"));
			}
			return (OUT) BeanUtil.map2Bean(out, mapRet);

		} catch (Exception e) {
			throw new WeixinException(999999, "调用腾讯接口异常", e);
		}
	}

	private <IN> String getBody(IN in, String appid) {
		Map<String, Object> map = BeanUtil.bean2Map(in);
		map.put("nonce_str", RandomUtil.getRandomString(16));
		String sign = weixinApiPaySign.getSign(map, appid);
		map.put("sign", sign);
		String body = "";
		body += "<xml>";
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			body = body + "<" + key + ">" + value + "</" + key + ">";
		}
		body += "</xml>";
		return body;
	}


}
