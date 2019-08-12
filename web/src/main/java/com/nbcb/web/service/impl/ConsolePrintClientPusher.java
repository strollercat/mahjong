package com.nbcb.web.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.common.util.JsonUtil;
import com.nbcb.core.io.ClientPusher;

public class ConsolePrintClientPusher implements ClientPusher {
	private static final Logger logger = LoggerFactory.getLogger(ConsolePrintClientPusher.class);

	
	@Override
	public void push(String account, Map info) {
		// TODO Auto-generated method stub
		try {
			String strPush = JsonUtil.encode(info);
			logger.info("######  push result account[" + account + "]info[" + strPush + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
