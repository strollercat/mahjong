package com.nbcb.common.util;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtil {
	/**
	 * 最大的字节流
	 */
	private static final int MAX_STR_LEN = 102400;

	/**
	 * 读取指定参数字节流
	 * 
	 * @param input
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStream(InputStream input, int length)
			throws IOException {
		int tmpLen = 1024;
		byte[] byteRet = new byte[length];
		byte[] byteTmp = new byte[tmpLen];
		int pos = 0;
		int ret;
		while (true) {
			if (length - pos > tmpLen) {
				ret = input.read(byteTmp, 0, tmpLen);
			} else {
				ret = input.read(byteTmp, 0, length - pos);
			}
			if (ret <= 0)
				break;
			System.arraycopy(byteTmp, 0, byteRet, pos, ret);
			pos += ret;
			if (pos >= length) {
				break;
			}

			if (length > MAX_STR_LEN) {
				throw new IOException("input too long");
			}
		}
		return byteRet;
	}

}
