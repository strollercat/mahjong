package com.nbcb.majiang.helper;

import java.util.List;

import com.nbcb.majiang.card.MajiangChiUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.user.MajiangPlayer;

public class ChiTanShuCalculator implements TanShuCalculator {

	@Override
	public int calculatorTanShu(MajiangPlayer actionPlayer,
			MajiangPlayer daPlayer) {
		// TODO Auto-generated method stub
		List<MajiangUnitCards> listUnitCards = actionPlayer
				.getMajiangMiddleCards().getListUnitCards();
		if (listUnitCards == null || listUnitCards.size() == 0) {
			return 0;
		}
		int chiTotal = 0;
		for (MajiangUnitCards ucs : listUnitCards) {
			if (ucs instanceof MajiangChiUnitCards) {
				MajiangChiUnitCards chiUcs = (MajiangChiUnitCards) ucs;
				if (chiUcs.getMjDaPlayer() == daPlayer) {
					chiTotal += 1;
				}
			}
		}
		return chiTotal;
	}

}
