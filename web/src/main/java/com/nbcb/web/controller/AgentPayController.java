package com.nbcb.web.controller;

import java.util.Date;

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
import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.GameUserIdService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class AgentPayController {

	private static final Logger logger = LoggerFactory
			.getLogger(AgentPayController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private GameMoneyDetailDao gameMoneyDetailDao;
	
	@Autowired
	private GameUserIdService gameUserIdService;

	@RequestMapping(value = "agentPay")
	@ResponseBody
	public Response agentPay(HttpServletRequest request) {
		
	
		
		String agentAccount = environmentService.getAccount(request);
		if (StringUtils.isEmpty(agentAccount)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		int money;
		try {
			money = Integer.parseInt(request.getParameter("money"));
		} catch (Exception e) {
			return ResponseFactory.newResponse("000004", "参数非法");
		}
		String payAccount = request.getParameter("payAccount");
		if (StringUtils.isEmpty(payAccount)) {
			return ResponseFactory.newResponse("000004", "参数非法");
		}
		
		int gameUserId ;
		try{
			gameUserId= gameUserIdService.decode(payAccount);
		}catch (Exception e) {
			return ResponseFactory.newResponse("000004", "参数非法");
		}
		
		GameUser agentGu = gameUserDao.selectGameUserByAccount(agentAccount);
		if (agentGu == null) {
			return ResponseFactory.newResponse("000002", "不存在該用戶");
		}
		if (!agentGu.isRecommend()) {
			return ResponseFactory.newResponse("000003", "不是代理人");
		}

		if (agentGu.getMoney() < money) {
			return ResponseFactory.newResponse("000005", "您的钻石不够了");
		}
		GameUser payGu = gameUserDao.selectGameUserById(gameUserId);
		if (payGu == null) {
			return ResponseFactory.newResponse("000006", "不存在该ID号");
		}


		GameMoneyDetail gameMoneyDetail = new GameMoneyDetail();
		gameMoneyDetail.setAction(GameMoneyDetail.AGENTSELLACTION);
		gameMoneyDetail.setMoney(-money);
		gameMoneyDetail.setOpenid(agentAccount);
		gameMoneyDetail.setRelate(payGu.getOpenid());
		gameMoneyDetail.setRemark(GameMoneyDetail.AGENTSELLREMARK);
		gameMoneyDetail.setTime(new Date());
		gameMoneyDetailDao.insertGameMoneyDetail(gameMoneyDetail);

		gameMoneyDetail = new GameMoneyDetail();
		gameMoneyDetail.setAction(GameMoneyDetail.AGENTBUYACTION);
		gameMoneyDetail.setMoney(money);
		gameMoneyDetail.setOpenid(payGu.getOpenid());
		gameMoneyDetail.setRelate(agentGu.getOpenid());
		gameMoneyDetail.setRemark(GameMoneyDetail.AGENTBUYREMARK);
		gameMoneyDetail.setTime(new Date());
		gameMoneyDetailDao.insertGameMoneyDetail(gameMoneyDetail);

		GameUser gameUser = new GameUser();
		gameUser.setOpenid(agentAccount);
		gameUser.setMoney(-money);
		gameUserDao.addMoney(gameUser);

		gameUser = new GameUser();
		gameUser.setOpenid(payGu.getOpenid());
		gameUser.setMoney(money);
		gameUserDao.addMoney(gameUser);

		return ResponseFactory.newResponse("000000", "success");
	}
}