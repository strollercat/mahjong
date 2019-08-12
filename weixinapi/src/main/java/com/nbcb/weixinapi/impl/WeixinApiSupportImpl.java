package com.nbcb.weixinapi.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.exception.WeixinException;
import com.nbcb.weixinapi.transport.HttpClientRequest;
import com.nbcb.weixinapi.transport.HttpClientResponse;
import com.nbcb.weixinapi.transport.Transport;

/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinApiSupportImpl implements WeixinApiSupport {

	private static final Logger logger = LoggerFactory.getLogger(WeixinApiSupportImpl.class);

	private Transport transport;

	private String encode;

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public <IN, OUT extends WeixinReplyCommon> OUT postWeixin(String uri, IN in, Class<OUT> clz)
			throws WeixinException {
		HttpClientRequest request = null;
		HttpClientResponse response = null;
		WeixinReplyCommon wrc = null;
		String body = null;
		try {
			if (in == null) {
				body = "";
			} else {
				body = JsonUtil.encode(in);
			}
			logger.debug("### request to the tx body " + body + "   uri " + uri);
			request = new HttpClientRequest("", uri, null, body.getBytes(encode));
			response = (HttpClientResponse) transport.submit(request);
			if (response.getState() != 200) {
				throw new WeixinException(999998, "调用腾讯接口返回状态码不等于200 ,状态码：" + response.getBody());
			}
			byte[] byteBody = response.getBody();
			String strBody = new String(byteBody, encode);
			logger.debug("### response from the tx body " + strBody + "   uri " + uri);
			OUT outRet = (OUT) JsonUtil.decode(strBody, clz);
			if (outRet.getErrcode() != 0) {
				throw new WeixinException(outRet.getErrcode(), outRet.getErrmsg());
			}
			return outRet;
		} catch (Exception e) {
			throw new WeixinException(999999, "调用腾讯接口异常", e);
		}
	}
}
