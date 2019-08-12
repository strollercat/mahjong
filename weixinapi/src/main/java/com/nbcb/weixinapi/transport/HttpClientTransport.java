package com.nbcb.weixinapi.transport;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.nbcb.weixinapi.exception.WeixinException;

public class HttpClientTransport implements Transport {

	private CloseableHttpClient httpclient;

	public HttpClientTransport() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		cm.setDefaultMaxPerRoute(100);
		httpclient = HttpClients.custom().setConnectionManager(cm).build();
	}

	@Override
	public Object submit(Object object) throws WeixinException {
		// TODO Auto-generated method stub
		try {
			HttpClientRequest request = (HttpClientRequest) object;
			byte[] postData = request.getBody();
			HttpPost httpPost = new HttpPost(request.getUrl());
			httpPost.setEntity(new ByteArrayEntity(postData));
			if (request.getHeaders() != null) {
				Map<String, String> headers = request.getHeaders();
				for (String key : headers.keySet()) {
					httpPost.setHeader(key, headers.get(key));
				}
			}
			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			int result = httpResponse.getStatusLine().getStatusCode();
			if (result == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				byte[] body = EntityUtils.toByteArray(httpEntity);
				httpPost.releaseConnection();
				httpResponse.close();
				return new HttpClientResponse(result, body);
			}
			httpPost.releaseConnection();
			httpResponse.close();
			return new HttpClientResponse(result, null);
		} catch (Exception e) {
			throw new WeixinException(999999, "httpclient.executeMethod", e);
		}
	}

}
