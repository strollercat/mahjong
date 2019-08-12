package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.server.Server;
import com.nbcb.majiang.fenhua.FenhuaMajiangRoomInfo;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CheckRoomController {

	private static final Logger logger = LoggerFactory
			.getLogger(CheckRoomController.class);

	@Autowired
	private Server server;

	@Autowired
	private WeixinUserDao weixinUserDao;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private EnvironmentService environmentService;

	static class ComparableMap extends HashMap implements Comparable {

		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			Map otherMap = (Map) o;
			int otherScore = (Integer) otherMap.get("score");
			int thisScore = (Integer) this.get("score");
			return otherScore - thisScore;
		}

	}

	private List<ComparableMap> filterUsers(RoomDaoEntity roomDaoEntity,
			List<Map> listUser) {
		List<ComparableMap> listRet = new ArrayList<ComparableMap>(4);

		FenhuaMajiangRoomInfo fhMjRoomInfo = null;
		if (roomDaoEntity != null) {
			try {
				if (roomDaoEntity.getName().equals("fenhuaMajiang")) {
					fhMjRoomInfo = (FenhuaMajiangRoomInfo) JsonUtil.decode(
							roomDaoEntity.getRoom_info(),
							FenhuaMajiangRoomInfo.class);
				}
			} catch (Exception e) {

			}
		}

		for (Map map : listUser) {
			ComparableMap tmpMap = new ComparableMap();
			listRet.add(tmpMap);
			tmpMap.putAll(map);
			int score = (Integer) map.get("score");
			if (fhMjRoomInfo != null && !fhMjRoomInfo.isPinghu()) {
				score -= fhMjRoomInfo.getChongciMoney();
			}
			tmpMap.put("score", score);
			String name = (String) map.get("name");
			WeixinReplyUser wru = new WeixinReplyUser();
			wru.setAppid(environmentService.getAppid());
			wru.setOpenid(name);
			wru = weixinUserDao.selectUserByOpenid(wru);
			if (wru == null || StringUtils.isEmpty(wru.getOpenid())) {
				continue;
			}
			tmpMap.put("name", wru.getNickname());
			tmpMap.put("headimg", wru.getHeadimgurl());
		}
		Collections.sort(listRet);
		return listRet;
	}

	private void fillBody(Map body, String createAccount, Date createTime,
			Date endTime, String roomId, String roomInfo) {
		body.put("createAccount", createAccount);
		body.put("createTime", DateUtil.formatDate(createTime));
		if (endTime != null) {
			body.put("endTime", DateUtil.formatDate(endTime));
			body.put("rangeTime",
					(endTime.getTime() - createTime.getTime()) / 1000 / 60);
		}
		body.put("roomId", roomId);
		Map mapRoomInfo = null;
		try {
			mapRoomInfo = (Map) JsonUtil.decode(roomInfo, HashMap.class);
		} catch (Exception e) {
			logger.error("### error!", e);
		}
		body.put("roomInfo", mapRoomInfo);
		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setAppid(environmentService.getAppid());
		wru.setOpenid(createAccount);
		wru = this.weixinUserDao.selectUserByOpenid(wru);
		if (wru == null) {
			return;
		}
		body.put("createNickName", wru.getNickname());
		body.put("createHeadImg", wru.getHeadimgurl());
	}

	private void fillBody(Map body, String createAccount, Date createTime,
			Date endTime, String roomId, RoomInfo roomInfo) {
		body.put("createAccount", createAccount);
		body.put("createTime", DateUtil.formatDate(createTime));
		if (endTime != null) {
			body.put("endTime", DateUtil.formatDate(endTime));
			body.put("rangeTime",
					(endTime.getTime() - createTime.getTime()) / 1000 / 60);
		}
		body.put("roomId", roomId);
		body.put("roomInfo", roomInfo);
		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setAppid(environmentService.getAppid());
		wru.setOpenid(createAccount);
		wru = this.weixinUserDao.selectUserByOpenid(wru);
		if (wru == null) {
			return;
		}
		body.put("createNickName", wru.getNickname());
		body.put("createHeadImg", wru.getHeadimgurl());
	}

	@RequestMapping(value = "checkRoomDetail")
	@ResponseBody
	public Response checkRoomDetail(String roomId) {
		Room room = server.getRoom(roomId);
		Map body = new HashMap();
		if (room == null) {
			RoomDaoEntity roomDaoEntity = roomDao
					.selectLastRoomByRoomId(roomId);
			if (roomDaoEntity == null) { // 不存在該房間
				return ResponseFactory.newResponse("000001", "不存在该房间");
			} else {
				this.fillBody(body, roomDaoEntity.getCreate_account(),
						roomDaoEntity.getCreate_time(),
						roomDaoEntity.getEnd_time(), roomDaoEntity.getRoomid(),
						roomDaoEntity.getRoom_info());

				if (StringUtils.isEmpty(roomDaoEntity.getUsers())) { // 超時結束
					body.put("status", 0);
				} else { // 正常結束
					body.put("status", 1);
					body.put("endJu", roomDaoEntity.getEnd_ju());
					body.put("uniqueId",roomDaoEntity.getId());
					try {
						List<Map> listUser = (List<Map>) JsonUtil.decode(
								roomDaoEntity.getUsers(), ArrayList.class);
						body.put("users",
								this.filterUsers(roomDaoEntity, listUser));
					} catch (Exception e) {
						logger.error("### error!", e);
					}
				}
			}
		} else { // 正在進行中
			body.put("status", 2);
			this.fillBody(body, room.getCreatePlayer(),
					new Date(room.getCreateTime()), null, room.getId(),
					room.getRoomInfo());
			List<Map> listUser = (List<Map>) room.format().get("users");
			body.put("users", this.filterUsers(null, listUser));
			body.put("order", room.getOrder());
			body.put("totalJu", room.getRoomInfo().getTotalJu());
		}
		return ResponseFactory.newResponse("000000", "success", body);
	}

}
