package com.nbcb.poker.threewater.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsCompare;
import com.nbcb.poker.card.PokerUnitCardsFinder;
import com.nbcb.poker.card.PokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.ColorSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.ListPokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.NumberSamePokerUnitCardsJudger;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsFinder;
import com.nbcb.poker.card.strategy.base.SequencePokerUnitCardsJudger;
import com.nbcb.poker.threewater.ThreeWaterPokerCards;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterDuiziPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterDuiziPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterHuluPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterHuluPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterLiangduiPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterLiangduiPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterNormalPokerUnitCardsCompare;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterSantiaoPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterSantiaoPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterShunziPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterShunziPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTiezhiPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTiezhiPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTonghuaPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTonghuaPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTonghuaShunPokerUnitCardsFinder;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterTonghuaShunPokerUnitCardsJudger;
import com.nbcb.poker.threewater.rule.judger.normal.ThreeWaterWulongPokerUnitCardsJudger;

public class DefaultThreeWaterPokerShootChooser implements
		ThreeWaterPokerShootChooser {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultThreeWaterPokerShootChooser.class);

	private PokerUnitCardsJudger normalPokerUnitCardsJudger;

	private PokerUnitCardsCompare normalPokerUnitCardsCompare;

	private List<PokerUnitCardsFinder> listFinder;

	public void setListFinder(List<PokerUnitCardsFinder> listFinder) {
		this.listFinder = listFinder;
	}

	public void setNormalPokerUnitCardsCompare(
			PokerUnitCardsCompare normalPokerUnitCardsCompare) {
		this.normalPokerUnitCardsCompare = normalPokerUnitCardsCompare;
	}

	public void setNormalPokerUnitCardsJudger(
			PokerUnitCardsJudger normalPokerUnitCardsJudger) {
		this.normalPokerUnitCardsJudger = normalPokerUnitCardsJudger;
	}

	private ThreeWaterPokerCards findRemoved(ThreeWaterPokerCards pcs1,
			ThreeWaterPokerCards pcs2) {

		ThreeWaterPokerCards removed = null;

		boolean ret1 = pcs1.getFirstPokerCards().getName()
				.equals(pcs2.getFirstPokerCards().getName());
		boolean ret2 = pcs1.getSecondPokerCards().getName()
				.equals(pcs2.getSecondPokerCards().getName());
		boolean ret3 = pcs1.getThirdPokerCards().getName()
				.equals(pcs2.getThirdPokerCards().getName());

		int total = 0;
		if (ret1) {
			total++;
		}
		if (ret2) {
			total++;
		}
		if (ret3) {
			total++;
		}
		if (total != 2) {
			return null;
		}
		if (!ret1) {
			if (normalPokerUnitCardsCompare.compare(pcs1.getFirstPokerCards(),
					pcs2.getFirstPokerCards()) > 0) {
				removed = pcs2;
			} else {
				removed = pcs1;
			}
		}

		if (!ret2) {
			if (normalPokerUnitCardsCompare.compare(pcs1.getSecondPokerCards(),
					pcs2.getSecondPokerCards()) > 0) {
				removed = pcs2;
			} else {
				removed = pcs1;
			}
		}
		if (!ret3) {
			if (normalPokerUnitCardsCompare.compare(pcs1.getThirdPokerCards(),
					pcs2.getThirdPokerCards()) > 0) {
				removed = pcs2;
			} else {
				removed = pcs1;
			}
		}
		return removed;
	}

	private void filter(List<ThreeWaterPokerCards> listTwpcs) {
		Set<ThreeWaterPokerCards> set = new HashSet<ThreeWaterPokerCards>();

		for (ThreeWaterPokerCards pcs1 : listTwpcs) {
			for (ThreeWaterPokerCards pcs2 : listTwpcs) {
				if (pcs1 == pcs2) {
					continue;
				}
				ThreeWaterPokerCards find = this.findRemoved(pcs1, pcs2);
				// System.out.println("find["+find+"]pcs1["+pcs1+"]pcs2["+pcs2+"]");
				if (find == null) {
					continue;
				}
				set.add(find);
			}
		}
		// System.out.println("listRemoved "+set);
		// System.out.println();
		listTwpcs.removeAll(set);
	}

	@Override
	public List<ThreeWaterPokerCards> chooseBetter(PokerCards pokerCards) {
		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(pokerCards.toArray());
		List<ThreeWaterPokerCards> list = new ArrayList<ThreeWaterPokerCards>();

		long start = System.currentTimeMillis();
		this.chooseBetterInner(list, new ArrayList<PokerUnitCards>(), copyPcs);
		logger.info("##################共耗费"
				+ (System.currentTimeMillis() - start) + "豪秒###############");
//		this.filter(list);
		return list;
	}

	private String getKey(ThreeWaterPokerCards pcs) {
		return pcs.getFirstPokerCards().getName() + " "
				+ pcs.getSecondPokerCards().getName() + " "
				+ pcs.getThirdPokerCards().getName();
	}

	private boolean containsSameKind(List<ThreeWaterPokerCards> listTwpcs,
			ThreeWaterPokerCards twPcs) {
		if (listTwpcs == null || listTwpcs.size() == 0) {
			return false;
		}
		for (ThreeWaterPokerCards pcs : listTwpcs) {
			if (this.getKey(pcs).equals(this.getKey(twPcs))) {
				return true;
			}
		}
		return false;
	}

	private boolean chooseBetterInner(List<ThreeWaterPokerCards> listTwpcs,
			List<PokerUnitCards> list, PokerCards pcs) {

		if (pcs.size() == 3) {
			PokerUnitCards thirdPucs = normalPokerUnitCardsJudger.judge(pcs);
			list.add(thirdPucs);
			ThreeWaterPokerCards twPcs = this.getThreeWaterPokerCards(list);
			list.remove(thirdPucs);
			if (twPcs == null) {
				return false;
			}
			if (this.containsSameKind(listTwpcs, twPcs)) {
				return false;
			}
			// System.out.println(twPcs);

			listTwpcs.add(twPcs);

			if (listTwpcs.size() >= 7) {
				return true;
			}
			return false;
		}
		for (PokerUnitCardsFinder f : listFinder) {
			List<PokerUnitCards> listPucs = f.find(pcs, 5);
			if (listPucs == null || listPucs.size() == 0) {
				continue;
			}
			for (PokerUnitCards pucs : listPucs) {
				list.add(pucs);
				pcs.removeCards(pucs.toArray());
				
				if(list.size() == 2){
					if(this.normalPokerUnitCardsCompare.compare(list.get(1), list.get(0)) >=0){
						pcs.addTailCards(pucs.toArray());
						list.remove(pucs);
						continue;
					}
				}
				
				boolean ret = chooseBetterInner(listTwpcs, list, pcs);
				if (ret) {
					return true;
				}
				pcs.addTailCards(pucs.toArray());
				list.remove(pucs);
			}
		}
		return false;
	}

	private ThreeWaterPokerCards getThreeWaterPokerCards(
			List<PokerUnitCards> list) {

		if (this.normalPokerUnitCardsCompare.compare(list.get(2), list.get(1)) < 0) {
			ThreeWaterPokerCards pcs = new ThreeWaterPokerCards();
			pcs.setFirstPokerCards(list.get(2));
			pcs.setSecondPokerCards(list.get(1));
			pcs.setThirdPokerCards(list.get(0));
			pcs.sort();
			return pcs;
		}
		return null;
	}

	@Override
	public boolean legal(ThreeWaterPokerCards twPcs) {
		if (twPcs.getFirstPokerCards() == null) {
			return false;
		}
		if (twPcs.getSecondPokerCards() == null) {
			return false;
		}
		if (twPcs.getThirdPokerCards() == null) {
			return false;
		}

		if (this.normalPokerUnitCardsCompare.compare(
				twPcs.getFirstPokerCards(), twPcs.getSecondPokerCards()) >= 0) {
			return false;
		}
		if (this.normalPokerUnitCardsCompare.compare(
				twPcs.getSecondPokerCards(), twPcs.getThirdPokerCards()) >= 0) {
			return false;
		}
		return true;

	}

	public static List<PokerUnitCardsFinder> getListFinder() {
		ColorSamePokerUnitCardsFinder colorSamePokerUnitCardsFinder = new ColorSamePokerUnitCardsFinder();
		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		List<PokerUnitCardsFinder> list = new ArrayList<PokerUnitCardsFinder>();

		ThreeWaterTonghuaShunPokerUnitCardsFinder j1 = new ThreeWaterTonghuaShunPokerUnitCardsFinder();
		j1.setSequencePokerUnitCardsFinder(sequencePokerUnitCardsFinder);
		list.add(j1);

		ThreeWaterTonghuaPokerUnitCardsFinder j2 = new ThreeWaterTonghuaPokerUnitCardsFinder();
		j2.setColorSamePokerUnitCardsFinder(colorSamePokerUnitCardsFinder);
		list.add(j2);

		ThreeWaterTiezhiPokerUnitCardsFinder j3 = new ThreeWaterTiezhiPokerUnitCardsFinder();
		j3.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j3);

		ThreeWaterHuluPokerUnitCardsFinder j4 = new ThreeWaterHuluPokerUnitCardsFinder();
		j4.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j4);

		ThreeWaterShunziPokerUnitCardsFinder j5 = new ThreeWaterShunziPokerUnitCardsFinder();
		j5.setSequencePokerUnitCardsFinder(sequencePokerUnitCardsFinder);
		list.add(j5);

		ThreeWaterSantiaoPokerUnitCardsFinder j6 = new ThreeWaterSantiaoPokerUnitCardsFinder();
		j6.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j6);

		ThreeWaterLiangduiPokerUnitCardsFinder j7 = new ThreeWaterLiangduiPokerUnitCardsFinder();
		j7.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j7);

		ThreeWaterDuiziPokerUnitCardsFinder j8 = new ThreeWaterDuiziPokerUnitCardsFinder();
		j8.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j8);

		return list;
	}

	public static PokerUnitCardsJudger getNormalJudger() {
		NumberSamePokerUnitCardsJudger numberSamePokerUnitCardsJudger = new NumberSamePokerUnitCardsJudger();
		ColorSamePokerUnitCardsJudger colorSamePokerUnitCardsJudger = new ColorSamePokerUnitCardsJudger();
		SequencePokerUnitCardsJudger sequencePokerUnitCardsJudger = new SequencePokerUnitCardsJudger();
		NumberSamePokerUnitCardsFinder numberSamePokerUnitCardsFinder = new NumberSamePokerUnitCardsFinder();
		SequencePokerUnitCardsFinder sequencePokerUnitCardsFinder = new SequencePokerUnitCardsFinder();

		ListPokerUnitCardsJudger jj = new ListPokerUnitCardsJudger();
		List<PokerUnitCardsJudger> list = new ArrayList<PokerUnitCardsJudger>();
		jj.setListPokerUnitCardsJudger(list);

		ThreeWaterTonghuaShunPokerUnitCardsJudger j1 = new ThreeWaterTonghuaShunPokerUnitCardsJudger();
		j1.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j1.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);
		list.add(j1);

		ThreeWaterTonghuaPokerUnitCardsJudger j5 = new ThreeWaterTonghuaPokerUnitCardsJudger();
		j5.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j5.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);
		list.add(j5);

		ThreeWaterTiezhiPokerUnitCardsJudger j2 = new ThreeWaterTiezhiPokerUnitCardsJudger();
		j2.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j2);

		ThreeWaterHuluPokerUnitCardsJudger j3 = new ThreeWaterHuluPokerUnitCardsJudger();
		j3.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j3);

		ThreeWaterShunziPokerUnitCardsJudger j4 = new ThreeWaterShunziPokerUnitCardsJudger();
		j4.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j4.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);
		list.add(j4);

		ThreeWaterSantiaoPokerUnitCardsJudger j6 = new ThreeWaterSantiaoPokerUnitCardsJudger();
		j6.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		j6.setNumberSamePokerUnitCardsJudger(numberSamePokerUnitCardsJudger);
		list.add(j6);

		ThreeWaterLiangduiPokerUnitCardsJudger j7 = new ThreeWaterLiangduiPokerUnitCardsJudger();
		j7.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j7);

		ThreeWaterDuiziPokerUnitCardsJudger j8 = new ThreeWaterDuiziPokerUnitCardsJudger();
		j8.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j8);

		ThreeWaterWulongPokerUnitCardsJudger j9 = new ThreeWaterWulongPokerUnitCardsJudger();
		j9.setSequencePokerUnitCardsJudger(sequencePokerUnitCardsJudger);
		j9.setColorSamePokerUnitCardsJudger(colorSamePokerUnitCardsJudger);
		j9.setNumberSamePokerUnitCardsFinder(numberSamePokerUnitCardsFinder);
		list.add(j9);

		return jj;

	}

	public static void main(String[] args) {

		ThreeWaterNormalPokerUnitCardsCompare twC = new ThreeWaterNormalPokerUnitCardsCompare();

		DefaultThreeWaterPokerShootChooser c = new DefaultThreeWaterPokerShootChooser();
		c.setNormalPokerUnitCardsCompare(twC);
		c.setNormalPokerUnitCardsJudger(getNormalJudger());
		c.setListFinder(getListFinder());

		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();
		pokerAllCards.removeTailCard();
		pokerAllCards.removeTailCard();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 1));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.FANGKUAI, 9));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 2));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 2));

		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 10));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HONGTAO, 11));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 12));

		// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
		// PokerCard.Color.HONGTAO, 4));
		// c.chooseBetter(pokerAllCards.getRandomPokerCards(13));
//		 c.chooseBetter(pcs);
//		System.out.println(c.chooseBetter(pcs));
		// System.out
		System .out.println(c.chooseBetter(pokerAllCards.getRandomPokerCards(13)));

	}

}
