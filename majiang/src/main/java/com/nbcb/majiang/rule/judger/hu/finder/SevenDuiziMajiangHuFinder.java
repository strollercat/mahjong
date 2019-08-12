package com.nbcb.majiang.rule.judger.hu.finder;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.card.MajiangUnitCards;

public class SevenDuiziMajiangHuFinder implements NonThreeTwoMajiangHuFinder {

	@Override
	public List<MajiangHuCards> findLegalHuCards(
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard) {
		if (mjCards.size() != 14) {
			return null;
		}
		MajiangCards copyMcs = new MajiangCards();
		copyMcs.addTailCards(mjCards.toArray());
		List<MajiangUnitCards> listUnitCards = new ArrayList<MajiangUnitCards>();
		if (this.findListHuCards(copyMcs, listUnitCards)) {
			List<MajiangHuCards> listRet = new ArrayList<MajiangHuCards>();
			MajiangHuCards mjHuCards = new MajiangHuCards();
			listRet.add(mjHuCards);

			mjHuCards.setMjHuCard(mjCard);
			for (MajiangUnitCards mucs : listUnitCards) {
				mjHuCards.addMajiangUnitCards(mucs);
			}
			mjHuCards.setHuFinderType(HuFinderEnum.SEVENDUIZI);
			return listRet;
		}
		return null;
	}

	private MajiangDuiziUnitCards findDuiziUnitCards(MajiangCards mjCards,
			MajiangCard mjCard) {
		Card[] cards = mjCards.findCardsByType(mjCard.getType());
		if (cards == null || cards.length == 0) {
			return null;
		} else if (cards.length == 1) {
			MajiangCards baidaCards = mjCards.baidaCards();
			if (baidaCards == null || baidaCards.size() == 0) {
				return null;
			}
			return new MajiangDuiziUnitCards((MajiangCard) cards[0],
					(MajiangCard) baidaCards.getHeadCard());
		} else {
			return new MajiangDuiziUnitCards((MajiangCard) cards[0],
					(MajiangCard) cards[1]);
		}
	}

	private boolean findListHuCards(MajiangCards majiangCards,
			List<MajiangUnitCards> listUnitCards) {

		if (majiangCards.size() == 2) {
			MajiangCard mc1 = (MajiangCard) majiangCards.getHeadCard();
			MajiangCard mc2 = (MajiangCard) majiangCards.getTailCard();
			if (mc1.isBaida()
					|| mc2.isBaida()
					|| majiangCards.getHeadCard().getType()
							.equals(majiangCards.getTailCard().getType())) {
				listUnitCards.add(new MajiangDuiziUnitCards(mc1, mc2));
				return true;
			}
			return false;
		}
		MajiangCard mjCard = (MajiangCard) majiangCards.firstNonBaidaCard();
		MajiangDuiziUnitCards mducs = null;
		if (mjCard == null) { //全是白搭
			MajiangCards  mcs = majiangCards.baidaCards();
			if(mcs == null || mcs.size() < 2 ){
				return false;
			}
			mducs = new MajiangDuiziUnitCards((MajiangCard)mcs.getHeadCard(),(MajiangCard)mcs.getTailCard());
		} else {
			mducs = this.findDuiziUnitCards(majiangCards,
					mjCard);
			if (mducs == null) {
				return false;
			}
		}
		listUnitCards.add(mducs);
		majiangCards.removeCards(mducs.toArray());
		return this.findListHuCards(majiangCards, listUnitCards);
	}
}
