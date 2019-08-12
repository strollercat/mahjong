package com.nbcb.majiang.rule.judger.hu.finder;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;

public class CombinedMajiangHuFinder implements NonThreeTwoMajiangHuFinder {

	private List<NonThreeTwoMajiangHuFinder> huFinders;

	public void setHuFinders(List<NonThreeTwoMajiangHuFinder> huFinders) {
		this.huFinders = huFinders;
	}

	@Override
	public List<MajiangHuCards> findLegalHuCards(
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard) {
		List<MajiangHuCards> listRet = new ArrayList<MajiangHuCards>();
		for (NonThreeTwoMajiangHuFinder huFinder : huFinders) {
			List<MajiangHuCards> list = huFinder.findLegalHuCards(
					listMiddleMjUnitCards, mjCards, mjCard);
			if (list != null && list.size() != 0) {
				listRet.addAll(list);
			}
		}
		return listRet;
	}
}
