package com.nbcb.web.service.impl;

import com.nbcb.common.util.RandomUtil;
import com.nbcb.web.service.OrderNumberGenerator;



public class MilleSecondRandomOrderNumberGenerator implements OrderNumberGenerator {

	@Override
	public String generatorOrderNumber() {
		return System.currentTimeMillis()+RandomUtil.getRandomString(5);
	}

}
