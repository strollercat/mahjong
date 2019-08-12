package com.nbcb.web.controller;

import java.util.Date;
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
import com.nbcb.common.util.DateUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.TimeRange;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.GameStaticsService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetTotalRoomsAndMoneysController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetTotalRoomsAndMoneysController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameStaticsService gameStaticsService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "getTotalRoomsAndMoneys")
	@ResponseBody
	public Response getTotalRoomsAndMoneys(HttpServletRequest request,
			String date) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		if (!adminService.isAdministrator(account)) {
			return ResponseFactory.newResponse("000001", "无权限!");
		}

		Date startDate = DateUtil.get000000DateByDateStr(date);
		Date endDate = DateUtil.get235959DateByDateStr(date);
		endDate = new Date(endDate.getTime() + 1000);
		if (startDate == null || endDate == null) {
			return ResponseFactory.newResponse("000002", "参数非法");
		}
		if (startDate.getTime() >= endDate.getTime()) {
			return ResponseFactory.newResponse("000002", "参数非法");
		}

		TimeRange timeRange = new TimeRange();
		timeRange.setStartTime(startDate);
		timeRange.setEndTime(endDate);
		logger.info("### getTotalRoomsAndMoneys timeRange[" + timeRange + "]");
		Map mapRet = new HashMap();
		mapRet.put("totalRoom",
				gameStaticsService.getTotalRoomsByTimeRange(timeRange));
		mapRet.put("totalMoney",
				gameStaticsService.getTotalMoneyConsumedByTimeRange(timeRange));
		mapRet.put("totalPay",
				gameStaticsService.getTotalPayByTimeRange(timeRange));
		return ResponseFactory.newResponse("000000", "success", mapRet);
	}
}