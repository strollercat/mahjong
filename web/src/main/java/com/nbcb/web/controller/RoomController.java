package com.nbcb.web.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nbcb.common.helper.Response;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.Server;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.MoneyStrategyService;
import com.nbcb.web.service.WeixinLoginService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.entity.WeixinUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class RoomController {

	private static final Logger logger = LoggerFactory
			.getLogger(RoomController.class);

	@Autowired
	private UserActionApi userActionApi;

	@Autowired
	private WeixinLoginService weixinLoginService;

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private Server server;

	@Autowired
	private MoneyStrategyService moneyStrategy;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private WeixinUserService weixinUserService;

	@RequestMapping(value = "home", method = RequestMethod.GET)
	public ModelAndView home(HttpSession httpSession,
			HttpServletRequest request, String code, String recommend,
			String view) {

		if (StringUtils.isEmpty(code)) {
			return new ModelAndView("redirect:"
					+ this.environmentService.getCodedUrl("home.do?recommend="
							+ recommend));
		}

		logger.info("### enter into the home code [" + code + "]recommend["
				+ recommend + "]view[" + view + "]ip["
				+ weixinUserService.getOriginIp(request) + "]");
		if (!environmentService.getEnvironment().equals("dev")) {
			if (StringUtils.isEmpty(code)) {
				return new ModelAndView(
						"redirect:"
								+ this.environmentService
										.getCodedUrl("home.do?recommend="
												+ recommend + "&view=" + view));
			}
			WeixinUser wu = weixinLoginService.login(code, httpSession);
			return new ModelAndView("redirect:"
					+ "/majiang/home.html?recommend=" + recommend + "&view="
					+ view);
		} else {
			WeixinUser wu = weixinLoginService.login(code, httpSession);
			return new ModelAndView("redirect:"
					+ "/grunt/build/home.html?code=" + code + "&recommend="
					+ recommend);
		}
	}

	private String getHomeUrlWithMsg(String msg, String recommend, String code) {
		msg = URLEncoder.encode(msg);
		if (!environmentService.getEnvironment().equals("dev")) {
			return "redirect:/majiang/home.html?msg=" + msg + "&recommend="
					+ recommend;
		} else {
			return "redirect:/grunt/build/home.html?msg=" + msg + "&recommend="
					+ recommend + "&code=" + code;
		}
	}

	private String getGameUrl(String roomId, String recommend, String code) {
		Room room = server.getRoom(roomId);
		if(room == null){
			return null;
		}
		String htmlName;
		if(room.getRoomInfo().getName().equals("threeWater")){
			htmlName = "pokergame.html";
		}else{
			htmlName = "game.html";
		}
		logger.info("### getGameUrl  htmlName["+htmlName+"]roomName["+room.getRoomInfo().getName()+"]");
		if (!environmentService.getEnvironment().equals("dev")) {
			return "redirect:/majiang/"+htmlName+"?roomId=" + roomId
					+ "&recommend=" + recommend;
		} else {
			return "redirect:/grunt/build/"+htmlName+"?roomId=" + roomId
					+ "&recommend=" + recommend + "&code=" + code;
		}
	}

	private String getHomeUrlWithRoomId(String roomId, String recommend,
			String code) {
		if (!environmentService.getEnvironment().equals("dev")) {
			return "redirect:/majiang/home.html?roomId=" + roomId
					+ "&recommend=" + recommend;
		} else {
			return "redirect:/grunt/build/home.html?roomId=" + roomId
					+ "&recommend=" + recommend + "&code=" + code;
		}
	}

	@RequestMapping(value = "enterRoom", method = RequestMethod.GET)
	public ModelAndView enterRoom(HttpSession httpSession,
			HttpServletRequest request, String code, String roomId,
			String recommend) {
		logger.info("### enter into enterRoom code[" + code + "]roomId["
				+ roomId + "]recommend[" + recommend + "]");
		
		WeixinUser wu = weixinLoginService.isLogin(httpSession);
		if(wu == null || StringUtils.isEmpty(wu.getOpenid())){
			if (StringUtils.isEmpty(code)) {
				logger.info("### code empty");
				return new ModelAndView(
						"redirect:"
								+ this.environmentService
										.getCodedUrl("enterRoom.do?roomId="
												+ roomId + "&recommend="
												+ recommend));
			}
			wu = weixinLoginService.login(code, httpSession);
			if (wu == null || StringUtils.isEmpty(wu.getOpenid())) {
				logger.error("############################################ login fail");
				return new ModelAndView(this.getHomeUrlWithMsg("亲，登录失败，请重新登录",
						recommend, code));
			}
		}
		
		
		if (StringUtils.isEmpty(roomId)) {
			logger.info("### roomId empty");
			return new ModelAndView(this.getHomeUrlWithMsg("亲,房间不存在哦",
					recommend, code));
		}
		Room room = server.getRoom(roomId);
		if (room == null) {
			logger.info("### room null");
			if (null == roomDao.selectLastRoomByRoomId(roomId)) {
				return new ModelAndView(this.getHomeUrlWithMsg("亲,房间不存在哦",
						recommend, code));
			} else {
				return new ModelAndView(this.getHomeUrlWithRoomId(roomId,
						recommend, code));
			}
		}

		if (room.getPlayerByAccount(wu.getOpenid()) != null) { // 本来就在房间里，那就让它进去呗
			return new ModelAndView(this.getGameUrl(roomId, recommend, code));
		}

//		if (!moneyStrategy.enoughMoney(wu.getOpenid(), room.getRoomInfo())) {
//			logger.info("### money not enough");
//			String redirectUrl = this.getHomeUrlWithMsg("亲,您的钻石不够了", recommend,
//					code);
//			redirectUrl += ("&view=chongzhi");
//			return new ModelAndView(redirectUrl);
//		}

		Response response = userActionApi.enterRoom(wu.getOpenid(), roomId);
		if (response.getErrCode().equals("000004")) {
			String redirectUrl = this.getHomeUrlWithMsg(response.getErrMsg(),
					recommend, code);
			redirectUrl += ("&view=faxian");
			return new ModelAndView(redirectUrl);
		}
		if (response.getErrCode().equals("000001")) {
			String redirectUrl = this.getHomeUrlWithRoomId(room.getId(), recommend, code);
			return new ModelAndView(redirectUrl);
		}
		if (!response.getErrCode().equals("000000")) {
			return new ModelAndView(this.getHomeUrlWithMsg(
					response.getErrMsg(), recommend, code));
		} else {
			// room.getPlayerByAccount(wu.getOpenid()).setOriginIp(
			// weixinUserService.getOriginIp(request));
			return new ModelAndView(this.getGameUrl(roomId, recommend, code));
		}

	}

	private boolean isSameIp(WeixinUser wu, HttpServletRequest request,
			Room room) {
		String originIp = weixinUserService.getOriginIp(request);
		for (int i = 0; i < room.getActivePlayers(); i++) {
			if (wu.getOpenid().equals(room.getPlayerByIndex(i).getAccount())) {
				continue;
			}
			String ip = room.getPlayerByIndex(i).getOriginIp();
			if (originIp.equals(ip)) {
				return true;
			}
		}
		return false;
	}
}
