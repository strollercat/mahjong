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
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GameUserController {

	private static final Logger logger = LoggerFactory
			.getLogger(GameUserController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private WeixinUserDao weixinUserDao;

	@RequestMapping(value = "getGameUserNameById")
	@ResponseBody
	public Response getGameUserNameById(HttpServletRequest request) {
		String agentAccount = environmentService.getAccount(request);
		logger.info("### agentAccount["+agentAccount+"]");
		if (StringUtils.isEmpty(agentAccount)) {
			return ResponseFactory.newResponse("000001", "非法用户");
		}
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			return ResponseFactory.newResponse("000004", "参数非法");
		}

		GameUser agentGu = gameUserDao.selectGameUserByAccount(agentAccount);
		if (agentGu == null) {
			return ResponseFactory.newResponse("000002", "您不是代理商");
		}

		GameUser gameUser = gameUserDao.selectGameUserById(id);
		if (gameUser == null) {
			return ResponseFactory.newResponse("000004", "不存在ID为" + id + "的用户");
		}

		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setAppid(this.environmentService.getAppid());
		wru.setOpenid(gameUser.getOpenid());
		WeixinReplyUser retUser = weixinUserDao.selectUserByOpenid(wru);
		if (retUser == null) {
			return ResponseFactory.newResponse("000004", "不存在ID为" + id + "的用户");
		}
		Map map = new HashMap();
		map.put("name", retUser.getNickname());
		return ResponseFactory.newResponse("000000", "success", map);
	}
}