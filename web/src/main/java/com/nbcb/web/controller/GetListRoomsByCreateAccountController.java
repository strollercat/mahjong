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
import com.nbcb.common.util.DateUtil;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.Server;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.RoomDaoEntityTranslateService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetListRoomsByCreateAccountController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetListRoomsByCreateAccountController.class);

	@Autowired
	private Server server;

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private RoomDaoEntityTranslateService roomDaoEntityTranslateService;

	@Autowired
	private RoomDao roomDao;

	@RequestMapping(value = "getListRoomsByCreateAccount")
	@ResponseBody
	public Response getListRoomsByCreateAccount(HttpServletRequest request,
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

		Page page = new Page(currentPage, rowsPerPage);

		List<RoomDaoEntity> list = roomDao
				.selectRoomsByCreateAccountByTimeAndPage(account, endTime, page);
		if (list == null || list.size() == 0) {
			return ResponseFactory.newResponse("000000", "success");
		}

		List listRet = new ArrayList<RoomDaoEntity>();
		for (RoomDaoEntity rde : list) {
			Map tmpMap = roomDaoEntityTranslateService
					.translateRoomDaoEntity(rde);
			String desc ="消耗钻石"+rde.getCost_money()+"颗 "+this.getState(rde);
			tmpMap.put("desc", desc);
			listRet.add(tmpMap);
		}
		return ResponseFactory.newResponse("000000", "success", listRet);
	}
	
	private String getState(RoomDaoEntity rde){
		if(rde.getEnd_time() !=null){
			return "已结束";
		}
		if(rde.getCost_money()!=0){
			return "进行中";
		}
		return "未开始";
	}

}