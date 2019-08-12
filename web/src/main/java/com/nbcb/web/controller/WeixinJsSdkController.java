package com.nbcb.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nbcb.common.helper.Response;
import com.nbcb.web.service.JsSdkService;

/**
 * 
 * @author zhengbinhui
 * 
 */
@Controller
public class WeixinJsSdkController {

	@Autowired
	private JsSdkService jsSdkService;

	@RequestMapping(value = "getJsSdkSign", method = RequestMethod.POST)
	@ResponseBody
	public Response jsSdk(String url) {
		return jsSdkService.getJsSdk(url);
	}
}
