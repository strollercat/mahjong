package com.nbcb.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	
	
	
	/**
	 * 获取长度为length的随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(random.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 获取a到b之间的随机数,包括a和b
	 * @param a
	 * @param b
	 * @return
	 */
	public static int getRandomNumber(int a, int b) {
		Random random = new Random();
		return random.nextInt(b-a+1) + a;
	}

	public static int[] getRandom(int a, int b) {
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		List<Integer> list = new ArrayList();
		for (int i = a; i <= b; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		int[] retInt = new int[b - a + 1];
		for (int i = 0; i < list.size(); i++) {
			retInt[i] = list.get(i);
		}
		return retInt;
	}
	
	public static void main(String [] args){
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
//		System.out.println(getRandomNumber(1,100));
		
		List<String> list = new ArrayList<String>();
		list.add("nima");
		list.add("nima");
		System.out.println(list.contains("nima"));
		
	}

}
