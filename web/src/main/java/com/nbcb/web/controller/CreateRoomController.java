package com.nbcb.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.io.UserActionApi;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.server.PlayerStat;
import com.nbcb.core.server.PlayerStatQuery;
import com.nbcb.core.server.Server;
import com.nbcb.majiang.fenhua.FenhuaMajiangRoomInfo;
import com.nbcb.majiang.guangdong.GuangdongMajiangRoomInfo;
import com.nbcb.majiang.hangzhou.HangzhouMajiangRoomInfo;
import com.nbcb.majiang.hongzhong.HongzhongMajiangRoomInfo;
import com.nbcb.majiang.ningbo.NingboMajiangRoomInfo;
import com.nbcb.majiang.tiantai.TiantaiMajiangRoomInfo;
import com.nbcb.web.dao.GameUserDao;
import com.nbcb.web.dao.RoomDao;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.MoneyStrategyService;
import com.nbcb.web.service.WeixinUserService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CreateRoomController {

	private static final Logger logger = LoggerFactory
			.getLogger(CreateRoomController.class);

	@Autowired
	private UserActionApi userActionApi;

	@Autowired
	private PlayerStatQuery playerStatQuery;

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private MoneyStrategyService moneyStrategyService;

	@Autowired
	private Server server;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private GameUserDao gameUserDao;

	@Autowired
	private WeixinUserService weixinUserService;

	private boolean str1Or2(String str) {
		if ("1".equals(str) || "2".equals(str)) {
			return true;
		}
		return false;
	}

	private boolean translateString(String str) {
		if ("1".equals(str)) {
			return true;
		}
		return false;
	}

	private RoomInfo createHangzhouMajiangRoom(HttpServletRequest request,
			int ju) {
		try {
			int ren = Integer.parseInt(request.getParameter("ren"));
			if (ren != 4 && ren != 3) {
				return null;
			}
			String caishen = request.getParameter("caishen");
			String laoshu = request.getParameter("laoshu");
			String santan = request.getParameter("santan");
			String hufa = request.getParameter("hufa");
			boolean haoqi = Boolean.parseBoolean(request.getParameter("haoqi"));
			boolean kaoxiang = Boolean.parseBoolean(request
					.getParameter("kaoxiang"));
			boolean pengtan = Boolean.parseBoolean(request
					.getParameter("pengtan"));
			if (!this.str1Or2(caishen)) {
				return null;
			}
			if (!this.str1Or2(laoshu)) {
				return null;
			}
			if (!this.str1Or2(santan)) {
				return null;
			}
			if (!this.str1Or2(hufa)) {
				return null;
			}

			HangzhouMajiangRoomInfo roomInfo = new HangzhouMajiangRoomInfo();
			roomInfo.setName("hangzhouMajiang");
			roomInfo.setPlayerNum(ren);
			roomInfo.setTotalJu(ju);

			roomInfo.setBaiBanBaida(this.translateString(caishen));
			roomInfo.setBaidaHaoqi(haoqi);
			roomInfo.setBaidaiKaoxiang(kaoxiang);
			roomInfo.setPengTan(pengtan);
			roomInfo.setSanLao(this.translateString(laoshu));
			roomInfo.setSanTan(this.translateString(santan));
			roomInfo.setZimoHu(this.translateString(hufa));

			return roomInfo;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createFenhuaMajiangRoom(HttpServletRequest request, int ju) {
		try {
			String type = request.getParameter("type");
			int ren = Integer.parseInt(request.getParameter("ren"));
			if (ren != 4 && ren != 3) {
				return null;
			}
			boolean pinghu = true;
			if (type.equals("冲刺")) {
				pinghu = false;
				ju = 4;
			}

			FenhuaMajiangRoomInfo roomInfo = new FenhuaMajiangRoomInfo();
			roomInfo.setName("fenhuaMajiang");
			roomInfo.setPinghu(pinghu);
			roomInfo.setChongciMoney(200);
			roomInfo.setPlayerNum(ren);
			roomInfo.setTotalJu(ju);
			return roomInfo;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createTiantaiMajiangRoom(HttpServletRequest request, int ju) {
		try {
			boolean hasBaida = Boolean.parseBoolean(request
					.getParameter("hasBaida"));
			int hushu = Integer.parseInt(request.getParameter("hushu"));
			logger.info("### createTiantaiMajiagn hasBaida[" + hasBaida
					+ "]hushu[" + hushu + "]ju[" + ju + "]");
			TiantaiMajiangRoomInfo roomInfo = new TiantaiMajiangRoomInfo();
			roomInfo.setName("tiantaiMajiang");
			roomInfo.setPlayerNum(3);
			roomInfo.setTotalJu(ju);
			roomInfo.setHasBaida(hasBaida);
			roomInfo.setMaxFan(hushu);
			return roomInfo;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createNingboMajiangRoom(HttpServletRequest request, int ju) {
		try {

			int baida = Integer.parseInt(request.getParameter("baida"));
			if (baida != 4 && baida != 7 && baida != 3) {
				return null;
			}
			int ren = Integer.parseInt(request.getParameter("ren"));
			if (ren != 4 && ren != 3 && ren != 2) {
				return null;
			}
			int tai = Integer.parseInt(request.getParameter("tai"));
			if (tai != 4 && tai != 3 && tai != 5) {
				return null;
			}
			int jinhua = Integer.parseInt(request.getParameter("jinhua"));
			if (jinhua != 1 && jinhua != 2) {
				return null;
			}
			int yehua = Integer.parseInt(request.getParameter("yehua"));
			if (yehua != 1 && yehua != 0) {
				return null;
			}
			if (jinhua == 2 && yehua != 1) {
				return null;
			}
			if (jinhua == 1 && yehua != 0) {
				return null;
			}
			NingboMajiangRoomInfo room = new NingboMajiangRoomInfo();
			room.setName("ningboMajiang");
			room.setTotalJu(ju);
			room.setPlayerNum(ren);
			room.setStartFan(tai);
			room.setBaida(baida);
			room.setJinhua(jinhua);
			room.setYehua(yehua);
			room.setHunpengqing(false);
			if (baida == 7) {
				room.setHunpengqing(true);
			}
			return room;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createHongzhongMajiangRoom(HttpServletRequest request,
			int ju) {
		try {
			int diScore = Integer.parseInt(request.getParameter("diScore"));
			logger.info("### diScore[" + diScore + "]");
			if (diScore < 1 || diScore > 100) {
				return null;
			}
			HongzhongMajiangRoomInfo room = new HongzhongMajiangRoomInfo();
			room.setName("hongzhongMajiang");
			room.setTotalJu(ju);
			room.setPlayerNum(4);
			room.setDiScore(diScore);
			return room;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createXiangshanMajiangRoom(HttpServletRequest request,
			int ju) {
		try {

			int ren = Integer.parseInt(request.getParameter("ren"));
			if (ren != 4 && ren != 3 && ren != 2) {
				return null;
			}
			RoomInfo room = new RoomInfo();
			room.setName("xiangshanMajiang");
			room.setTotalJu(ju);
			room.setPlayerNum(ren);
			return room;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createGuangdongMajiangRoom(HttpServletRequest request,
			int ju) {
		try {
			int diScore = Integer.parseInt(request.getParameter("diScore"));
			logger.info("### diScore[" + diScore + "]");
			if (diScore < 1 || diScore > 100) {
				return null;
			}
			GuangdongMajiangRoomInfo room = new GuangdongMajiangRoomInfo();
			room.setName("guangdongMajiang");
			room.setTotalJu(ju);
			room.setPlayerNum(4);
			room.setDiScore(diScore);
			return room;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	private RoomInfo createThreeWaterRoom(HttpServletRequest request, int ju) {
		try {
			System.out.println("createThreeWaterRoom");
			int ren = Integer.parseInt(request.getParameter("ren"));
			System.out.println(ren);
			if (ren != 4 && ren != 3 && ren != 2) {
				return null;
			}

			RoomInfo room = new RoomInfo();
			room.setName("threeWater");
			room.setTotalJu(ju);
			room.setPlayerNum(ren);
			return room;
		} catch (Exception e) {
			logger.error("### creatRoomError!", e);
			return null;
		}
	}

	@RequestMapping(value = "createRoom")
	@ResponseBody
	public Response createRoom(HttpServletRequest request, String mjType, int ju) {

		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "unlogin");
		}
		logger.info("### create Room openid[" + openid + "]mjType[" + mjType
				+ "]ju[" + ju + "]");
		PlayerStat ps = playerStatQuery.queryPlayerStat(openid);
		if (ps != PlayerStat.DOOR) {
			return ResponseFactory.newResponse("000009", "亲，您正在游戏中！");
		}

		if (!(ju == 4 || ju == 8 || ju == 12 || ju == 16)) {
			return ResponseFactory.newResponse("000002", "参数非法！！");
		}

		logger.info("### create Room ju[" + ju + "]mjType[" + mjType + "]");
		RoomInfo roomInfo = null;
		if ("ningboMajiang".equals(mjType)) {
			roomInfo = this.createNingboMajiangRoom(request, ju);
		} else if ("fenhuaMajiang".equals(mjType)) {
			roomInfo = this.createFenhuaMajiangRoom(request, ju);
		} else if ("tiantaiMajiang".equals(mjType)) {
			roomInfo = this.createTiantaiMajiangRoom(request, ju);
		} else if ("hangzhouMajiang".equals(mjType)) {
			roomInfo = this.createHangzhouMajiangRoom(request, ju);
		} else if ("hongzhongMajiang".equals(mjType)) {
			roomInfo = this.createHongzhongMajiangRoom(request, ju);
		} else if ("guangdongMajiang".equals(mjType)) {
			roomInfo = this.createGuangdongMajiangRoom(request, ju);
		} else if ("threeWater".equals(mjType)) {
			roomInfo = this.createThreeWaterRoom(request, ju);
		} else if ("xiangshanMajiang".equals(mjType)) {
			roomInfo = this.createXiangshanMajiangRoom(request, ju);
		}
		if (roomInfo == null) {
			logger.info("### roomInfo null!");
			return ResponseFactory.newResponse("000004", "亲，创建房间失败了，请重新创建！");
		}
		if (!moneyStrategyService.enoughMoney(openid, roomInfo)) {
			return ResponseFactory.newResponse("000004", "亲,您的钻石不够了");
		}

		Room room = server.createRoom(openid, roomInfo);
		if (room == null) {
			return ResponseFactory.newResponse("000005", "亲，创建房间失败了，请重新创建！");
		}

		String recommend = request.getParameter("recommend");
		try {
			RoomDaoEntity roomDaoEntity = new RoomDaoEntity();
			roomDaoEntity.setCreate_time(new Date(room.getCreateTime()));
			roomDaoEntity.setRoomid(room.getId());
			roomDaoEntity.setCreate_account(room.getCreatePlayer());
			roomDaoEntity.setName(roomInfo.getName());
			roomDaoEntity.setPlayer_num(roomInfo.getPlayerNum());
			roomDaoEntity.setRecommend(recommend);
			roomDaoEntity.setRoom_info(JsonUtil.encode(room.getRoomInfo()));
			roomDaoEntity.setTotal_ju(roomInfo.getTotalJu());
			roomDao.insertRoom(roomDaoEntity);
			room.setUniqueId(roomDaoEntity.getId());
		} catch (Exception e) {
			logger.error(
					"###########################  create room insert db fail ",
					e);
			return ResponseFactory.newResponse("000005", "亲，创建房间失败了，请重新创建！");
		}
		// String originIp = weixinUserService.getOriginIp(request);
		// if (!StringUtils.isEmpty(originIp)) {
		// server.getPlayer(openid).setOriginIp(originIp);
		// }
		return ResponseFactory.newResponse("000000", "成功,房间号是" + room.getId(),
				room.getId());
	}
}
