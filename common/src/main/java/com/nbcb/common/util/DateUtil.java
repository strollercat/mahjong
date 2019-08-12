package com.nbcb.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date get000000DateOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date get235959DateOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 
	 * @param str
	 *            2017-07-08这种格式
	 * @return
	 */
	public static Date get000000DateByDateStr(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param str
	 *            2017-07-08这种格式
	 * @return
	 */
	public static Date get235959DateByDateStr(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(str);
			Date endDate = new Date(date.getTime() + 24 * 60 * 60 * 1000 - 1000);
			return endDate;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
//		System.out.println(formatDate(new Date()));
//		System.out.println(get000000DateByDateStr("2018-02-28"));
//		System.out.println(get235959DateByDateStr("2018-03-29"));
//		System.out.println(get000000DateOfToday());
//		System.out.println(get235959DateOfToday());
		System.out.println(parseDate("2018-03-03 02:02:02"));
	}
}
