package com.nbcb.weixinapi.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.weixinapi.WeixinApiMedia;
import com.nbcb.weixinapi.WeixinApiSupport;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinReplyMedia;
import com.nbcb.weixinapi.entity.WeixinSendMedia;
import com.nbcb.weixinapi.exception.WeixinException;
import com.nbcb.weixinapi.transport.HttpClientRequest;
import com.nbcb.weixinapi.transport.HttpClientResponse;
import com.nbcb.weixinapi.transport.Transport;

/**
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinApiMediaImpl implements WeixinApiMedia {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinApiMediaImpl.class);

	private WeixinApiSupport weixinApiSupport;

	public void setWeixinApiSupport(WeixinApiSupport weixinApiSupport) {
		this.weixinApiSupport = weixinApiSupport;
	}

	private String _boundary = "----WebKitFormBoundaryAVy9JyALDavdDDnB";

	private Transport transport;

	private String encode;

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	private byte[] getFormatBytes(File file, String type) throws Exception {

		FileInputStream fis = new FileInputStream(file);

		String disposition = "Content-Disposition: form-data; name=\"media\"; "
				+ "filename=\"" + file.getName() + "\"; filelength=" + "\""
				+ String.valueOf(fis.available()) + "\"";

		String contentType = "; content-type=\"" + type + "\"";

		disposition += contentType;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		baos.write(("--" + _boundary + "\r\n").getBytes());
		baos.write((disposition + "\r\n").getBytes());

		baos.write("\r\n".getBytes());

		int ret;
		byte[] byteFile = new byte[1024];
		while (true) {
			ret = fis.read(byteFile);
			if (ret <= 0)
				break;
			baos.write(byteFile, 0, ret);
		}
		fis.close();
		baos.write("\r\n".getBytes());
		baos.write(("--" + _boundary + "--" + "\r\n").getBytes());
		byte[] byteRet = baos.toByteArray();
		baos.close();
		return byteRet;
	}

	@Override
	public WeixinReplyMedia uploadPermanentMedia(WeixinSendMedia weixinSendMedia)
			throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material";
		return this.uploadMedia(url, weixinSendMedia);
	}

	@Override
	public WeixinReplyCommon deletePermanentMedia(String accessToken,
			String mediaId) throws WeixinException {
		// TODO Auto-generated method stub
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token="
					+ accessToken;
			Map map = new HashMap();
			map.put("media_id", mediaId);
			return this.weixinApiSupport.postWeixin(url, map,
					WeixinReplyCommon.class);
		} catch (Exception e) {
			throw new WeixinException(999999, "调用腾讯接口异常", e);
		}
	}

	private WeixinReplyMedia uploadMedia(String url,
			WeixinSendMedia weixinSendMedia) throws WeixinException {
		try {
			url = url + "?access_token=" + weixinSendMedia.getAccessToken()
					+ "&type=" + weixinSendMedia.getType();
			logger.info("### uploadMedia url [" + url + "]");
			String contentType = "multipart/form-data;boundary=" + _boundary;
			byte[] content = this.getFormatBytes(
					new File(weixinSendMedia.getAbsoluteFileName()),
					weixinSendMedia.getType());
			Map<String, String> headers = new HashMap();
			headers.put("Content-Type", contentType);
			HttpClientRequest request = new HttpClientRequest(
					HttpClientRequest.POSTMETHOD, url, headers, content);
			HttpClientResponse response = (HttpClientResponse) transport
					.submit(request);
			WeixinReplyMedia weixinReplyMedia = null;
			if (response.getState() != 200) {
				throw new WeixinException(999998, "调用腾讯接口返回状态码不等于200 ,状态码："
						+ response.getState());
			}
			byte[] byteBody = response.getBody();
			String strBody = new String(byteBody, encode);
			logger.info("### updateMedia return body [" + strBody + "]");
			weixinReplyMedia = (WeixinReplyMedia) JsonUtil.decode(strBody,
					WeixinReplyMedia.class);
			if (weixinReplyMedia.getErrcode() != 0) {
				throw new WeixinException(999999, "调用腾讯接口返回值不为0");
			}
			return weixinReplyMedia;
		} catch (Exception e) {
			throw new WeixinException(999999, "调用腾讯接口异常", e);
		}
	}

	@Override
	public WeixinReplyMedia uploadTemporarytMedia(
			WeixinSendMedia weixinSendMedia) throws WeixinException {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload";
		return this.uploadMedia(url, weixinSendMedia);
	}
}
