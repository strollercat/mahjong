package com.nbcb.majiang.rule.judger.hu.finder;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.card.MajiangAllBaidaUnitCards;
import com.nbcb.majiang.card.MajiangAllDongUnitCards;
import com.nbcb.majiang.card.MajiangAllTiaoUnitCards;
import com.nbcb.majiang.card.MajiangAllWanUnitCards;
import com.nbcb.majiang.card.MajiangAllZiUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.card.MajiangUnitCards;

public class DadahuMajiangHuFinder implements NonThreeTwoMajiangHuFinder {

	private static final Logger logger = LoggerFactory
			.getLogger(DadahuMajiangHuFinder.class);

	private boolean repeat(MajiangCards mcs) {
		for (int i = 0; i < mcs.size(); i++) {
			for (int j = 0; j < mcs.size(); j++) {
				if (i == j) {
					continue;
				}
				if (mcs.getCard(i).getType().equals(mcs.getCard(j).getType())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean sequence(MajiangCards mcs) {
		for (int i = 0; i < mcs.size(); i++) {
			for (int j = 0; j < mcs.size(); j++) {
				if (i == j) {
					continue;
				}
				int c1 = Integer.parseInt(mcs.getCard(i).getType()
						.substring(0, 1));
				int c2 = Integer.parseInt(mcs.getCard(j).getType()
						.substring(0, 1));
				if (Math.abs(c1 - c2) <= 2) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<MajiangHuCards> findLegalHuCards(
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard) {
//		logger.info("### dadahuFinder");
		if (mjCards.size() != 14) {
			return null;
		}
		MajiangAllZiUnitCards mjAllZi = new MajiangAllZiUnitCards();
		MajiangAllWanUnitCards mjAllWan = new MajiangAllWanUnitCards();
		MajiangAllTiaoUnitCards mjAllTiao = new MajiangAllTiaoUnitCards();
		MajiangAllDongUnitCards mjAllDong = new MajiangAllDongUnitCards();
		MajiangAllBaidaUnitCards mjAllBaida = new MajiangAllBaidaUnitCards();
		for (int i = 0; i < mjCards.size(); i++) {
			MajiangCard mc = (MajiangCard) mjCards.getCard(i);
			if (mc.isBaida()) {
				mjAllBaida.addHeadCard(mc);
			} else if (mc.isUnit(MajiangCard.DNXB)
					|| mc.isUnit(MajiangCard.ZFB)) {
				mjAllZi.addHeadCard(mc);
			} else if (mc.isUnit(MajiangCard.TIAO)) {
				mjAllTiao.addHeadCard(mc);
			} else if (mc.isUnit(MajiangCard.DONG)) {
				mjAllDong.addHeadCard(mc);
			} else if (mc.isUnit(MajiangCard.WAN)) {
				mjAllWan.addHeadCard(mc);
			}
		}
		// logger.info("### mjAllZi[" + mjAllZi + "]mjAllWan[" + mjAllWan
		// + "]mjAllTiao[" + mjAllTiao + "]mjAllDong[" + mjAllDong
		// + "]mjAllBaida[" + mjAllBaida + "]");
		if (mjAllDong.size() > 3 || mjAllTiao.size() > 3 || mjAllWan.size() > 3) {
			logger.info("### dayu3");
			return null;
		}
		if (this.repeat(mjAllZi)) {
			logger.info("### zi repeat");
			return null;
		}
		if (this.sequence(mjAllWan) || this.sequence(mjAllTiao)
				|| this.sequence(mjAllDong)) {
			logger.info("### sequence");
			return null;
		}
		List<MajiangHuCards> listRet = new ArrayList<MajiangHuCards>();
		MajiangHuCards mjHuCards = new MajiangHuCards();
		listRet.add(mjHuCards);

		mjHuCards.setMjHuCard(mjCard);
		mjHuCards.addMajiangUnitCards(mjAllZi);
		mjHuCards.addMajiangUnitCards(mjAllWan);
		mjHuCards.addMajiangUnitCards(mjAllTiao);
		mjHuCards.addMajiangUnitCards(mjAllDong);
		mjHuCards.addMajiangUnitCards(mjAllBaida);
		mjHuCards.setHuFinderType(HuFinderEnum.DADAHU);
		return listRet;
	}
}
