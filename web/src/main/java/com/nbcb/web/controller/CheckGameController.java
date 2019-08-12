package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.nbcb.common.util.DateUtil;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.Server;
import com.nbcb.web.service.CheckRoomGameService;
import com.nbcb.web.service.EnvironmentService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CheckGameController {

	private static final Logger logger = LoggerFactory
			.getLogger(CheckGameController.class);

	@Autowired
	private Server server;

	@Autowired
	private CheckRoomGameService checkRoomGameService;

	@Autowired
	private EnvironmentService environmentService;

	@RequestMapping(value = "checkGame")
	@ResponseBody
	public Response checkGame(HttpServletRequest request) {
		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		try {
			List<Map> list = new ArrayList();
			String roomId = null;
			Room room = server.getRoomByAccount(openid);
			if (room != null) {
				Map body = new HashMap();
				list.add(body);
				body.put("roomInfo", room.getRoomInfo());
				body.put("roomId", room.getId());
				roomId = room.getId();
				body.put("createTime",
						DateUtil.formatDate(new Date(room.getCreateTime())));
				body.put("roomOrder", room.getOrder());
			}

//			List<RoomDaoEntity> listRoom = checkRoomGameService.findUnstartRoomByCreateAccount(openid);
//			if (listRoom != null && listRoom.size() != 0) {
//				for (RoomDaoEntity rde : listRoom) {
//					if(rde.getRoomid().equals(roomId)){
//						continue;
//					}
//					Map body = new HashMap();
//					list.add(body);
//					body.put("roomInfo",
//							JsonUtil.decode(rde.getRoom_info(), HashMap.class));
//					body.put("roomId", rde.getRoomid());
//					body.put("createTime",
//							DateUtil.formatDate(rde.getCreate_time()));
//					body.put("roomOrder", 0);
//				}
//			}
			return ResponseFactory.newResponse("000000", "成功", list);
		} catch (Exception e) {
			logger.error("### error", e);
			return ResponseFactory.newResponse("000001", "error!!");
		}

	}

}
