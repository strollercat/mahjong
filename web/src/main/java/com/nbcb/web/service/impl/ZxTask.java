package com.nbcb.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.web.dao.GameDictDao;
import com.nbcb.weixinapi.WeixinApiTemplate;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.entity.WeixinReplyCommon;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate;
import com.nbcb.weixinapi.transport.HttpClientRequest;
import com.nbcb.weixinapi.transport.HttpClientResponse;
import com.nbcb.weixinapi.transport.HttpClientTransport;
import com.nbcb.weixinapi.transport.Transport;

public class ZxTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(ZxTask.class);

	private int second = 20000;

	private String appid;

	private String url = "https://zxyh.nbcb.com.cn/desktop/InvestListQry.do";

	private String body = "{\"PageNum\":1,\"PageCount\":\"5\",\"ExpectIncomeMin\":\"\",\"ExpectIncomeMax\":\"\",\"InvestLimitMin\":\"\",\"InvestLimitMax\":\"\",\"ProjectScaleMin\":\"\",\"ProjectScaleMax\":\"\",\"SortRule\":\"0\",\"ProjName\":\"\",\"_ChannelId\":\"pc\",\"OS\":\"PC\",\"BankId\":\"9999\"}";

	private WeixinApiTemplate weixinApiTemplate;

	private WeixinCachedApiBase weixinCachedApiBase;

	private Transport httpClientTransport;

	private GameDictDao gameDictDao;

	private String templateId;

	private String superadmin;

	public void setGameDictDao(GameDictDao gameDictDao) {
		this.gameDictDao = gameDictDao;
	}

	public void setSuperadmin(String superadmin) {
		this.superadmin = superadmin;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setWeixinApiTemplate(WeixinApiTemplate weixinApiTemplate) {
		this.weixinApiTemplate = weixinApiTemplate;
	}

	public void setHttpClientTransport(Transport httpClientTransport) {
		this.httpClientTransport = httpClientTransport;
	}

	public void setWeixinCachedApiBase(WeixinCachedApiBase weixinCachedApiBase) {
		this.weixinCachedApiBase = weixinCachedApiBase;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public void start() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, second);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("### start to run timertask");
		String value = gameDictDao.selectValueByKey("zxyh");
		if (StringUtils.isEmpty(value)) {
			return;
		}
		if (!value.equals("yes")) {
			return;
		}

		HttpClientRequest hcr = new HttpClientRequest(
				HttpClientRequest.POSTMETHOD, this.url, null, body.getBytes());
		try {
			HttpClientResponse hcResponse = (HttpClientResponse) httpClientTransport
					.submit(hcr);
			byte[] body = hcResponse.getBody();
			String strBody = new String(body, "UTF-8");
			Map map = (Map) JsonUtil.decode(strBody, HashMap.class);
			logger.info("### map [" + map + "]");
			if (!map.get("_RejCode").equals("000000")) {
				logger.error("### get invest list return code is not 000000");
				return;
			}
			List<Map> list = (List) map.get("List");
			
			boolean pushed = false;
			for (Map m : list) {
//				m.put("RemainAmt", "1");
				if (!m.get("RemainAmt").equals("0")) {
					logger.info("### found one");
					if (pushed) {
						return;
					}
					String accessToken = weixinCachedApiBase
							.getCachedAccessToken(this.appid);
					
//					accessToken = "10_HQ-rrLQt-4nnDNU_mJPZ24gCNj2SYu6AZ_icRFSSQ_ZsR5cFW125PTSoel6ZBXO5J8Tm7Is1MHReNzDpJqMRjnBst2ElxnxH09xcHDY8oayrq8YdkIwxYTzL3O26xSwrrnIRNIwUBOwDXr4hCHJcAAAXCE";

					WeixinSendPushTemplate wspt = new WeixinSendPushTemplate();
					wspt.setAccessToken(accessToken);
					wspt.setTemplate_id(this.templateId);
//					wspt.setTemplate_id("INFe3FIME2oUn38OBirhOsSuXbWTnSv7SNRFth5cnAE");
					wspt.setUrl("");
					wspt.setTouser(this.superadmin);
//					wspt.setTouser("o3WiI1W_-C0ZRKOA87_juQsrSAO4");
					WeixinReplyCommon wrc = weixinApiTemplate.template(wspt);
					pushed = true;
					return;
				}
			}
			logger.info("### do not found one");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("### transport zx error", e);
		}

	}

	public static void main(String[] args) {
		ZxTask zx = new ZxTask();
		Transport tr = new HttpClientTransport();
		zx.setHttpClientTransport(tr);
		zx.run();
	}
}
