package com.nbcb.web.controller;

import java.util.Date;
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
public class GetTotalRoomsByRecommmendController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetTotalRoomsByRecommmendController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameStaticsService gameStaticsService;

	@Autowired
	private GameUserDao gameUserDao;

	@RequestMapping(value = "getTotalRoomsByRecommmend")
	@ResponseBody
	public Response getTotalRoomsByRecommmend(HttpServletRequest request,
			String date) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
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

		GameUser agentGu = gameUserDao.selectGameUserByAccount(account);
		if (agentGu == null) {
			return ResponseFactory.newResponse("000002", "不存在該用戶");
		}
		if (!agentGu.isRecommend()) {
			return ResponseFactory.newResponse("000003", "您不是代理人");
		}

		TimeRange timeRange = new TimeRange();
		timeRange.setStartTime(startDate);
		timeRange.setEndTime(endDate);
		logger.info("### getTotalRoomsByRecommmend timeRange[" + timeRange + "]");

		Map mapRet = gameStaticsService
				.getTotalRoomsAndMoneyConsumedByRecommmendAndTimeRange(
						agentGu.getId(), timeRange);
//		logger.info(mapRet+"");
		return ResponseFactory.newResponse("000000", "success", mapRet);
	}
}