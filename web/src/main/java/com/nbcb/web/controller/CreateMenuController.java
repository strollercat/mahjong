package com.nbcb.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcb.common.helper.Response;
import com.nbcb.common.helper.ResponseFactory;
import com.nbcb.web.service.EnvironmentService;
import com.nbcb.weixinapi.WeixinApiMenu;
import com.nbcb.weixinapi.WeixinCachedApiBase;
import com.nbcb.weixinapi.entity.WeixinSendMenus;
import com.nbcb.weixinapi.entity.WeixinSendMenus.WeixinSendMenu;
import com.nbcb.weixinapi.exception.WeixinException;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class CreateMenuController {

	private static final Logger logger = LoggerFactory
			.getLogger(CreateMenuController.class);

	@Autowired
	private EnvironmentService environmentService;

	@Autowired
	private WeixinCachedApiBase weixinCachedApiBase;

	@Autowired
	private WeixinApiMenu weixinApiMenu;

	@RequestMapping(value = "createMenu")
	@ResponseBody
	public Response createMenu() {

		String hallUrl = "home.do";

		WeixinSendMenus.WeixinSendMenu wsm1 = new WeixinSendMenus.WeixinSendMenu();
		wsm1.setName("宁波麻将");
		wsm1.setType(WeixinSendMenus.WeixinSendMenu.TYPE_VIEW);
		String url = hallUrl + "?view=ningboMajiang";
		url = this.environmentService.getCodedUrl(url);
		wsm1.setUrl(url);
		logger.info("## url "+url);

		WeixinSendMenus.WeixinSendMenu wsm2 = new WeixinSendMenus.WeixinSendMenu();
		wsm2.setName("红中麻将");
		wsm2.setType(WeixinSendMenus.WeixinSendMenu.TYPE_VIEW);
		url = hallUrl + "?view=hongzhongMajiang";
		url = this.environmentService.getCodedUrl(url);
		wsm2.setUrl(url);
		logger.info("## url "+url);

		WeixinSendMenus.WeixinSendSubMenu wssm1 = new WeixinSendMenus.WeixinSendSubMenu();
		wssm1.setName("创建游戏");
		List<WeixinSendMenu> subButtons = new ArrayList();
		subButtons.add(wsm2);
		subButtons.add(wsm1);
		wssm1.setSub_button(subButtons);

		WeixinSendMenus.WeixinSendMenu wsm3 = new WeixinSendMenus.WeixinSendMenu();
		wsm3.setName("游戏大厅");
		url = hallUrl;
		url = this.environmentService.getFullUrl(url);
		wsm3.setType(WeixinSendMenus.WeixinSendMenu.TYPE_VIEW);
		wsm3.setUrl(url);
		logger.info("## url "+url);

		WeixinSendMenus.WeixinSendMenu wsm6 = new WeixinSendMenus.WeixinSendMenu();
		wsm6.setName("代理");
		wsm6.setType(WeixinSendMenus.WeixinSendMenu.TYPE_CLICK);
		wsm6.setKey("KEY_DAILI");

		WeixinSendMenus.WeixinSendSubMenu wssm3 = new WeixinSendMenus.WeixinSendSubMenu();
		wssm3.setName("联系客服");
		List<WeixinSendMenu> subButtons3 = new ArrayList();
		subButtons3.add(wsm6);
		wssm3.setSub_button(subButtons3);

		WeixinSendMenus wsms = new WeixinSendMenus();
		List buttons = new ArrayList();
		buttons.add(wssm1);
		buttons.add(wsm3);
		buttons.add(wssm3);
		wsms.setButton(buttons);

		try {
			String at = weixinCachedApiBase
					.getCachedAccessToken(this.environmentService.getAppid());
			wsms.setAccessToken(at);
			this.weixinApiMenu.createMenu(wsms);
			return ResponseFactory.newResponse("000000", "成功");
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			logger.error("### error ", e);
			return ResponseFactory.newResponse("000001", "error");
		}

	}
}
