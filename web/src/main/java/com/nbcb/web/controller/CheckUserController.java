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
import com.nbcb.common.service.AdminService;
import com.nbcb.web.dao.GameDictDao;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.GameUserIdService;
import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CheckUserController {

	private static final Logger logger = LoggerFactory
			.getLogger(CheckUserController.class);

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private WeixinUserDao weixinUserDao;

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private GameUserIdService gameUserIdService;
	
	@Autowired
	private GameDictDao gameDictDao;

	@RequestMapping(value = "checkUser")
	@ResponseBody
	public Response checkUser(HttpServletRequest request) {
		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		Map body = new HashMap();
		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setOpenid(openid);
		wru.setAppid(environmentService.getAppid());
		wru = this.weixinUserDao.selectUserByOpenid(wru);
		if (wru == null || StringUtils.isEmpty(wru.getOpenid())) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		body.put("nickname", wru.getNickname());
		body.put("headimg", wru.getHeadimgurl());
		GameUser gameUser = gameUserDao.selectGameUserByAccount(openid);
		body.put("money", gameUser.getMoney());
		body.put("score", gameUser.getScore());
		String userId = gameUserIdService.encode(gameUser.getId());
		body.put("specialid", gameUser.isRecommend() ? userId : "");
		body.put("gameUserId", userId);
		body.put("isAdmin",this.adminService.isAdministrator(openid));
		body.put("superAdmin",this.adminService.isSuperAdmin(openid));
		body.put("message", this.gameDictDao.selectValueByKey("notification"));
		return ResponseFactory.newResponse("000000", "success", body);
	}

}
