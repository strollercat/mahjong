package com.nbcb.majiang.helper;

import com.nbcb.majiang.user.MajiangPlayer;

public class AllTanShuCalculator implements TanShuCalculator {

	private TanShuCalculator chiTanShuCalculator;

	private TanShuCalculator pengGangTanShuCalculator;

	public void setChiTanShuCalculator(TanShuCalculator chiTanShuCalculator) {
		this.chiTanShuCalculator = chiTanShuCalculator;
	}

	public void setPengGangTanShuCalculator(
			TanShuCalculator pengGangTanShuCalculator) {
		this.pengGangTanShuCalculator = pengGangTanShuCalculator;
	}

	@Override
	public int calculatorTanShu(MajiangPlayer actionPlayer,
			MajiangPlayer daPlayer) {
		return this.chiTanShuCalculator
				.calculatorTanShu(actionPlayer, daPlayer)
				+ this.pengGangTanShuCalculator.calculatorTanShu(actionPlayer,
						daPlayer);
	}

}
