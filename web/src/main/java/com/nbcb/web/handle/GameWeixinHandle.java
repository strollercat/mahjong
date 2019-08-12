package com.nbcb.web.handle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.common.service.AdminService;
import com.nbcb.common.util.DateUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.TimeRange;
import com.nbcb.web.service.GameStaticsService;
import com.nbcb.weixinhandle.entity.Context;
import com.nbcb.weixinhandle.handle.WeixinDefaultHandle;

public class GameWeixinHandle extends WeixinDefaultHandle {

	private static final Logger logger = LoggerFactory
			.getLogger(GameWeixinHandle.class);

	private AdminService adminService;

	private GameStaticsService gameStaticsService;

	private GameUserDao gameUserDao;

	public void setGameUserDao(GameUserDao gameUserDao) {
		this.gameUserDao = gameUserDao;
	}

	public void setGameStaticsService(GameStaticsService gameStaticsService) {
		this.gameStaticsService = gameStaticsService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String eventClick_KEY_DAILI_Handle(Context context) {
		String content = "诚邀代理商。代理加盟联系微信号 redier ";
		return this.replyText(context, content);
	}

	@Override
	protected String textHandle(Context context) {
		// TODO Auto-generated method stub
		String content = context.getString("Content");
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		String fromUserName = context.getString("FromUserName");
		if ("yesterday".equals(content) || "today".equals(content)) {
			if (!this.adminService.isAdministrator(fromUserName)) {
				return "";
			}
			Date startDate, endDate;
			String replyContent;
			if ("yesterday".equals(content)) {
				startDate = DateUtil.get000000DateOfToday();
				startDate = new Date(startDate.getTime() - 24 * 60 * 60 * 1000);
				endDate = DateUtil.get235959DateOfToday();
				endDate = new Date(endDate.getTime() - 24 * 60 * 60 * 1000);
				replyContent = "昨日数据如下:    ";
			} else {
				startDate = DateUtil.get000000DateOfToday();
				endDate = DateUtil.get235959DateOfToday();
				replyContent = "今日数据如下(截止当前):    ";
			}
			TimeRange timeRange = new TimeRange();
			timeRange.setStartTime(startDate);
			timeRange.setEndTime(endDate);

			replyContent += ("总开房数:"
					+ this.gameStaticsService
							.getTotalRoomsByTimeRange(timeRange) + "\r\n");
//			replyContent += ("总消耗钻石数:"
//					+ this.gameStaticsService
//							.getTotalMoneyConsumedByTimeRange(timeRange) + "\r\n");
//			replyContent += ("总在线充值数:"
//					+ this.gameStaticsService.getTotalPayByTimeRange(timeRange) + "\r\n");
			return this.replyText(context, replyContent);

		}
		Map map = null;
		try {
			map = parse(content);
		} catch (Exception e) {
			return "";
		}
		if (map == null) {
			return "";
		}
		return this.adminIdRecommendRoomd(fromUserName, map, context);
	}

	private String adminIdRecommendRoomd(String fromUserName, Map map,
			Context context) {
		String id = (String) map.get("id");
		if (!StringUtils.isEmpty("id")
				&& adminService.isAdministrator(fromUserName)) {
			int intId;
			try {
				intId = Integer.parseInt(id);
			} catch (Exception e) {
				return this.replyText(context, "id应该为整数");
			}
			GameUser gameUser = gameUserDao.selectGameUserById(intId);
			if (gameUser == null) {
				return this.replyText(context, "id:" + intId + "的用户不存在!");
			}
			if (!gameUser.isRecommend()) {
				return this.replyText(context, "id:" + intId + "的用户不是代理商!");
			}
			String date = (String) map.get("date");
			Date startDate = DateUtil.get000000DateByDateStr(date);
			Date endDate = DateUtil.get235959DateByDateStr(date);
			if (startDate == null || endDate == null) {
				return this.replyText(context,
						"格式错误,正确的格式应该为\"#id:000001#date:2018-03-04\"");
			}

			TimeRange timeRange = new TimeRange();
			timeRange.setStartTime(startDate);
			timeRange.setEndTime(endDate);

			Map mapRet = this.gameStaticsService
					.getTotalRoomsAndMoneyConsumedByRecommmendAndTimeRange(gameUser.getId(),
							timeRange);

			String replyText = DateUtil.formatDate(startDate) + "到"
					+ DateUtil.formatDate(endDate) + ",ID为" + intId
					+ "的代理商推荐房间数如下:";

			replyText += "4局房间:" + mapRet.get("r4") + "---";
			replyText += "8局房间:" + mapRet.get("r8") + "---";
			replyText += "12局房间:" + mapRet.get("r12") + "---";
			replyText += "16局房间:" + mapRet.get("r16") + "---";
//			replyText += "总消耗钻石数:" + mapRet.get("totalMoney");

			return this.replyText(context, replyText);
		}
		return "";
	}

	private static Map parse(String str) {
		int jinhao = calcuCharTimes(str, '#');
		if (jinhao == 0) {
			return null;
		}
		int maohao = calcuCharTimes(str, ':');
		if (maohao == 0) {
			return null;
		}
		if (jinhao != maohao) {
			return null;
		}

		Map map = new HashMap();

		String[] strs = str.split("#");
		for (int i = 0; i < strs.length; i++) {
			if (StringUtils.isEmpty(strs[i])) {
				continue;
			}
			String tmpStrs[] = strs[i].split(":");
			map.put(tmpStrs[0], tmpStrs[1]);
		}
		return map;
	}

	private static int calcuCharTimes(String str, char ch) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		}
		int total = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				total += 1;
			}
		}
		return total;
	}

}
