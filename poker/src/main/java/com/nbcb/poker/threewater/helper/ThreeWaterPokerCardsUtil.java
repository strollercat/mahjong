package com.nbcb.poker.threewater.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nbcb.core.card.Card;
import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCards;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterDuiziPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterHuluPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterLiangduiPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterSantiaoPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterShunziPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTiezhiPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTonghuaPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTonghuaShunPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterWulongPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterCouyisePokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterLiuduibanPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterQuandaPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterQuanxiaoPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterSanfentianxiaPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterSanshunziPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterSantonghuaPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterSantonghuashunPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterShierhuangzuPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterSitaosantiaoPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterWuduisantiaoPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterYitiaolongPokerUnitCards;
import com.nbcb.poker.threewater.rule.judger.special.ThreeWaterZhizunqinglongPokerUnitCards;

public class ThreeWaterPokerCardsUtil {

	private static Map<Class, Integer> mapNormalOrder = new HashMap<Class, Integer>();

	private static Map<Class, Integer> mapSpecialOrder = new HashMap<Class, Integer>();

	static {
		mapNormalOrder.put(ThreeWaterWulongPokerUnitCards.class, 0);
		mapNormalOrder.put(ThreeWaterDuiziPokerUnitCards.class, 1);
		mapNormalOrder.put(ThreeWaterLiangduiPokerUnitCards.class, 2);
		mapNormalOrder.put(ThreeWaterSantiaoPokerUnitCards.class, 3);
		mapNormalOrder.put(ThreeWaterShunziPokerUnitCards.class, 4);
		mapNormalOrder.put(ThreeWaterTonghuaPokerUnitCards.class, 5);
		mapNormalOrder.put(ThreeWaterHuluPokerUnitCards.class, 6);
		mapNormalOrder.put(ThreeWaterTiezhiPokerUnitCards.class, 7);
		mapNormalOrder.put(ThreeWaterTonghuaShunPokerUnitCards.class, 8);

		// mapNormalName.put(ThreeWaterWulongPokerUnitCards.class, "乌龙");
		// mapNormalName.put(ThreeWaterDuiziPokerUnitCards.class, "对子");
		// mapNormalName.put(ThreeWaterLiangduiPokerUnitCards.class, "两队");
		// mapNormalName.put(ThreeWaterSantiaoPokerUnitCards.class, "三条");
		// mapNormalName.put(ThreeWaterTonghuaPokerUnitCards.class, "同花");
		// mapNormalName.put(ThreeWaterShunziPokerUnitCards.class, "顺子");
		// mapNormalName.put(ThreeWaterHuluPokerUnitCards.class, "葫芦");
		// mapNormalName.put(ThreeWaterTiezhiPokerUnitCards.class, "铁支");
		// mapNormalName.put(ThreeWaterTonghuaShunPokerUnitCards.class, "同花顺");

		mapSpecialOrder.put(ThreeWaterZhizunqinglongPokerUnitCards.class, 0);
		mapSpecialOrder.put(ThreeWaterYitiaolongPokerUnitCards.class, 1);
		mapSpecialOrder.put(ThreeWaterShierhuangzuPokerUnitCards.class, 2);
		mapSpecialOrder.put(ThreeWaterSantonghuashunPokerUnitCards.class, 3);
		mapSpecialOrder.put(ThreeWaterSanfentianxiaPokerUnitCards.class, 4);
		mapSpecialOrder.put(ThreeWaterQuandaPokerUnitCards.class, 5);
		mapSpecialOrder.put(ThreeWaterQuanxiaoPokerUnitCards.class, 6);
		mapSpecialOrder.put(ThreeWaterCouyisePokerUnitCards.class, 7);
		mapSpecialOrder.put(ThreeWaterSitaosantiaoPokerUnitCards.class, 8);
		mapSpecialOrder.put(ThreeWaterWuduisantiaoPokerUnitCards.class, 9);
		mapSpecialOrder.put(ThreeWaterLiuduibanPokerUnitCards.class, 10);
		mapSpecialOrder.put(ThreeWaterSanshunziPokerUnitCards.class, 11);
		mapSpecialOrder.put(ThreeWaterSantonghuaPokerUnitCards.class, 12);

		// mapSpecialName
		// .put(ThreeWaterZhizunqinglongPokerUnitCards.class, "至尊青龙");
		// mapSpecialName.put(ThreeWaterYitiaolongPokerUnitCards.class, "一条龙");
		// mapSpecialName.put(ThreeWaterShierhuangzuPokerUnitCards.class,
		// "十二皇族");
		// mapSpecialName
		// .put(ThreeWaterSantonghuashunPokerUnitCards.class, "三同花顺");
		// mapSpecialName.put(ThreeWaterSanfentianxiaPokerUnitCards.class,
		// "三分天下");
		// mapSpecialName.put(ThreeWaterQuandaPokerUnitCards.class, "全大");
		// mapSpecialName.put(ThreeWaterQuanxiaoPokerUnitCards.class, "全小");
		// mapSpecialName.put(ThreeWaterCouyisePokerUnitCards.class, "凑一色");
		// mapSpecialName.put(ThreeWaterSitaosantiaoPokerUnitCards.class,
		// "四套三条");
		// mapSpecialName.put(ThreeWaterWuduisantiaoPokerUnitCards.class,
		// "五对三条");
		// mapSpecialName.put(ThreeWaterLiuduibanPokerUnitCards.class, "六对半");
		// mapSpecialName.put(ThreeWaterSanshunziPokerUnitCards.class, "三顺子");
		// mapSpecialName.put(ThreeWaterSantonghuaPokerUnitCards.class, "");

	}

