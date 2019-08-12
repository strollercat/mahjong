package com.nbcb.majiang.rule.judger.hu.finder;

import java.util.List;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;

public interface NonThreeTwoMajiangHuFinder {
	
	public List<MajiangHuCards> findLegalHuCards(List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,MajiangCard mjCard);

}
