package com.nbcb.common.util;

import java.security.MessageDigest;

/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class CryptoUtil {

	/**
	 * JAVA SHA1加密
	 * 
	 * @param sourceStr字节数组
	 * @return 加密后字符串
	 * @throws Exception
	 */
	public static String sha1(byte[] sourceStr) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("SHA-1");
		alg.update(sourceStr);
		byte[] testStr = alg.digest();
		return bytes2Hex(testStr);
	}

	/**
	 * 二进制转十六进制
	 * 
	 * @param bts
	 * @return
	 */
	private static String bytes2Hex(final byte[] bts) {
		String des = "";
		String tmp = "";
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des = des + "0" + tmp;
			} else {
				des = des + tmp;
			}
		}
		return des;
	}

}