	public static int getNormalOrder(Class<? extends PokerUnitCards> clazz) {
		return mapNormalOrder.get(clazz);
	}

	// public static String getNormalName(Class<? extends PokerUnitCards> clazz)
	// {
	// return mapNormalName.get(clazz);
	// }

	public static int getSpecialOrder(Class<? extends PokerUnitCards> clazz) {
		return mapSpecialOrder.get(clazz);
	}

	// public static String getSpecialName(Class<? extends PokerUnitCards>
	// clazz) {
	// return mapSpecialName.get(clazz);
	// }

	/**
	 * 
	 * @param pcs
	 * @return
	 */
	public static PokerCard findBiggestPokerNumberCard(PokerCards pcs) {
		for (int i = 0; i < pcs.size(); i++) {
			PokerCard pc = (PokerCard) pcs.getCard(i);
			if (pc.getPokerNumber() == 1) {
				return pc;
			}
		}
		Set<Integer> set = pcs.findAllPokerNumber();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(set);
		Collections.sort(list);
		int biggest = list.get(list.size() - 1);
		for (int i = 0; i < pcs.size(); i++) {
			PokerCard pc = (PokerCard) pcs.getCard(i);
			if (pc.getPokerNumber() == biggest) {
				return pc;
			}
		}
		throw new RuntimeException("can not to here");
	}

	public static int comparePokerCardByPokerNumber(PokerCard pc1, PokerCard pc2) {
		if (pc1.getPokerNumber() == 1 && pc2.getPokerNumber() != 1) {
			return 1;
		}
		if (pc1.getPokerNumber() != 1 && pc2.getPokerNumber() == 1) {
			return -1;
		}
		if (pc1.getPokerNumber() == 1 && pc2.getPokerNumber() == 1) {
			return 0;
		}
		return pc1.getPokerNumber() - pc2.getPokerNumber();
	}

	public static int comparePokerCardsByPokerNumber(PokerCards pcs1,
			PokerCards pcs2) {

		pcs1 = sortByPokerNumber(pcs1);
		pcs2 = sortByPokerNumber(pcs2);

		int len;
		if (pcs1.size() > pcs2.size()) {
			len = pcs2.size();
		} else {
			len = pcs1.size();
		}

		for (int i = 0; i < len; i++) {
			PokerCard pc1 = (PokerCard) pcs1.getCard(i);
			PokerCard pc2 = (PokerCard) pcs2.getCard(i);
			int result = comparePokerCardByPokerNumber(pc1, pc2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	/**
	 * 降序
	 * 
	 * @param pcs
	 */
	public static PokerCards sortByPokerNumber(PokerCards pcs) {
		PokerCards retPcs = new PokerCards();

		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(pcs.toArray());
		while (true) {
			if (copyPcs.size() <= 0) {
				break;
			}
			PokerCard pc = findBiggestPokerNumberCard(copyPcs);
			retPcs.addTailCard(pc);
			copyPcs.removeCard(pc);
		}
		return retPcs;
	}

	public static PokerCards getPokerCardsByIndex(PokerCards pokerCards,
			int start, int size) {

		PokerCards pcs = new PokerCards();
		for (int i = start; i < size + start; i++) {
			pcs.addTailCard(pokerCards.getCard(i));
		}
		return pcs;

	}

	public static List<PokerCards> getNotSameCardsList(PokerCards pcs, int size) {
		if (size > 3) {
			throw new RuntimeException("size[" + size + "]is ");
		}
		List<PokerCards> list = new ArrayList<PokerCards>();
		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(pcs.toArray());
		getNotSameCardsListInner(list, copyPcs, new PokerCards(), size,
				new ArrayList<PokerCards>());
		return list;
	}
	
	

	private static boolean allNotSameByPokerNumber(PokerCards pcs) {
		Set set = pcs.findAllPokerNumber();
		if (set.size() != pcs.size()) {
			return false;
		}
		return true;
	}

	private static void getNotSameCardsListInner(List<PokerCards> list,
			PokerCards oriPcs, PokerCards pcs, int size,
			List<PokerCards> listRoad) {
		if (pcs.size() == size) {
			if (!allNotSameByPokerNumber(pcs)) {
				return;
			}
			if (pcs.containedPokerCardsByPokerNumber(list)) {
				return;
			}

			PokerCards copyPcs = new PokerCards();
			copyPcs.addTailCards(pcs.toArray());
			list.add(copyPcs);
			return;
		}

		Card card[] = oriPcs.toArray();
		for (Card c : card) {
			oriPcs.removeCard(c);
			pcs.addTailCard(c);

			if (pcs.containedPokerCardsByPokerNumber(listRoad)) {
				oriPcs.addTailCard(c);
				pcs.removeCard(c);
				continue;
			}
			PokerCards tmpPcs = new PokerCards();
			tmpPcs.addTailCards(pcs.toArray());
			listRoad.add(tmpPcs);

			getNotSameCardsListInner(list, oriPcs, pcs, size, listRoad);
			oriPcs.addTailCard(c);
			pcs.removeCard(c);
		}
	}

	public static void main(String[] args) {

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 11));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 12));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 13));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 9));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 6));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 7));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 8));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 5));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 4));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 1));
		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 1));

		// System.out.println(pcs.chooseAllNotSamePokerNumberCardsBySize(2));
		System.out.println(getNotSameCardsList(pcs, 3));

	}

}
