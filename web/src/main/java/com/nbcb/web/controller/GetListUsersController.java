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
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetListUsersController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetListUsersController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private AdminService adminService;

	@Autowired
	private WeixinUserService weixinUserService;

	@RequestMapping(value = "getListUsers")
	@ResponseBody
	public Response getListUsers(HttpServletRequest request, String date,
			int currentPage, int rowsPerPage, int scoreDir) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		if (!adminService.isSuperAdmin(account)) {
			return ResponseFactory.newResponse("000001", "无权限");
		}

		if (currentPage < 0 || currentPage > 200) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}
		if (rowsPerPage > 10 || rowsPerPage < 0) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}
		if (scoreDir != 1 && scoreDir != 0) {
			return ResponseFactory.newResponse("000001", "参数非法");
		}
		Page page = new Page(currentPage, rowsPerPage);
		List<GameUser> list = gameUserDao.selectGameUsersByPageAndScoredir(
				page, scoreDir);
		if (list == null || list.size() == 0) {
			return ResponseFactory.newResponse("000000", "success");
		}
		
		List listRet = new ArrayList();
		for (GameUser gu : list) {
			Map map = BeanUtil.bean2Map(gu);
			WeixinReplyUser wru = weixinUserService.getWeixinUserByOpenid(gu
					.getOpenid());
			map.put("image", wru.getHeadimgurl());
			map.put("name", wru.getNickname());
			map.put("gameUserId",
					gameUserDao.selectGameUserByAccount(wru.getOpenid())
							.getId());
			listRet.add(map);
		}
		return ResponseFactory.newResponse("000000", "success", listRet);
	}

}