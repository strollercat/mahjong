package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.nbcb.common.util.DateUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.TimeRange;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.GameStaticsService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetBillController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetBillController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameStaticsService gameStaticsService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "getBill")
	@ResponseBody
	public Response getBill(HttpServletRequest request, String startTime,
			String endTime) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		if (!adminService.isAdministrator(account)) {
			return ResponseFactory.newResponse("000001", "无权限!");
		}

		TimeRange timeRange = new TimeRange();
		try {

			timeRange.setStartTime(DateUtil.parseDate(startTime));
			timeRange.setEndTime(DateUtil.parseDate(endTime));
			if (timeRange.getStartTime().getTime() >= timeRange.getEndTime()
					.getTime()) {
				return ResponseFactory.newResponse("000001", "参数非法!");
			}
		} catch (Exception e) {
			logger.error("### e", e);
			return ResponseFactory.newResponse("000001", "参数非法!");
		}
		logger.info("### timeRange[" + timeRange + "]");
		Map mapRet = new HashMap();
		mapRet.put("money",
				gameStaticsService.getTotalPayByTimeRange(timeRange));
		return ResponseFactory.newResponse("000000", "success", mapRet);
	}
}