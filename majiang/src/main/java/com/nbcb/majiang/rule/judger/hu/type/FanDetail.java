package com.nbcb.majiang.rule.judger.hu.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FanDetail {

	private boolean paoda = false;

	private Map<String/* 单个的胡法 */, Integer> mapHuType = new HashMap<String, Integer>();

	public FanDetail(String type, int fan) {
		mapHuType.put(type, fan);
	}

	public FanDetail() {

	}

	public boolean isPaoda() {
		return paoda;
	}

	public void setPaoda(boolean paoda) {
		this.paoda = paoda;
	}

	public void add(FanDetail fanDetail) {
		Set<String> keySet = fanDetail.mapHuType.keySet();
		for (String key : keySet) {
			if (fanDetail.mapHuType.get(key) > 0) {
				if (this.mapHuType.containsKey(key)) {
					this.mapHuType.put(key, this.mapHuType.get(key)
							+ fanDetail.mapHuType.get(key));
				} else {
					this.mapHuType.put(key, fanDetail.mapHuType.get(key));
				}
			}
		}
	}

	public void add(String type, int fan) {
		if (mapHuType.get(type) != null) {
			mapHuType.put(type, mapHuType.get(type) + fan);
			return;
		}
		mapHuType.put(type, fan);
	}

	public void substract(String type, int fan) {
		if (mapHuType.get(type) != null) {
			mapHuType.put(type, mapHuType.get(type) - fan);
			return;
		}
	}

	public int getFan() {
		Set<String> keySet = mapHuType.keySet();
		int total = 0;
		for (String key : keySet) {
			if (mapHuType.get(key) > 0) {
				total += mapHuType.get(key);
			}
		}
		return total;
	}

	public String getDetail() {
		Set<String> keySet = mapHuType.keySet();
		String detail = "";
		for (String key : keySet) {
			if (mapHuType.get(key) > 0) {
				detail += (key + "+" + mapHuType.get(key));
			}
		}
		return detail;
	}

	public boolean includeHuType(String huTypeName) {
		if (mapHuType.get(huTypeName) == null) {
			return false;
		}
		int fan = mapHuType.get(huTypeName);
		return fan > 0;
	}

	@Override
	public String toString() {
		return "fan[" + this.getFan() + "]detail[" + this.getDetail()
				+ "]paoda[" + this.isPaoda() + "]";
	}

}
