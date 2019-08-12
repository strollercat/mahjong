package com.nbcb.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.common.service.AdminService;
import com.nbcb.core.game.Game;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.majiang.ningbo.NingboMajiangRoomInfo;
import com.nbcb.poker.action.PokerAction;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.web.dao.GameMoneyDetailDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.OnlineUserRegistry;
import com.nbcb.weixinapi.WeixinApiMedia;
import com.nbcb.weixinapi.WeixinApiTemplate;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate;
import com.nbcb.weixinapi.entity.WeixinSendPushTemplate.WeixinSendPushTemplateItem;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class DemoController {

	private static final Logger logger = LoggerFactory
			.getLogger(DemoController.class);

	@Autowired
	private Server server;

	@Autowired
	private UserActionApi userActionApi;

	@Autowired
	private WeixinApiMedia weixinApiMedia;

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private WeixinCachedApiBase weixinCachedApiBase;

	@Autowired
	private PlayerStatQuery playerStatQuery;

	@Autowired
	private OnlineUserRegistry onlineUserRegistry;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private GameMoneyDetailDao gameMoneyDetailDao;

	@Autowired
	private AdminService adminService;
	
	
	@Autowired
	private WeixinApiTemplate weixinApiTemplate;

	@RequestMapping(value = "bootDemo", method = RequestMethod.GET)
	@ResponseBody
	public Response bootDemo() {
		String player0 = "oVcAKt6ue0HsrS-XK-Az15TvrkVc";
		String player1 = "oVcAKt0EFj-Pmq8VBp4_usWEiz3k";
		String player2 = "player2";
		String player3 = "player3";
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		} else if ("test".equals(this.environmentService.getEnvironment())) {
			player0 = "oVcAKt6ue0HsrS-XK-Az15TvrkVc";
			player1 = "oVcAKt0EFj-Pmq8VBp4_usWEiz3k";
			player2 = "oVcAKtz6pG91kXyN3XHkX9FRcolo";
			player3 = "oVcAKt9S5WSgtq-vZOIUy2uktwzw";
		} else {
			player0 = "player0";
			player1 = "player1";
			player2 = "player2";
			player3 = "player3";
		}
		NingboMajiangRoomInfo ri = new NingboMajiangRoomInfo();
		ri.setBaida(7);
		ri.setStartFan(5);
		ri.setHunpengqing(true);
		ri.setPlayerNum(4);
		ri.setJinhua(1);
		ri.setYehua(0);
		ri.setName("ningboMajiang");
		ri.setTotalJu(4);
		Room room = server.createRoom(player0, ri);
		server.enterRoom(player1, room.getId());
		server.enterRoom(player2, room.getId());
		server.enterRoom(player3, room.getId());
		server.playerReady(player0, room.getId());
		server.playerReady(player1, room.getId());
		server.playerReady(player2, room.getId());
		server.playerReady(player3, room.getId());

		// RoomInfo ri1 = new RoomInfo();
		// ri1.setName("fenhuaMajiang");
		// ri1.setPlayerNum(4);
		// ri1.setTotalJu(3);
		// Room room = server.createRoom(player0,ri1);
		// server.enterRoom(player1, room.getId());
		// server.enterRoom(player2, room.getId());
		// server.enterRoom(player3, room.getId());

		// TiantaiMajiangRoomInfo ri1 = new TiantaiMajiangRoomInfo();
		// ri1.setName("tiantaiMajiang");
		// ri1.setPlayerNum(3);
		// ri1.setTotalJu(3);
		// ri1.setHasBaida(true);
		// ri1.setMaxFan(300);
		// Room room = server.createRoom(player0,ri1);
		// server.enterRoom(player1, room.getId());
		// server.enterRoom(player2, room.getId());

		// RoomInfo ri1 = new RoomInfo();
		// ri1.setName("hangzhouMajiang");
		// ri1.setPlayerNum(4);
		// ri1.setTotalJu(3);
		// Room room = server.createRoom(player0, ri1);
		// server.enterRoom(player1, room.getId());
		// server.enterRoom(player2, room.getId());
		// server.enterRoom(player3, room.getId());

		return ResponseFactory.newResponse("000000", "成功", room.format());
	}

	@RequestMapping(value = "sendMessage", method = RequestMethod.GET)
	@ResponseBody
	public Response sendMessage(String account) {
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		}
		try {
			WebSocketSession session = onlineUserRegistry.get(account);
			if (session == null) {
				logger.info("### session is null");
				return ResponseFactory.newResponse("000001", "fail");
			}
			TextMessage message = new TextMessage("fuck".getBytes("utf-8"));
			session.sendMessage(message);
			return ResponseFactory.newResponse("000000", "成功");
		} catch (Exception e) {
			logger.error("#### errro", e);
			return ResponseFactory.newResponse("000001", "fail");
		}

	}

	@RequestMapping(value = "heartBreak", method = RequestMethod.POST)
	@ResponseBody
	public Response heartBreak(String url) {
		logger.error("#### heart break;url", url);
		return ResponseFactory.newResponse("000000", "success");
	}

	@RequestMapping(value = "pokerDemo", method = RequestMethod.GET)
	@ResponseBody
	public Response pokerDemo() {
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		}
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setName("shisanshui");
		roomInfo.setPlayerNum(4);
		roomInfo.setTotalJu(4);
		Room room = server.createRoom("player0", roomInfo);
		server.enterRoom("player1", room.getId());
		server.enterRoom("player2", room.getId());
		server.enterRoom("player3", room.getId());
		server.playerReady("player0", room.getId());
		server.playerReady("player1", room.getId());
		server.playerReady("player2", room.getId());
		server.playerReady("player3", room.getId());
		return ResponseFactory.newResponse("000000", "success");
	}

