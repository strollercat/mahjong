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
import com.nbcb.common.service.AdminService;
import com.nbcb.common.util.DateUtil;
import com.nbcb.common.util.JsonUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.RoomDaoEntityTranslateService;
import com.nbcb.web.service.RoomRecordService;
import com.nbcb.web.service.WeixinUserService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CheckRecordController {

	private static final Logger logger = LoggerFactory
			.getLogger(CheckRecordController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private RoomRecordService roomRecordService;

	@Autowired
	private WeixinUserService weixinUserService;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private AdminService adminService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private RoomDaoEntityTranslateService roomDaoEntityTranslateService;

	private boolean isPlayerIn(String account, RoomDaoEntity rde) {
		try {
			List<Map> listUsers = (List) JsonUtil.decode(rde.getUsers(),
					ArrayList.class);
			for (int i = 0; i < listUsers.size(); i++) {
				Map mapUser = listUsers.get(i);
				String name = (String) mapUser.get("name");
				if (account.equals(name)) {
					logger.info("### playerIn");
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error("### error", e);
			return false;
		}
	}

	private boolean isRecommend(String account, RoomDaoEntity rde) {
		try {
			int recommendId = Integer.parseInt(rde.getRecommend());
			GameUser gu = gameUserDao.selectGameUserById(recommendId);
			if (gu == null) {
				logger.info("### recommend[" + recommendId + "] is null");
				return false;
			}
			if (!gu.getOpenid().equals(account)) {
				logger.info("### account[" + account + "] is not the room["
						+ rde.getRoomid() + "] recommend");
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("### error,", e);
			return false;
		}

	}

	private boolean hasRight(String account, RoomDaoEntity rde) {
		if (adminService.isAdministrator(account)) {
			// logger.info("### isAdmin");
			return true;
		}
		return this.isPlayerIn(account, rde) || this.isRecommend(account, rde);
	}

	/**
	 * 查询录像信息
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 */
	@RequestMapping(value = "checkRecord")
	@ResponseBody
	public Response checkRecord(HttpServletRequest request, int roomId) {
		String account = this.environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "fail");
		}

		if (roomId <= 0) {
			return ResponseFactory.newResponse("000001", "fail");
		}

		RoomDaoEntity rde = this.roomDao.selectRoomById(roomId);
		if (rde == null) {
			return ResponseFactory.newResponse("000001", "no data");
		}

//		if (!this.hasRight(account, rde)) {
//			return ResponseFactory.newResponse("000001", "没有权限查询录像!");
//		}
		logger.info("### account[" + account
				+ "] start to check record roomId [" + roomId + "]");
		return ResponseFactory.newResponse("000000", "success",
				roomRecordService.queryRecordDataByRoomid(account, roomId));
	}

	@RequestMapping(value = "checkLastRoomResult")
	@ResponseBody
	public Response checkLastRoomResult(HttpServletRequest request) {
		String account = this.environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "fail");
		}
		RoomDaoEntity roomDaoEntity = roomRecordService
				.checkLastRoomResult(account);
		if (roomDaoEntity == null) {
			return ResponseFactory.newResponse("000001", "no room");
		}
		Map map = roomDaoEntityTranslateService
				.translateRoomDaoEntity(roomDaoEntity);
		if (map == null) {
			return ResponseFactory.newResponse("000001", "no room");
		}
		return ResponseFactory.newResponse("000000", "success", map);
	}

	@RequestMapping(value = "checkRoomResultsByAccountAndPage")
	@ResponseBody
	public Response checkRoomResultsByAccountAndPage(
			HttpServletRequest request, String date, int currentPage,
			int rowsPerPage) {
		String account = this.environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "fail");
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

		List<RoomDaoEntity> list = roomRecordService
				.checkRoomResultsByTimeAndPageAndAccount(account, endTime, page);

		if (list == null) {
			return ResponseFactory.newResponse("000000", "success");
		}

		List<Map> listRet = new ArrayList<Map>();
		for (RoomDaoEntity rde : list) {
			listRet.add(roomDaoEntityTranslateService
					.translateRoomDaoEntity(rde));
		}
		return ResponseFactory.newResponse("000000", "success", listRet);
	}

}
