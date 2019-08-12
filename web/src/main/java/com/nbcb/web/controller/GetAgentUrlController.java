package com.nbcb.web.controller;

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
import com.nbcb.web.service.GameUserIdService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetAgentUrlController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetAgentUrlController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private GameUserIdService gameUserIdService;

	@RequestMapping(value = "getAgentUrl")
	@ResponseBody
	public Response getAgentUrl(HttpServletRequest request) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		GameUser agentGu = gameUserDao.selectGameUserByAccount(account);
		if (agentGu == null) {
			return ResponseFactory.newResponse("000002", "不存在該用戶");
		}
		if (!agentGu.isRecommend()) {
			return ResponseFactory.newResponse("000003", "您不是代理人");
		}

		
		return ResponseFactory.newResponse("000000", "success", gameUserIdService.encode(agentGu.getId()));
	}
}