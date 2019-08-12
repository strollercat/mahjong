package com.nbcb.majiang.fenhua;

import java.util.List;

import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangBaoCalculator;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangThreeActiveBao;
import com.nbcb.majiang.user.MajiangPlayer;

public class FenhuaMajiangBaoCalculator implements MajiangBaoCalculator {

	private MajiangThreeActiveBao majiangThreeActiveBao;

	private MajiangThreeActiveBao majiangFourActiveBao;

	public void setMajiangFourActiveBao(
			MajiangThreeActiveBao majiangFourActiveBao) {
		this.majiangFourActiveBao = majiangFourActiveBao;
	}

	public void setMajiangThreeActiveBao(
			MajiangThreeActiveBao majiangThreeActiveBao) {
		this.majiangThreeActiveBao = majiangThreeActiveBao;
	}

	@Override
	public List<MajiangPlayer> calculatorMajiangBaos(MajiangGame mjGame,
			MajiangHuResult mjHuResult, MajiangPlayer mjPlayer) {
		// TODO Auto-generated method stub
		List<MajiangPlayer> list = majiangFourActiveBao.calculatorMajiangBaos(
				mjGame, mjHuResult, mjPlayer);
		if (list != null && list.size() > 0) {
			return list;
		}

		list = majiangThreeActiveBao.calculatorMajiangBaos(mjGame, mjHuResult,
				mjPlayer);
		if (list == null || list.size() == 0) {
			return null;
		}
		String details = mjHuResult.getDetails();
		if (details.contains("对对胡") || details.contains("清一色")
				|| details.contains("混一色") || details.contains("清老头")) {
			return list;
		}
		return null;
	}

}
