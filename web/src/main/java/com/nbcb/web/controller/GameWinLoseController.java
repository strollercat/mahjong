package com.nbcb.web.controller;

import java.util.ArrayList;
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
import com.nbcb.common.util.BeanUtil;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.GameWinLoseDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.GameWinLose;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.service.EnvironmentService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GameWinLoseController {

	private static final Logger logger = LoggerFactory
			.getLogger(GameWinLoseController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameWinLoseDao gameWinLoseDao;

	@Autowired
	private AdminService adminService;

	@Autowired
	private GameUserDao gameUserDao;

	@RequestMapping(value = "getListWinLoses")
	@ResponseBody
	public Response getListWinLoses(HttpServletRequest request,
			int currentPage, int rowsPerPage) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		if (!adminService.isSuperAdmin(account)) {
			return ResponseFactory.newResponse("000001", "无权限");
		}

		if (currentPage <= 0 || currentPage > 200) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}
		if (rowsPerPage > 10 || rowsPerPage < 0) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}

		Page page = new Page(currentPage, rowsPerPage);

		List<GameWinLose> list = gameWinLoseDao.selectGameWinLosesByPage(page);
		if (list == null || list.size() == 0) {
			return ResponseFactory.newResponse("000000", "success");
		}
		List<Map> listRet = new ArrayList<Map>();
		for (GameWinLose wl : list) {
			Map map = BeanUtil.bean2Map(wl);
			map.put("gameUserId",
					gameUserDao.selectGameUserByAccount(wl.getOpenid()).getId());
			listRet.add(map);
		}
		return ResponseFactory.newResponse("000000", "success", listRet);
	}

	@RequestMapping(value = "operatorWinLose")
	@ResponseBody
	public Response operatorWinLose(HttpServletRequest request, int gameUserId,
			int winlose, int ratio) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		if (!adminService.isAdministrator(account)) {
			return ResponseFactory.newResponse("000001", "无权限!");
		}
		if (winlose != 1 && winlose != 0 && winlose != 2) {
			return ResponseFactory.newResponse("000001", "参数非法!!");
		}
		if (gameUserId < 0) {
			return ResponseFactory.newResponse("000001", "参数非法!!");
		}
		GameUser gameUser = gameUserDao.selectGameUserById(gameUserId);
		if (gameUser == null) {
			return ResponseFactory.newResponse("000001", "不存在该用户!");
		}

		if (winlose == 2) {
			gameWinLoseDao.deleteGameWinLoseByOpenid(gameUser.getOpenid());
		} else {
			if (ratio <= 0 || ratio > 100) {
				return ResponseFactory.newResponse("000001", "参数非法!!");
			}
			GameWinLose gameWinLose = new GameWinLose();
			gameWinLose.setOpenid(gameUser.getOpenid());
			gameWinLose.setRatio(ratio);
			gameWinLose.setWinlose(String.valueOf(winlose));

			GameWinLose gameWinLoseRet = gameWinLoseDao
					.selectGameWinLoseByOpenid(gameUser.getOpenid());
			if (gameWinLoseRet == null) {
				gameWinLoseDao.insertGameWinLose(gameWinLose);
			} else {
				gameWinLoseDao.updateGameWinLoseByOpenid(gameWinLose);
			}
		}
		return ResponseFactory.newResponse("000000", "成功");
	}

}