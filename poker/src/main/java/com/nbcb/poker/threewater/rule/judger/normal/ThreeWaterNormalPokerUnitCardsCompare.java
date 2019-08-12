package com.nbcb.poker.threewater.rule.judger.normal;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsCompare;
import com.nbcb.poker.threewater.helper.ThreeWaterPokerCardsUtil;

public class ThreeWaterNormalPokerUnitCardsCompare implements
		PokerUnitCardsCompare {

	

	private int compareWulong(ThreeWaterWulongPokerUnitCards pucs1,
			ThreeWaterWulongPokerUnitCards pucs2) {
		return ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(pucs1,
				pucs2);
	}

	private int compareDuizi(ThreeWaterDuiziPokerUnitCards pucs1,
			ThreeWaterDuiziPokerUnitCards pucs2) {
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pucs1.getDuizi(), pucs2.getDuizi());
		if (result != 0) {
			return result;
		} else {
			return ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
					pucs1.getWulong(), pucs2.getWulong());
		}
	}

	private PokerCards getMaxPokerCards(PokerCards pcs1, PokerCards pcs2) {
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pcs1, pcs2);
		if (result > 0) {
			return pcs1;
		} else {
			return pcs2;
		}
	}

	private PokerCards getMinPokerCards(PokerCards pcs1, PokerCards pcs2) {
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pcs1, pcs2);
		if (result > 0) {
			return pcs2;
		} else {
			return pcs1;
		}
	}

	private int compareLiangdui(ThreeWaterLiangduiPokerUnitCards pucs1,
			ThreeWaterLiangduiPokerUnitCards pucs2) {
		PokerCards pcs1Max = this.getMaxPokerCards(pucs1.getPcs1(),
				pucs1.getPcs2());
		PokerCards pcs1Min = this.getMinPokerCards(pucs1.getPcs1(),
				pucs1.getPcs2());
		PokerCards pcs2Max = this.getMaxPokerCards(pucs2.getPcs1(),
				pucs2.getPcs2());
		PokerCards pcs2Min = this.getMinPokerCards(pucs2.getPcs1(),
				pucs2.getPcs2());
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pcs1Max, pcs2Max);

		if (result > 0) {
			return 1;
		} else if (result < 0) {
			return -1;
		}

		result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pcs1Min, pcs2Min);

		if (result > 0) {
			return 1;
		} else if (result < 0) {
			return -1;
		}

		return ThreeWaterPokerCardsUtil.comparePokerCardByPokerNumber(
				pucs1.getPc(), pucs2.getPc());
	}

	private int compareSantiao(ThreeWaterSantiaoPokerUnitCards pucs1,
			ThreeWaterSantiaoPokerUnitCards pucs2) {
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pucs1.getThreePcs(), pucs2.getThreePcs());
		if (result != 0) {
			return result;
		} else {
			return ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
					pucs1.getWulong(), pucs2.getWulong());
		}
	}

	private int compareTonghua(ThreeWaterTonghuaPokerUnitCards pucs1,
			ThreeWaterTonghuaPokerUnitCards pucs2) {
		return ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(pucs1,
				pucs2);
	}

	private int compareShunzi(ThreeWaterShunziPokerUnitCards pucs1,
			ThreeWaterShunziPokerUnitCards pucs2) {
		return pucs1.getSequenceUnitCards().getSequenceMax()
				- pucs2.getSequenceUnitCards().getSequenceMax();
	}

	private int compareHulu(ThreeWaterHuluPokerUnitCards pucs1,
			ThreeWaterHuluPokerUnitCards pucs2) {
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pucs1.getThreePcs(), pucs2.getThreePcs());
		if (result != 0) {
			return result;
		} else {
			return ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
					pucs1.getTwoPcs(), pucs2.getTwoPcs());
		}
	}

	private int compareTiezhi(ThreeWaterTiezhiPokerUnitCards pucs1,
			ThreeWaterTiezhiPokerUnitCards pucs2) {
		int result = ThreeWaterPokerCardsUtil.comparePokerCardsByPokerNumber(
				pucs1.getFourPcs(), pucs2.getFourPcs());
		if (result != 0) {
			return result;
		} else {
			return ThreeWaterPokerCardsUtil.comparePokerCardByPokerNumber(
					pucs1.getPc(), pucs2.getPc());
		}
	}

	private int compareTonghuaShun(ThreeWaterTonghuaShunPokerUnitCards pucs1,
			ThreeWaterTonghuaShunPokerUnitCards pucs2) {
		return pucs1.getSequenceUnitCards().getSequenceMax()
				- pucs2.getSequenceUnitCards().getSequenceMax();
	}

	@Override
	public int compare(PokerUnitCards pucs1, PokerUnitCards pucs2) {
		// TODO Auto-generated method stub
		int order1 = ThreeWaterPokerCardsUtil.getNormalOrder(pucs1.getClass());
		int order2 = ThreeWaterPokerCardsUtil.getNormalOrder(pucs2.getClass());
		if (order1 > order2) {
			return 1;
		} else if (order1 < order2) {
			return -1;
		}
		if (pucs1 instanceof ThreeWaterWulongPokerUnitCards) {
			return this.compareWulong((ThreeWaterWulongPokerUnitCards) pucs1,
					(ThreeWaterWulongPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterDuiziPokerUnitCards) {
			return this.compareDuizi((ThreeWaterDuiziPokerUnitCards) pucs1,
					(ThreeWaterDuiziPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterLiangduiPokerUnitCards) {
			return this.compareLiangdui(
					(ThreeWaterLiangduiPokerUnitCards) pucs1,
					(ThreeWaterLiangduiPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterSantiaoPokerUnitCards) {
			return this.compareSantiao((ThreeWaterSantiaoPokerUnitCards) pucs1,
					(ThreeWaterSantiaoPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterTonghuaPokerUnitCards) {
			return this.compareTonghua((ThreeWaterTonghuaPokerUnitCards) pucs1,
					(ThreeWaterTonghuaPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterShunziPokerUnitCards) {
			return this.compareShunzi((ThreeWaterShunziPokerUnitCards) pucs1,
					(ThreeWaterShunziPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterHuluPokerUnitCards) {
			return this.compareHulu((ThreeWaterHuluPokerUnitCards) pucs1,
					(ThreeWaterHuluPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterTiezhiPokerUnitCards) {
			return this.compareTiezhi((ThreeWaterTiezhiPokerUnitCards) pucs1,
					(ThreeWaterTiezhiPokerUnitCards) pucs2);
		}
		if (pucs1 instanceof ThreeWaterTonghuaShunPokerUnitCards) {
			return this.compareTonghuaShun(
					(ThreeWaterTonghuaShunPokerUnitCards) pucs1,
					(ThreeWaterTonghuaShunPokerUnitCards) pucs2);
		}

		return 0;

	}
}
