package com.nbcb.majiang.rule.judger.hu.type;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;

public class ZiHuaOrder {
	
	private static final Logger logger = LoggerFactory.getLogger(ZiHuaOrder.class);

	
	private static Map<String, Integer> mapDnxbOrder = new HashMap<String, Integer>();
	private static Map<String, Integer> mapCxqdOrder = new HashMap<String, Integer>();
	private static Map<String, Integer> mapMljzOrder = new HashMap<String, Integer>();
	static {
		mapDnxbOrder.put("东", 0);
		mapDnxbOrder.put("南", 1);
		mapDnxbOrder.put("西", 2);
		mapDnxbOrder.put("北", 3);

		mapCxqdOrder.put("春", 0);
		mapCxqdOrder.put("夏", 1);
		mapCxqdOrder.put("秋", 2);
		mapCxqdOrder.put("冬", 3);

		mapMljzOrder.put("梅", 0);
		mapMljzOrder.put("兰", 1);
		mapMljzOrder.put("菊", 2);
		mapMljzOrder.put("竹", 3);
	}

	public static Integer getDnxbOrder(String type) {
		return mapDnxbOrder.get(type);
	}

	public static Integer getCxqdOrder(String type) {
		return mapCxqdOrder.get(type);
	}

	public static Integer getMljzOrder(String type) {
		return mapMljzOrder.get(type);
	}
	

	public static boolean fullCxqd(Cards cs) {
		int size = cs.size();
//		logger.info("### cs "+cs);
		if (size < 4) {
			return false;
		}
		Map mapRet = new HashMap();
		for (int i = 0; i < size; i++) {
			Card card = cs.getCard(i);
			Integer order = mapCxqdOrder.get(card.getType());
			if (null != order) {
				mapRet.put(order, true);
			}
		}
//		logger.info("### mapRet "+mapRet);
		if (mapRet.get(0) != null && mapRet.get(1) != null
				&& mapRet.get(2) != null && mapRet.get(3) != null) {
			return true;
		}
		return false;
	}

	public static boolean fullMljz(Cards cs) {
		int size = cs.size();
		if (size < 4) {
			return false;
		}
		Map mapRet = new HashMap();
		for (int i = 0; i < size; i++) {
			Card card = cs.getCard(i);
			Integer order = mapMljzOrder.get(card.getType());
			if (null != order) {
				mapRet.put(order, new Object());
			}
		}
		if (mapRet.get(0) != null && mapRet.get(1) != null
				&& mapRet.get(2) != null && mapRet.get(3) != null) {
			return true;
		}
		return false;
	}

}
