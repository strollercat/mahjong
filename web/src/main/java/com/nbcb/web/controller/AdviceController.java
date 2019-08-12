package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.common.util.BeanUtil;
import com.nbcb.common.util.DateUtil;
import com.nbcb.web.dao.GameAdviceDao;
import com.nbcb.web.dao.entity.GameAdvice;
import com.nbcb.web.service.EnvironmentService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class AdviceController implements InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(AdviceController.class);

	private Map<String, String> adviceGameTypeMap;

	private Map<String, String> adviceTypeMap;

	@Autowired
	private GameAdviceDao gameAdviceDao;

	@Autowired
	private EnvironmentService environmentService;

	@RequestMapping(value = "checkAdvice")
	@ResponseBody
	public Response checkAdvice(HttpServletRequest request) {
		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}

		List<GameAdvice> listGa = this.gameAdviceDao
				.seletGameAdvicesByAccount(openid);
		List<Map> listBody = new ArrayList<Map>();
		if (listGa != null && listGa.size() > 0) {
			for (GameAdvice ga : listGa) {
				Map tmpMap = BeanUtil.bean2Map(ga);
				Date date = (Date) tmpMap.get("time");
				tmpMap.put("time", DateUtil.formatDate(date));
				listBody.add(tmpMap);
			}
		}
		return ResponseFactory.newResponse("000000", "成功", listBody);
	}

	@RequestMapping(value = "giveAdvice")
	@ResponseBody
	public Response giveAdvice(HttpSession httpSession,
			HttpServletRequest request) {
		String openid = environmentService.getAccount(request);
		if (StringUtils.isEmpty(openid)) {
			return ResponseFactory.newResponse("000001", "不存在該用戶");
		}
		String type = request.getParameter("type");
		String gameType = request.getParameter("gametype");
		String advice = request.getParameter("advice");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		logger.debug("### type[" + type + "]gameType[" + gameType + "]advice["
				+ advice + "]name[" + name + "]phone[" + phone + "]");

		GameAdvice ga = new GameAdvice();
		ga.setType(this.adviceTypeMap.get(type));
		ga.setGame_type(this.adviceGameTypeMap.get(gameType));
		ga.setAdvice(advice);
		ga.setName(name);
		ga.setPhone(phone);
		ga.setOpenid(openid);
		ga.setTime(new Date(System.currentTimeMillis()));
		this.gameAdviceDao.insertGameAdvice(ga);
		return ResponseFactory.newResponse("000000", "成功");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		this.adviceGameTypeMap = new HashMap<String, String>();
		this.adviceGameTypeMap.put("3", "宁波麻将三百搭");
		this.adviceGameTypeMap.put("7", "宁波麻将七百搭");

		this.adviceTypeMap = new HashMap<String, String>();
		this.adviceTypeMap.put("1", "意见反馈");
		this.adviceTypeMap.put("2", "违法举报");
		this.adviceTypeMap.put("3", "游戏建议");
		this.adviceTypeMap.put("4", "充值问题");
		this.adviceTypeMap.put("5", "扣钻问题");
		this.adviceTypeMap.put("6", "掉线问题");
	}

}
