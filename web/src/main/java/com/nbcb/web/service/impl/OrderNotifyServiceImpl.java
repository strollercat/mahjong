package com.nbcb.web.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.nbcb.common.service.AdminService;
import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.GameOrderDao;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.OrderNotifyService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.WeixinApiTemplate;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate.WeixinSendPushTemplateItem;
import com.nbcb.weixinapi.exception.WeixinException;

public class OrderNotifyServiceImpl implements OrderNotifyService {

	private static final Log logger = LogFactory
			.getLog(OrderNotifyServiceImpl.class);

	private GameOrderDao gameOrderDao;

	private GameMoneyDetailDao gameMoneyDetailDao;

	private GameUserDao gameUserDao;

	private String templateId;

	private WeixinApiTemplate weixinApiTemplate;

	private WeixinCachedApiBase weixinCachedApiBase;

	private AdminService adminService;

	private String appid;

	private WeixinUserService weixinUserService;

	public void setWeixinUserService(WeixinUserService weixinUserService) {
		this.weixinUserService = weixinUserService;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public void setWeixinCachedApiBase(WeixinCachedApiBase weixinCachedApiBase) {
		this.weixinCachedApiBase = weixinCachedApiBase;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setWeixinApiTemplate(WeixinApiTemplate weixinApiTemplate) {
		this.weixinApiTemplate = weixinApiTemplate;
	}

	public void setGameUserDao(GameUserDao gameUserDao) {
		this.gameUserDao = gameUserDao;
	}

	public void setGameMoneyDetailDao(GameMoneyDetailDao gameMoneyDetailDao) {
		this.gameMoneyDetailDao = gameMoneyDetailDao;
	}

	public void setGameOrderDao(GameOrderDao gameOrderDao) {
		this.gameOrderDao = gameOrderDao;
	}

	private void asynTemplate(Map mapOrder, String result) throws Exception {

		String money = (String) mapOrder.get("total_fee");
		String outTradeNo = (String) mapOrder.get("out_trade_no");
		String openid = (String) mapOrder.get("openid");
		WeixinReplyUser wru = weixinUserService.getWeixinUserByOpenid(openid);
		String nickName = wru.getNickname();
		String imgUrl = wru.getHeadimgurl();
		String id = gameUserDao.selectGameUserByAccount(openid).getId() + "";
		logger.info("### asynTemplate money[" + money + "]outTradeNo["
				+ outTradeNo + "]openid[" + openid + "]nickname[" + nickName
				+ "]imgUrl[" + imgUrl + "]id[" + id + "]");

		final WeixinSendPushTemplate wst = new WeixinSendPushTemplate();
		wst.setAccessToken(this.weixinCachedApiBase.getCachedAccessToken(appid));
		wst.setTouser(adminService.getSuperAdminAccount());
		wst.setUrl(imgUrl);
		wst.setTemplate_id(templateId);

		WeixinSendPushTemplateItem item = new WeixinSendPushTemplateItem();
		item.setValue(nickName + "充值通知");
		wst.addItem("first", item);

		item = new WeixinSendPushTemplateItem();
		item.setValue("ID");
		wst.addItem("accountType", item);

		item = new WeixinSendPushTemplateItem();
		item.setValue(id);
		wst.addItem("account", item);

		item = new WeixinSendPushTemplateItem();
		item.setValue(money + "分");
		wst.addItem("amount", item);

		item = new WeixinSendPushTemplateItem();
		item.setValue(result);
		wst.addItem("result", item);

		item = new WeixinSendPushTemplateItem();
		item.setValue("outTradeNo[" + outTradeNo + "]");
		wst.addItem("remark", item);

		new Thread(new Runnable() {
			public void run() {
				try {
					OrderNotifyServiceImpl.this.weixinApiTemplate.template(wst);
				} catch (WeixinException e) {
					logger.error(
							"########################################### error ",
							e);
				}
			}
		}).start();
	}

	@Override
	public boolean orderNotify(Map mapOrder) {
		// TODO Auto-generated method stub
		try {
			this.asynTemplate(mapOrder, "支付成功回调");
		} catch (Exception e) {
			logger.error("########################################### error ",
					e);
		}

		String outTradeNo = (String) mapOrder.get("out_trade_no");
		if (!StringUtils.hasText(outTradeNo)) {
			logger.error("### outTradeNo 为空");
			return false;
		}
		Map mapOrderRet = gameOrderDao.selectGameOrderByTradeNo(outTradeNo);
		if (mapOrderRet != null) {
			logger.info("有相关订单信息，可能是重复推送 " + mapOrder);
			return true;
		}

		logger.info("### 没有相关的订单信息,可以插入");
		try {
			gameOrderDao.insertGameOrder(mapOrder);
		} catch (Exception e) {
			logger.info("### 可能是重复推送", e);
			return true;
		}
		try {
			this.processZuanshi(mapOrder);
		} catch (Exception e) {
			logger.error(
					"###########################################  冲钻失败!!!", e);
			try {
				this.asynTemplate(mapOrder, "钻石充值失败");
			} catch (Exception e1) {
				logger.error(
						"########################################### error ",
						e1);
			}

			return true;
		}

		return true;

	}

	private void processZuanshi(Map mapOrder) {
		int intFee = Integer.parseInt((String) mapOrder.get("total_fee"));
		String openid = (String) mapOrder.get("openid");
		int id = Integer.parseInt(mapOrder.get("id").toString());
		String attach = (String) mapOrder.get("attach");
		logger.info("### intFee[" + intFee + "]openid[" + openid + "]id[" + id
				+ "]attach["+attach+"]");
		
		int money = 0;
		if("userBuy".equals(attach)){
			if (intFee == 200) {
				money = 1;
			} else if (intFee == 1500) {
				money = 11;
			} else if (intFee == 3000) {
				money = 22;
			} else if (intFee == 4500) {
				money = 33;
			} else if(intFee == 7500){
				money = 55;
			}else{
				return ;
			}
		}else if("agentBuy".equals(attach)){
			money = intFee / 30;
		}else{
			return ;
		}
		
		if (money == 0) {
			throw new RuntimeException("### money is 0");
		}
		GameMoneyDetail gameMoneyDetail = new GameMoneyDetail();
		gameMoneyDetail.setAction(GameMoneyDetail.ONLINEBUYACTION);
		gameMoneyDetail.setMoney(money);
		gameMoneyDetail.setOpenid(openid);
		gameMoneyDetail.setRelate(id+"");
		gameMoneyDetail.setRemark(GameMoneyDetail.ONLINEBUYREMARK);
		gameMoneyDetail.setTime(new Date());
		gameMoneyDetailDao.insertGameMoneyDetail(gameMoneyDetail);

		GameUser gameUser = new GameUser();
		gameUser.setOpenid(openid);
		gameUser.setMoney(money);
		gameUserDao.addMoney(gameUser);

	}
}
