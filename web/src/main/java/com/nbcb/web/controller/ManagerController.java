package com.nbcb.web.controller;

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

import com.nbcb.web.service.EnvironmentService;
import com.nbcb.web.service.WeixinLoginService;
import com.nbcb.weixinapi.entity.WeixinUser;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class ManagerController {

	private static final Logger logger = LoggerFactory
			.getLogger(ManagerController.class);

	@Autowired
	private WeixinLoginService weixinLoginService;

	@Autowired
	private EnvironmentService environmentService;

	@RequestMapping(value = "manager", method = RequestMethod.GET)
	public ModelAndView manager(HttpSession httpSession,
			HttpServletRequest request, String code) {
		if (StringUtils.isEmpty(code)) {
			logger.info("### code empty");
			return new ModelAndView("redirect:"
					+ this.environmentService.getCodedUrl("manager.do"));
		}
		WeixinUser wu = weixinLoginService.login(code, httpSession);
		return new ModelAndView("redirect:/majiang/manager.html");
	}
	
	@RequestMapping(value = "superManager", method = RequestMethod.GET)
	public ModelAndView superManager(HttpSession httpSession,
			HttpServletRequest request, String code) {
		if (StringUtils.isEmpty(code)) {
			logger.info("### code empty");
			return new ModelAndView("redirect:"
					+ this.environmentService.getCodedUrl("superManager.do"));
		}
		WeixinUser wu = weixinLoginService.login(code, httpSession);
		return new ModelAndView("redirect:/majiang/super.html");
	}

}
