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

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CheckBattleController {

	private static final Logger logger = LoggerFactory
			.getLogger(CheckBattleController.class);

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private EnvironmentService environmentService;

	@RequestMapping(value = "checkBattle")
	@ResponseBody
	public Response checkBattle(HttpServletRequest request) {
		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		GameUser gameUser = gameUserDao.selectGameUserByAccount(openid);
		Map body = new HashMap();
		body.put("score", gameUser.getScore());
		body.put("win", gameUser.getWin());
		body.put("lose",gameUser.getLose());
		body.put("ping", gameUser.getPing());
		return ResponseFactory.newResponse("000000", "success", body);
	}
}
