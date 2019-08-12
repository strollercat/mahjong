package com.nbcb.majiang.rule.judger.hu;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangThreePassiveBao implements MajiangBaoCalculator {

	private MajiangBaoCalculator majiangThreeActiveBao;

	public void setMajiangThreeActiveBao(
			MajiangBaoCalculator majiangThreeActiveBao) {
		this.majiangThreeActiveBao = majiangThreeActiveBao;
	}

	@Override
	public List<MajiangPlayer> calculatorMajiangBaos(MajiangGame mjGame,
			MajiangHuResult mjHuResult, MajiangPlayer mjPlayer) {
		// TODO Auto-generated method stub

		List<MajiangPlayer> listRetPlayer = new ArrayList<MajiangPlayer>();

		MajiangPlayer mjNextPlayer = mjPlayer;
		while ((mjNextPlayer = (MajiangPlayer) mjGame.nextPlayer(mjNextPlayer)) != mjPlayer) {
			List<MajiangPlayer> listPlayer = majiangThreeActiveBao
					.calculatorMajiangBaos(mjGame, mjHuResult, mjNextPlayer);
			if (listPlayer!=null && listPlayer.contains(mjPlayer)) {
				listRetPlayer.add(mjNextPlayer);
			}
		}
		return listRetPlayer.size() == 0 ? null : listRetPlayer;

	}
}
