package com.nbcb.web.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.nbcb.core.io.ClientPusher;
import com.nbcb.core.io.MessageListener;
import com.nbcb.core.io.ResponseFilter;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.Location;
import com.nbcb.core.user.LocationService;
import com.nbcb.core.user.Player;
import com.nbcb.web.service.EnvironmentService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class GetLocationController {

	private static final Logger logger = LoggerFactory
			.getLogger(GetLocationController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private Server server;

	@Autowired
	private PlayerStatQuery playerStatQuery;

	@Autowired
	private MessageListener messageListener;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ResponseFilter responseFilter;

	@Autowired
	private ClientPusher clientPusher;

	@RequestMapping(value = "getLocation")
	@ResponseBody
	public Response getLocation(HttpServletRequest request, double latitude,
			double longitude, double speed, double accuracy) {
		String account = environmentService.getAccount(request);
		if (StringUtils.isEmpty(account)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		logger.info("latitude[" + latitude + "]longitude[" + longitude
				+ "]speed[" + speed + "]accuracy[" + accuracy + "]");

		Player player = server.getPlayer(account);
		if (player != null) {
			Location location = new Location();
			location.setLatitude(latitude);
			location.setLatitude(longitude);
			location.setSpeed(speed);
			location.setAccuracy(accuracy);
			location.setDate(new Date());
			player.setLocation(location);
		}

		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.ROOM) {
			Room room = server.getRoomByAccount(account);
			if (room != null && room.getOrder() == 0) {// 在房间里,且游戏没有开始
//				this.judgePosition(room, player);
			}
		}
		return ResponseFactory.newResponse("000000", "success");
	}

	/**
	 * 
	 * @param room
	 * @param player
	 * @return true 位置相近 false 位置远的
	 */
	private boolean judgePosition(Room room, Player player) {
		if (null == player.getLocation()) {
			return false;
		}
		String message = null;
		for (int i = 0; i < room.getActivePlayers(); i++) {
			Player tmpPlayer = room.getPlayerByIndex(i);
			if (tmpPlayer == player) {
				continue;
			}
			if (tmpPlayer.getLocation() == null) {
				continue;
			}
			double distance = locationService.calculatorDistance(tmpPlayer.getLocation(),
					player.getLocation());
			logger.info("################  distance ["+distance+"]");
			if (locationService.calculatorDistance(tmpPlayer.getLocation(),
					player.getLocation()) < 150) {
				message = messageListener.getPlaceHolderAccount(tmpPlayer
						.getAccount())
						+ "和,,,"
						+ messageListener.getPlaceHolderAccount(player
								.getAccount()) + ",,,距离比较近";
				break;
			}
		}
		if (message != null) {
			for (int i = 0; i < room.getActivePlayers(); i++) {
				Player tmpPlayer = room.getPlayerByIndex(i);
				String translateMessage = messageListener.translateMessage(
						tmpPlayer.getAccount(), message);

				this.sendMessageToPlayer(tmpPlayer,
						this.codeMessageBox(translateMessage.split(",,,")),
						room);
			}
			return true;
		}
		return false;
	}

	private Map codeMessageBox(String[] message) {
		Map mapRet = new HashMap();
		mapRet.put("code", "messagebox");
		mapRet.put("message", message);
		return mapRet;
	}

	private void sendMessageToPlayer(Player toPlayer, Map message, Room room) {
		Map mapInner = new HashMap();
		mapInner.putAll(message);
		mapInner = this.responseFilter.filter(toPlayer, mapInner, room);
		this.clientPusher.push(toPlayer.getAccount(), mapInner);
	}

}