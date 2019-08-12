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
import com.nbcb.common.service.AdminService;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.EnvironmentService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class AgentSetController {

	private static final Logger logger = LoggerFactory
			.getLogger(AgentSetController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "agentSet")
	@ResponseBody
	public Response agentSet(HttpServletRequest request, int agentId,
			int isAgent) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		if (!adminService.isSuperAdmin(account)) {
			return ResponseFactory.newResponse("000001", "无权限");
		}

		GameUser agentGu = gameUserDao.selectGameUserById(agentId);
		if (agentGu == null) {
			return ResponseFactory.newResponse("000002", "不存在該用戶");
		}

		if (isAgent != 0 && isAgent != 1) {
			return ResponseFactory.newResponse("000002", "参数非法!");
		}

		if (agentGu.isRecommend() && isAgent == 1) {
			return ResponseFactory.newResponse("000000", "success");
		}

		if (!agentGu.isRecommend() && isAgent == 0) {
			return ResponseFactory.newResponse("000000", "success");
		}

		if (isAgent == 1) {
			agentGu.setRecommend(true);
		} else {
			agentGu.setRecommend(false);
		}
		this.gameUserDao.updateRecommendById(agentGu);
		return ResponseFactory.newResponse("000000", "success");
	}
}