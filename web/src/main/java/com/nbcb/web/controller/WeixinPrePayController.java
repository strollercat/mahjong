package com.nbcb.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.common.util.BeanUtil;
import com.nbcb.common.util.RandomUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.OrderNumberGenerator;
import com.nbcb.weixinapi.WeixinApiPay;
import com.nbcb.weixinapi.WeixinApiPaySign;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.entity.WeixinReplyUnifiedOrder;
import com.nbcb.weixinapi.entity.WeixinSendUnifiedOrder;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class WeixinPrePayController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinPrePayController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private WeixinCachedApiBase weixinCachedApiBase;

	@Autowired
	private OrderNumberGenerator orderNumberGenerator;

	@Autowired
	private WeixinApiPay weixinApiPay;

	@Autowired
	private WeixinApiPaySign weixinApiPaySign;
	
	@Autowired
	private GameUserDao gameUserDao;

	private String getRemoteIp(HttpServletRequest request) {
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
		remoteIp = remoteIp.trim();
		return remoteIp;
	}

	@RequestMapping(value = "prePay")
	@ResponseBody
	public Response prePay(HttpServletRequest request) {

		String totalFee = request.getParameter("totalFee");
		logger.info("### prepay.do! totalFee[" + totalFee + "]");
		if (StringUtils.isEmpty(totalFee)) {
			return ResponseFactory.newResponse("000002", "参数非法");
		}
		if (!(totalFee.equals("200") || totalFee.equals("1500")
				|| totalFee.equals("3000") || totalFee.equals("4500") || totalFee
					.equals("7500"))) {
			return ResponseFactory.newResponse("000002", "金额不对");
		}
		// String totalFee = "1";

		logger.info("### totalFee[" + totalFee + "]");

		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		String appid = environmentService.getAppid();

		String outTradeNo = orderNumberGenerator.generatorOrderNumber();

		String mchId = weixinCachedApiBase.getWeixinAccountByAppid(appid)
				.getMach_id();

		String body = "游戏充值";
		
		String remoteIp = this.getRemoteIp(request);

		String url = environmentService.getFullUrl("weixinPayCallback.do");

		String tradeType = "JSAPI";

		String limitPay = "no_credit";

		logger.info("###   openid appid  outTradeNo mchId body remoteIp totalFee url tradeType limitPay\n"
				+ openid
				+ ","
				+ appid
				+ ","
				+ outTradeNo
				+ ","
				+ mchId
				+ ","
				+ body
				+ ","
				+ remoteIp
				+ ","
				+ totalFee
				+ ","
				+ url
				+ ","
				+ tradeType + "," + limitPay);
		WeixinSendUnifiedOrder w = new WeixinSendUnifiedOrder();
		w.setAppid(appid);
		w.setMch_id(mchId);
		w.setBody(body);
		w.setOut_trade_no(outTradeNo);
		w.setTotal_fee(totalFee);
		w.setSpbill_create_ip(remoteIp);
		w.setNotify_url(url);
		w.setTrade_type(tradeType);
		w.setOpenid(openid);
		w.setLimit_pay(limitPay);
		w.setAttach("userBuy");
		WeixinReplyUnifiedOrder weixinReplyUnifiedOrder = null;
		try {
			weixinReplyUnifiedOrder = weixinApiPay.unifiedOrder(w);
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			logger.error("### prePay error!", e);
			return ResponseFactory.newResponse("000002", "prePayError");
		}
		Map mapIn = BeanUtil.bean2Map(w);
		Map mapOut = BeanUtil.bean2Map(weixinReplyUnifiedOrder);
		mapIn.putAll(mapOut);
		mapIn.put("appid", appid);
		mapIn.put("openid", openid);

		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonceStr = RandomUtil.getRandomString(32);
		String pack = "prepay_id=" + weixinReplyUnifiedOrder.getPrepay_id();
		String signType = "MD5";
		Map mapFront = new HashMap();
		mapFront.put("appId", appid);
		mapFront.put("timeStamp", timeStamp);
		mapFront.put("nonceStr", nonceStr);
		mapFront.put("package", pack);
		mapFront.put("signType", signType);
		String paySign = weixinApiPaySign.getSign(mapFront, appid);
		mapFront.put("paySign", paySign);
		return ResponseFactory.newResponse("000000", "success", mapFront);
	}

	@RequestMapping(value = "agentPrePay")
	@ResponseBody
	public Response agentPrePay(HttpServletRequest request) {

		String diamond = request.getParameter("diamond");
		logger.info("### agentPrePay.do! diamond[" + diamond + "]");
		if (StringUtils.isEmpty(diamond)) {
			return ResponseFactory.newResponse("000002", "参数非法");
		}
		int intDiamond = 0;
		try {
			intDiamond = Integer.parseInt(diamond);
		} catch (Exception e) {
			return ResponseFactory.newResponse("000002", "亲,钻石数量必须是整数个数哦");
		}
		if (intDiamond < 0) {
			return ResponseFactory.newResponse("000002", "亲,钻石数量必须是正数哦");
		}
		if (intDiamond > 4000) {
			return ResponseFactory.newResponse("000002", "亲,一次性不能购买超过4000颗钻石哦");
		}
		int totalFee = intDiamond * 30;
		logger.info("### totalFee[" + totalFee + "]");

		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		GameUser gu = gameUserDao.selectGameUserByAccount(openid);
		if(gu == null || !gu.isRecommend()){
			return ResponseFactory.newResponse("000001", "您不是代理商,无权限购买");
		}
		

		String appid = environmentService.getAppid();

		String outTradeNo = orderNumberGenerator.generatorOrderNumber();

		String mchId = weixinCachedApiBase.getWeixinAccountByAppid(appid)
				.getMach_id();

		String body = "游戏充值";
		
		String remoteIp = this.getRemoteIp(request);

		String url = environmentService.getFullUrl("weixinPayCallback.do");

		String tradeType = "JSAPI";

		String limitPay = "no_credit";

		logger.info("###   openid appid  outTradeNo mchId body remoteIp totalFee url tradeType limitPay\n"
				+ openid
				+ ","
				+ appid
				+ ","
				+ outTradeNo
				+ ","
				+ mchId
				+ ","
				+ body
				+ ","
				+ remoteIp
				+ ","
				+ totalFee
				+ ","
				+ url
				+ ","
				+ tradeType + "," + limitPay);
		WeixinSendUnifiedOrder w = new WeixinSendUnifiedOrder();
		w.setAppid(appid);
		w.setMch_id(mchId);
		w.setBody(body);
		w.setOut_trade_no(outTradeNo);
		w.setTotal_fee(String.valueOf(totalFee));
		w.setSpbill_create_ip(remoteIp);
		w.setNotify_url(url);
		w.setTrade_type(tradeType);
		w.setOpenid(openid);
		w.setLimit_pay(limitPay);
		w.setAttach("agentBuy");
		WeixinReplyUnifiedOrder weixinReplyUnifiedOrder = null;
		try {
			weixinReplyUnifiedOrder = weixinApiPay.unifiedOrder(w);
		} catch (WeixinException e) {
			logger.error("### prePay error!", e);
			return ResponseFactory.newResponse("000002", "prePayError");
		}
		Map mapIn = BeanUtil.bean2Map(w);
		Map mapOut = BeanUtil.bean2Map(weixinReplyUnifiedOrder);
		mapIn.putAll(mapOut);
		mapIn.put("appid", appid);
		mapIn.put("openid", openid);

		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonceStr = RandomUtil.getRandomString(32);
		String pack = "prepay_id=" + weixinReplyUnifiedOrder.getPrepay_id();
		String signType = "MD5";
		Map mapFront = new HashMap();
		mapFront.put("appId", appid);
		mapFront.put("timeStamp", timeStamp);
		mapFront.put("nonceStr", nonceStr);
		mapFront.put("package", pack);
		mapFront.put("signType", signType);
		String paySign = weixinApiPaySign.getSign(mapFront, appid);
		mapFront.put("paySign", paySign);
		return ResponseFactory.newResponse("000000", "success", mapFront);
	}
}
