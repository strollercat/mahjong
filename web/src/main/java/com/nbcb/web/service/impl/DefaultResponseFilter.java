package com.nbcb.web.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.io.ResponseFilter;
import com.nbcb.core.room.Room;
import com.nbcb.core.user.Player;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.entity.GameUser;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

public class DefaultResponseFilter implements ResponseFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultResponseFilter.class);

	private GameUserDao gameUserDao;

	private WeixinUserService weixinUserService;

	public void setWeixinUserService(WeixinUserService weixinUserService) {
		this.weixinUserService = weixinUserService;
	}

	public void setGameUserDao(GameUserDao gameUserDao) {
		this.gameUserDao = gameUserDao;
	}

	protected void putUrlAndNameToUser(List<Map> users) {
		for (Map map : users) {
			this.putUrlAndNameToMap(map);
		}
	}

	protected void putUrlAndNameToMap(Map map) {
		String name = (String) map.get("name");
		WeixinReplyUser wru = weixinUserService.getWeixinUserByOpenid(name);
		if (wru == null) {
			return;
		}
		map.put("url", wru.getHeadimgurl());
		map.put("name", wru.getNickname());
		map.put("openid", name);
	}

	protected void putFightInfoToUser(List<Map> users) {
		for (Map map : users) {
			this.putFightInfoToMap(map);
		}
	}

	protected void putFightInfoToMap(Map map) {
		String name = (String) map.get("openid");
		logger.info("### name[" + name + "]");
		GameUser gameUser = gameUserDao.selectGameUserByAccount(name);
		Map fightInfo = new HashMap();
		if (gameUser == null) {
			logger.info("### gameUser is null!");
			return;
		}
		fightInfo.put("win", gameUser.getWin());
		fightInfo.put("lose", gameUser.getLose());
		fightInfo.put("ping", gameUser.getPing());
		fightInfo.put("score", gameUser.getScore());
		fightInfo.put("money", gameUser.getMoney());
		map.put("fightInfo", fightInfo);
		map.remove("openid");
	}

	private int[] getAllSameArr(int number, int len) {
		int ret[] = new int[len];
		for (int i = 0; i < len; i++) {
			ret[i] = number;
		}
		return ret;
	}

	private int[] copyArry(int[] ints) {
		int ret[] = new int[ints.length];
		for (int i = 0; i < ints.length; i++) {
			ret[i] = ints[i];
		}
		return ret;
	}

	protected List<Map> filterCards(int dir, List<Map> cards) {

		List<Map> listRet = new ArrayList<Map>();
		for (int i = 0; i < cards.size(); i++) {
			Map map = new HashMap();
			Map card = cards.get(i);
			map.put("o", card.get("o"));
			map.put("i", this.copyArry((int[]) card.get("i")));
			map.put("m", card.get("m"));
			map.put("dir", card.get("dir"));
			map.put("candite", card.get("candite"));
			map.put("special", card.get("special"));
			listRet.add(map);
		}
		for (Map map : listRet) {
			int tmpDir = ((Integer) map.get("dir")).intValue();
			if (tmpDir != dir) {
				map.put("i",
						this.getAllSameArr(-1, Array.getLength(map.get("i"))));
				map.put("candite", null);
				map.put("special",null);
			}
		}
		return listRet;
	}

	public Map filter(Player player, Map map, Room room) {
		// TODO Auto-generated method stub
		String code = (String) map.get("code");
		Map mapInner = new HashMap();
		mapInner.putAll(map);
		if (code.equals("gameNotice")) {
			mapInner.put("author", player.getPlayerOrder());
			this.putUrlAndNameToUser((List<Map>) mapInner.get("users"));
			this.putFightInfoToUser((List<Map>) mapInner.get("users"));
			mapInner.put(
					"cards",
					this.filterCards(player.getPlayerOrder(),
							(List<Map>) map.get("cards")));
		} else if (code.equals("roomNotice")) {
			logger.info("### roomNotice " + mapInner);
			mapInner.put("author", player.getPlayerOrder());
			this.putUrlAndNameToUser((List<Map>) mapInner.get("users"));
			this.putFightInfoToUser((List<Map>) mapInner.get("users"));
			logger.info("### roomNotice " + mapInner);
		} else if (code.equals("enterRoom")) {
			this.putUrlAndNameToMap(mapInner);
			this.putFightInfoToMap(mapInner);
		} else if (code.equals("gameStart")) {
			mapInner.put("cards", this.filterCards(player.getPlayerOrder(),
					(List<Map>) mapInner.get("cards")));
		} else if (code.equals("mo")) {
			if (player.getPlayerOrder() != ((Integer) mapInner.get("dir"))
					.intValue()) {
				mapInner.put("card", -1);
			}
		} else if (code.equals("no")) {
			if (player.getPlayerOrder() != ((Integer) mapInner.get("dir"))
					.intValue()) {
				mapInner = null;
			}
		} else if (code.equals("destroy")) {
			String reason = (String) mapInner.get("reason");
			mapInner.put("reason", this.translateReason(reason));
		}
		return mapInner;
	}

	private String translateReason(String reason) {
		int start = reason.indexOf('[');
		if (start < 0) {
			return reason;
		}
		int end = reason.indexOf(']');
		if (end < 0) {
			return reason;
		}
		if (start >= end) {
			return reason;
		}

		String openid = reason.substring(start + 1, end);

		if (end + 1 >= reason.length()) {
			return reason;
		}

		reason = reason.substring(end + 1, reason.length());

		try {
			if (openid == null || openid.equals("")) {
				return reason;
			}
			String openids[] = openid.split(",");
			String names = "";
			for (String str : openids) {
				WeixinReplyUser wru = weixinUserService
						.getWeixinUserByOpenid(str);
				if (wru == null) {
					continue;
				}
				names += (wru.getNickname() + "和");
			}
			return names.substring(0, names.length() - 1) + reason;
		} catch (Exception e) {
			logger.error("### error", e);
			return reason;
		}

	}

}
