package com.nbcb.majiang.rule.judger.hu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangUnitCards;

public class MajiangHuRoads {

	private Map<MajiangHuRoad, Object> mapRoad = new HashMap<MajiangHuRoad, Object>();

	private Map<Integer, List<MajiangHuRoad>> mapRoadSort = new HashMap<Integer, List<MajiangHuRoad>>();

	private MajiangCard mjHuCard;

	public MajiangHuRoads(MajiangCard mjHuCard) {
		this.mjHuCard = mjHuCard;

		mapRoadSort.put(1, new ArrayList<MajiangHuRoad>());
		mapRoadSort.put(2, new ArrayList<MajiangHuRoad>());
		mapRoadSort.put(3, new ArrayList<MajiangHuRoad>());
		mapRoadSort.put(4, new ArrayList<MajiangHuRoad>());
	}

	public boolean addListUnitCards(List<MajiangUnitCards> listUnitCards) {


		MajiangHuRoad mhr = new MajiangHuRoad(listUnitCards, mjHuCard);
		if (mapRoad.get(mhr) == null) {
			mapRoad.put(mhr, new Object());
			mapRoadSort.get(listUnitCards.size()).add(mhr);
			return true;
		}
		// System.out.println("listUnitCards has walked ever "+listUnitCards);
		return false;
	}

	@Override
	public String toString() {

		String str = "";
		for (int i = 1; i <= 4; i++) {
			List<MajiangHuRoad> list = mapRoadSort.get(i);
			for (int j = 0; j < list.size(); j++) {
				str += (list.get(j) + "\r\n");
			}
		}

		// Set<MajiangHuRoad> set = mapRoad.keySet();
		// for (MajiangHuRoad huRoad : set) {
		// str += (huRoad + "\r\n");
		// }
		return str;
	}
}
