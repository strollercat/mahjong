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
import com.nbcb.common.service.AdminService;
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
public class SendMoneyController {

	private static final Logger logger = LoggerFactory
			.getLogger(SendMoneyController.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private GameMoneyDetailDao gameMoneyDetailDao;

	@RequestMapping(value = "sendMoney")
	@ResponseBody
	public Response sendMoney(HttpServletRequest request, int userId, int money) {

		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		if (!adminService.isAdministrator(account)) {
			return ResponseFactory.newResponse("000001", "无权限!");
		}

		GameUser gameUser = gameUserDao.selectGameUserById(userId);
		if (gameUser == null) {
			return ResponseFactory.newResponse("000002", "不存在該用戶");
		}

		if (money <= 0 || money > 23000) {
			return ResponseFactory.newResponse("000002", "钻石数量不得高于23000");
		}

		GameMoneyDetail gameMoneyDetail = new GameMoneyDetail();
		gameMoneyDetail.setAction(GameMoneyDetail.SYSTEMSENDACTION);
		gameMoneyDetail.setMoney(money);
		gameMoneyDetail.setOpenid(gameUser.getOpenid());
		gameMoneyDetail.setRelate("");
		gameMoneyDetail.setRemark(GameMoneyDetail.SYSTEMSENDREMARK);
		gameMoneyDetail.setTime(new Date());
		gameMoneyDetailDao.insertGameMoneyDetail(gameMoneyDetail);

		GameUser gameUserParam = new GameUser();
		gameUserParam.setOpenid(gameUser.getOpenid());
		gameUserParam.setMoney(money);
		gameUser.setMoney(money);
		gameUserDao.addMoney(gameUser);

		return ResponseFactory.newResponse("000000", "success");
	}
}