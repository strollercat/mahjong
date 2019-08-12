package com.nbcb.majiang.ningbo.sevenbaida;

import com.nbcb.core.action.Actions;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangQiangPengActionCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang7BaidaPengActionCalculator extends
		MajiangQiangPengActionCalculator {


	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub

		Actions actions = super.calculateActionInner(mjGame, mjAction);

		NingboMajiang7BaidaGame ningboMajiang7BaidaGame = (NingboMajiang7BaidaGame) mjGame;

		if (ningboMajiang7BaidaGame.isBao()) { // 已经是包的状态，直接下去了
			return actions;
		}

		MajiangPlayer actionPlayer = (MajiangPlayer) mjAction.getPlayer();
		MajiangPengUnitCards mucs = (MajiangPengUnitCards) actionPlayer
				.getMajiangMiddleCards().getLatestMajiangUnitCards();
		MajiangPlayer daPlayer = mucs.getMjDaPlayer();

		int tanShu = allTanShuCalculator.calculatorTanShu(actionPlayer,
				daPlayer);
		if (tanShu == 3) {
			ningboMajiang7BaidaGame.setBao(true);

			String pengAccount = actionPlayer.getAccount();
			ningboMajiang7BaidaGame.putGen(pengAccount, true);

			String daAccount = daPlayer.getAccount();
			ningboMajiang7BaidaGame.putGen(daAccount, true);

			int total = ningboMajiang7BaidaGame.getActivePlayers();
			for (int i = 0; i < total; i++) {
				MajiangPlayer mp = (MajiangPlayer) ningboMajiang7BaidaGame
						.getPlayerByIndex(i);
				if (mp.getAccount().equals(daAccount)) {
					continue;
				}
				if (mp.getAccount().equals(pengAccount)) {
					continue;
				}
				actions.addAction(new MajiangAction(mp, MajiangAction.GEN,
						null, true));
			}
		}
		return actions;
	}
}
