package com.nbcb.majiang.rule.judger.hu.finder;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.majiang.card.MajiangAllZiUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.card.MajiangHuaUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;

public class AllziMajiangHuFinder implements NonThreeTwoMajiangHuFinder {

	private boolean isAllZi(MajiangCards mcs) {
		for (int i = 0; i < mcs.size(); i++) {
			MajiangCard mc = (MajiangCard) mcs.getCard(i);
			if (mc.isBaida()) {
				continue;
			}
			if (mc.isUnit(MajiangCard.DNXB)) {
				continue;
			}
			if (mc.isUnit(MajiangCard.ZFB)) {
				continue;
			}
			return false;
		}
		return true;
	}

	@Override
	public List<MajiangHuCards> findLegalHuCards(
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard) {

		List<MajiangHuCards> listHuCards = null;
		MajiangAllZiUnitCards mjAllziUnitCards = new MajiangAllZiUnitCards();
		mjAllziUnitCards.addTailCards(mjCards.toArray());
		for (MajiangUnitCards mucs : listMiddleMjUnitCards) {
			if (!(mucs instanceof MajiangHuaUnitCards)) {
				mjAllziUnitCards.addTailCards(mucs.toArray());
			}
		}
		if (this.isAllZi(mjAllziUnitCards)) {
			MajiangHuCards mjHuCards = new MajiangHuCards();
			mjHuCards.setMjHuCard(mjCard);
			mjHuCards.addMajiangUnitCards(mjAllziUnitCards);
			mjHuCards.setHuFinderType(HuFinderEnum.ALLZI);
			if (listHuCards == null) {
				listHuCards = new ArrayList<MajiangHuCards>();
			}
			listHuCards.add(mjHuCards);
		}
		return listHuCards;
	}

}
