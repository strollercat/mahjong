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
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.service.EnvironmentService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetTotalUsersController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetTotalUsersController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "getTotalUsers")
	@ResponseBody
	public Response getTotalUsers(HttpServletRequest request) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		if (!adminService.isSuperAdmin(account)) {
			return ResponseFactory.newResponse("000001", "无权限!");
		}

		Map mapRet = new HashMap();
		mapRet.put("totalUsers", gameUserDao.selectTotalUsers());
		mapRet.put("totalLiveUsers", gameUserDao.selectTotalLiveUsers());
		return ResponseFactory.newResponse("000000", "success", mapRet);
	}
}