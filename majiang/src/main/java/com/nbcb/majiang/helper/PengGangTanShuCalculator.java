package com.nbcb.majiang.helper;

import java.util.List;

import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.user.MajiangPlayer;

public class PengGangTanShuCalculator implements TanShuCalculator {

	@Override
	public int calculatorTanShu(MajiangPlayer actionPlayer,
			MajiangPlayer daPlayer) {
		// TODO Auto-generated method stub
		List<MajiangUnitCards> listUnitCards = actionPlayer.getMajiangMiddleCards()
				.getListUnitCards();
		if (listUnitCards == null || listUnitCards.size() == 0) {
			return 0;
		}
		int pengTotal = 0;
		for (MajiangUnitCards ucs : listUnitCards) {
			if (ucs instanceof MajiangPengUnitCards) {
				MajiangPengUnitCards pengUcs = (MajiangPengUnitCards) ucs;
				if (pengUcs.getMjDaPlayer()== daPlayer) {
					pengTotal += 1;
				}
			} else if (ucs instanceof MajiangMingGangUnitCards) {
				MajiangMingGangUnitCards pengUcs = (MajiangMingGangUnitCards) ucs;
				if (pengUcs.getMjDaPlayer() == daPlayer) {
					pengTotal += 1;
				}
			} else if (ucs instanceof MajiangXianGangUnitCards) {
				MajiangXianGangUnitCards pengUcs = (MajiangXianGangUnitCards) ucs;
				if (pengUcs.getMjDaPlayer() == daPlayer) {
					pengTotal += 1;
				}
			}
		}
		return pengTotal;
	}

}
