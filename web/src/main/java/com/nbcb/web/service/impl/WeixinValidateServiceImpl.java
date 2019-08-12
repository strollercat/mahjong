package com.nbcb.web.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nbcb.common.util.CryptoUtil;
import com.nbcb.web.service.WeixinValidateService;

@Component
public class WeixinValidateServiceImpl implements WeixinValidateService {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinValidateServiceImpl.class);

	@Override
	public boolean validate(String token, String signature, String timestamp,
			String nonce) {
		// TODO Auto-generated method stub
		String[] sourceArray = new String[3];
		sourceArray[0] = token;
		sourceArray[1] = timestamp;
		sourceArray[2] = nonce;

		Arrays.sort(sourceArray);
		String targetStr = sourceArray[0] + "" + sourceArray[1] + ""
				+ sourceArray[2];
		try {
			String targetSignature = CryptoUtil.sha1(targetStr.getBytes());
			if (targetSignature.equals(signature)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

}
