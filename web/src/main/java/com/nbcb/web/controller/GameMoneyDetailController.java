package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.nbcb.common.util.BeanUtil;
import com.nbcb.common.util.DateUtil;
import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.TimeRange;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GameMoneyDetailController {

	private static final Logger logger = LoggerFactory
			.getLogger(GameMoneyDetailController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameMoneyDetailDao gameMoneyDetailDao;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private WeixinUserService weixinUserService;

	private boolean isRecommend(String account) {
		GameUser gu = this.gameUserDao.selectGameUserByAccount(account);
		return gu != null && gu.isRecommend();
	}

	@RequestMapping(value = "getSellMoneyDetailsByAccountPage")
	@ResponseBody
	public Response getListRoomsByCreateAccount(HttpServletRequest request,
			String date, int currentPage, int rowsPerPage) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		if (!this.isRecommend(account)) {
			return ResponseFactory.newResponse("000001", "没有权限");
		}

		Date endTime = DateUtil.parseDate(date);
		if (endTime == null) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}

		if (currentPage < 0 || currentPage > 200) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}
		if (rowsPerPage > 10 || rowsPerPage < 0) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}

		Page page = new Page(currentPage, rowsPerPage);
		logger.info("### account["+account+"]page["+page+"]endTime["+endTime+"]");

		List<GameMoneyDetail> list = this.gameMoneyDetailDao
				.getMoneyDetailsByActionAccountAndPage(account,
						GameMoneyDetail.AGENTSELLACTION, endTime, page);

		if (list == null || list.size() == 0) {
			return ResponseFactory.newResponse("000000", "success");
		}
		List<Map> listRet = new ArrayList<Map>();
		for (GameMoneyDetail gmd : list) {
			Map tmpMap = BeanUtil.bean2Map(gmd);
			WeixinReplyUser wru = this.weixinUserService
					.getWeixinUserByOpenid(gmd.getRelate());
			tmpMap.put("nickname", wru.getNickname());
			tmpMap.put("headimg", wru.getHeadimgurl());
			tmpMap.put("time", DateUtil.formatDate(gmd.getTime()));
			listRet.add(tmpMap);
		}
		return ResponseFactory.newResponse("000000", "success", listRet);
	}

	@RequestMapping(value = "getTotalSellMoneyByAccountTimeRange")
	@ResponseBody
	public Response getTotalSellMoneyByAccountTimeRange(
			HttpServletRequest request, String startTime, String endTime) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		if (!this.isRecommend(account)) {
			return ResponseFactory.newResponse("000001", "没有权限");
		}

		TimeRange timeRange = new TimeRange();
		try {

			timeRange.setStartTime(DateUtil.parseDate(startTime));
			timeRange.setEndTime(DateUtil.parseDate(endTime));
			if (timeRange.getStartTime().getTime() >= timeRange.getEndTime()
					.getTime()) {
				return ResponseFactory.newResponse("000001", "参数非法!");
			}
//			if ((timeRange.getEndTime().getTime() - timeRange.getStartTime()
//					.getTime()) > 30 * 24 * 60 * 60 * 1000) {
//				return ResponseFactory.newResponse("000001", "时间范围不能超过30天");
//			}
		} catch (Exception e) {
			logger.error("### e", e);
			return ResponseFactory.newResponse("000001", "参数非法!");
		}
		logger.info("### timeRange[" + timeRange + "]");
		return ResponseFactory.newResponse("000000", "success",
				this.gameMoneyDetailDao.totalMoneyByActionAccountAndTimeRange(
						account, GameMoneyDetail.AGENTSELLACTION, timeRange));
	}

}