package com.nbcb.majiang.rule.judger.hu;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangThreeBaoCalculator implements
		MajiangBaoCalculator {

	private MajiangBaoCalculator majiangThreeActiveBaoCalculator;

	private MajiangBaoCalculator majiangThreePassiveBaoCalculator;

	public void setMajiangThreeActiveBaoCalculator(
			MajiangBaoCalculator majiangThreeActiveBaoCalculator) {
		this.majiangThreeActiveBaoCalculator = majiangThreeActiveBaoCalculator;
	}

	public void setMajiangThreePassiveBaoCalculator(
			MajiangBaoCalculator majiangThreePassiveBaoCalculator) {
		this.majiangThreePassiveBaoCalculator = majiangThreePassiveBaoCalculator;
	}

	@Override
	public List<MajiangPlayer> calculatorMajiangBaos(MajiangGame mjGame,
			MajiangHuResult mjHuResult, MajiangPlayer mjPlayer) {
		// TODO Auto-generated method stub
		List<MajiangPlayer> retListPlayer = new ArrayList<MajiangPlayer>();

		List<MajiangPlayer> listPlayer = majiangThreeActiveBaoCalculator.calculatorMajiangBaos(mjGame, mjHuResult, mjPlayer);
		if (listPlayer != null && listPlayer.size() != 0) {
			retListPlayer.addAll(listPlayer);
		}

		listPlayer = majiangThreePassiveBaoCalculator.calculatorMajiangBaos(
				mjGame, mjHuResult, mjPlayer);
		if (listPlayer != null && listPlayer.size() != 0) {
			retListPlayer.addAll(listPlayer);
		}
		return retListPlayer.size() == 0 ? null : retListPlayer;

	}

}
