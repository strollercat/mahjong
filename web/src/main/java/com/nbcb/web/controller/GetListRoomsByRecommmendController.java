package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.GameStaticsService;
import com.nbcb.web.service.RoomDaoEntityTranslateService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetListRoomsByRecommmendController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetListRoomsByRecommmendController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameStaticsService gameStaticsService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private RoomDaoEntityTranslateService roomDaoEntityTranslateService;

	@RequestMapping(value = "getListRoomsByRecommmend")
	@ResponseBody
	public Response getListRoomsByRecommmend(HttpServletRequest request,
			String date, int currentPage, int rowsPerPage) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
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

		GameUser agentGu = gameUserDao.selectGameUserByAccount(account);
		if (agentGu == null) {
			return ResponseFactory.newResponse("000002", "无权限");
		}
		if (!agentGu.isRecommend()) {
			return ResponseFactory.newResponse("000002", "无权限");
		}

		Page page = new Page(currentPage, rowsPerPage);

		List<RoomDaoEntity> list = gameStaticsService
				.selectRoomsByRecommendByTimeAndPage(agentGu.getId(), endTime,
						page);
		if (list == null || list.size() == 0) {
			return ResponseFactory.newResponse("000000", "success");
		}

		List listRet = new ArrayList<RoomDaoEntity>();
		for (RoomDaoEntity rde : list) {
			listRet.add(roomDaoEntityTranslateService
					.translateRoomDaoEntity(rde));
		}
		return ResponseFactory.newResponse("000000", "success", listRet);
	}

}