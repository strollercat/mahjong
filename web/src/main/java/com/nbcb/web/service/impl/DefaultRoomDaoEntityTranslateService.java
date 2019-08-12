package com.nbcb.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.DateUtil;
import com.nbcb.common.util.JsonUtil;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.RoomDaoEntityTranslateService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

public class DefaultRoomDaoEntityTranslateService implements
		RoomDaoEntityTranslateService {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultRoomDaoEntityTranslateService.class);

	private WeixinUserService weixinUserService;

	public void setWeixinUserService(WeixinUserService weixinUserService) {
		this.weixinUserService = weixinUserService;
	}

	@Override
	public Map translateRoomDaoEntity(RoomDaoEntity rde) {
		// TODO Auto-generated method stub
		Map map = new HashMap();

		WeixinReplyUser wru = weixinUserService.getWeixinUserByOpenid(rde
				.getCreate_account());
		if (wru != null) {
			map.put("image", wru.getHeadimgurl());
		}

		map.put("createTime", DateUtil.formatDate(rde.getCreate_time()));
		map.put("roomId", rde.getId());
		map.put("roomShowId",rde.getRoomid());
		map.put("recommend",rde.getRecommend());

		String title = "";
		String name = rde.getName();
		if (name.equals("hongzhongMajiang")) {
			title = "红中麻将";
		} else if (name.equals("guangdongMajiang")) {
			title = "广东麻将";
		} else if (name.equals("ningboMajiang")) {
			title = "宁波麻将";
		} else if (name.equals("hangzhouMajiang")) {
			title = "杭州麻将";
		} else if (name.equals("fenhuaMajiang")) {
			title = "奉化麻将";
		} else if (name.equals("tiantaiMajiang")) {
			title = "天台麻将";
		} else if(name.equals("threeWater")){
			title = "闯三关";
		} else if(name.equals("xiangshanMajiang")){
			title = "象山麻将";
		}
		title += (" " + rde.getRoomid());
		title += (" " + rde.getEnd_ju() + '局' + "(共" + rde.getTotal_ju() + "局)");
		map.put("title", title);

		String desc = "";
		if(rde.getUsers()!=null && !rde.getUsers().equals("")){
			try {
				List<Map> listUser = (List<Map>) JsonUtil.decode(rde.getUsers(),
						ArrayList.class);
				if (listUser != null && listUser.size() > 0) {
					for (Map tmpMap : listUser) {
						String openid = (String) tmpMap.get("name");
						int score = (Integer) tmpMap.get("score");
						wru = weixinUserService.getWeixinUserByOpenid(openid);
						if (wru != null) {
							desc += wru.getNickname();
						}
						if (score >= 0) {
							desc += ("+" + score);
						} else {
							desc += score;
						}
						desc += " ";
					}
				}
			} catch (Exception e) {
				logger.error("### error", e);
			}
		}
		map.put("desc", desc);
		return map;
	}

}
