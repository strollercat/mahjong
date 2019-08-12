package com.nbcb.core.user;

import java.util.Date;

public class DefaultLocationService implements LocationService {

	private double EARTH_RADIUS = 6378.137;

	private double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM） 参数为String类型
	 * 
	 * @param lat1
	 *            用户经度
	 * @param lng1
	 *            用户纬度
	 * @param lat2
	 *            商家经度
	 * @param lng2
	 *            商家纬度
	 * @return
	 */
	public String getDistance(String lat1Str, String lng1Str, String lat2Str,
			String lng2Str) {
		Double lat1 = Double.parseDouble(lat1Str);
		Double lng1 = Double.parseDouble(lng1Str);
		Double lat2 = Double.parseDouble(lat2Str);
		Double lng2 = Double.parseDouble(lng2Str);

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1) - rad(lng2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(
				Math.sin(difference / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

		return distanceStr;
	}

	@Override
	public double calculatorDistance(Location location1, Location location2) {
		// TODO Auto-generated method stub
		long time1 = location1.getDate().getTime();
		long time2 = location2.getDate().getTime();

		if (time1 - time2 > 10 * 60 * 1000) {
			return 9999999999L;
		}

		String distance = this.getDistance(String.valueOf(location1.getLatitude()),
				String.valueOf(location1.getLongitude()), String.valueOf(location2.getLatitude()),
				String.valueOf(location2.getLongitude()));
		try{
			return Double.parseDouble(distance);
		}catch(Exception e){
			return 9999999999L;
		}
		
	}
	
	public static void main(String [] args){
		DefaultLocationService ds = new DefaultLocationService();
		Location l1 = new Location();
		l1.setDate(new Date());
		l1.setLatitude(29.847352981567383);
		l1.setLongitude(121.60005187988281);
		
		
		Location l2 = new Location();
		l2.setDate(new Date());
		l2.setLatitude(29.84718132019043);
		l2.setLongitude(120.5993118286132);
		
		System.out.println(ds.calculatorDistance(l1, l2));
	}

}