//	@RequestMapping(value = "pokerAction", method = RequestMethod.GET)
//	@ResponseBody
//	public Response pokerDemo(String account, int type, String cards) {
//
//		if ("prod".equals(this.environmentService.getEnvironment())) {
//			return ResponseFactory.newResponse("000001", "illegal");
//		}
//
//		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
//		if (ps != PlayerStat.GAME) {
//			return ResponseFactory.newResponse("000001", "游戏未开始!");
//		}
//		Game game = server.getRoomByAccount(account).currentGame();
//		if (type == PokerAction.DA) {
//			PokerCards pcs = new PokerCards();
//			String strCards[] = cards.split(",");
//
//			for (String str : strCards) {
//				int number = Integer.parseInt(str);
//				pcs.addTailCard(game.getAllCards().findCardByNumber(number));
//			}
//			PokerAction pa = new PokerAction(server.getPlayer(account),
//					PokerAction.DA, pcs, true);
//			game.nextAction(pa);
//		} else if (type == PokerAction.GUO) {
//			PokerAction pa = new PokerAction(server.getPlayer(account),
//					PokerAction.GUO, null, true);
//			game.nextAction(pa);
//		}
//		return ResponseFactory.newResponse("000000", "success");
//	}

	@RequestMapping(value = "dismiss", method = RequestMethod.GET)
	@ResponseBody
	public Response dismiss(String account, boolean dismiss) {
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		}

		PlayerStat ps = playerStatQuery.queryPlayerStat(account);
		if (ps == PlayerStat.DOOR) {
			return ResponseFactory.newResponse("000001", "游戏未开始!");
		}
		Room room = server.getRoomByAccount(account);
		server.requestDismissRoom(account, dismiss, room.getId());
		return ResponseFactory.newResponse("000000", "success");
	}

	@RequestMapping(value = "adminTest", method = RequestMethod.GET)
	@ResponseBody
	public Response adminTest(HttpServletRequest request) {
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		}
		return ResponseFactory.newResponse("000000", "success", adminService
				.isSuperAdmin(this.environmentService.getAccount(request)));
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	@ResponseBody
	public Response test(HttpServletRequest request) {
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		}
		int recommend = 1;
		Page page = new Page(1, 1);
		return ResponseFactory.newResponse("000000", "success", roomDao
				.selectRoomsByRecommendByTimeAndPage(recommend, new Date(),
						page));

	}
	
	@RequestMapping(value = "templateTest", method = RequestMethod.GET)
	@ResponseBody
	public Response templateTest(HttpServletRequest request) {
		if ("prod".equals(this.environmentService.getEnvironment())) {
			return ResponseFactory.newResponse("000001", "illegal");
		}
		String templateId = "INFe3FIME2oUn38OBirhOsSuXbWTnSv7SNRFth5cnAE";
		String at = "8_NVJnOxyZr0F6oiaMA5y-AI4xFvjzD_vHvXhiuO0Ve-bZHaSzltE34jJMh1rgafRRgMgcgatuh7pEVkHyxiJJoAXl9GEHUciCaTY620ZLAo6CvB7oNeB27eETe8TCNtkJffLX_W8jw9ntoS_cANWhAEAMZI";
		String toUser = "o3WiI1W_-C0ZRKOA87_juQsrSAO4";
		
		WeixinSendPushTemplate wst = new WeixinSendPushTemplate();
		wst.setAccessToken(at);
		wst.setTouser(toUser);
		wst.setUrl("");
		wst.setTemplate_id(templateId);
		
		WeixinSendPushTemplateItem item = new WeixinSendPushTemplateItem();
		item.setValue("first");
		wst.addItem("first", item);
		
		item = new WeixinSendPushTemplateItem();
		item.setValue("accountType");
		wst.addItem("accountType", item);
		
		item = new WeixinSendPushTemplateItem();
		item.setValue("account");
		wst.addItem("account", item);
		
		item = new WeixinSendPushTemplateItem();
		item.setValue("amount");
		wst.addItem("amount", item);
		
		item = new WeixinSendPushTemplateItem();
		item.setValue("result");
		wst.addItem("result", item);
		
		item = new WeixinSendPushTemplateItem();
		item.setValue("remark");
		wst.addItem("remark", item);
		
		
	
		try {
			weixinApiTemplate.template(wst);
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseFactory.newResponse("000000", "success");
		
	}
	
	
}
