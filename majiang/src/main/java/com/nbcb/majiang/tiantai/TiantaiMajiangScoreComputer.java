package com.nbcb.majiang.tiantai;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.AbstractMajiangScoreComputer;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public class TiantaiMajiangScoreComputer extends AbstractMajiangScoreComputer {

	@Override
	protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {
		// TODO Auto-generated method stub
		PlayerScores pss = new DefaultPlayerScores();
		if (mjHuResults.size() == 2) {
			MajiangHuResult r1 = mjHuResults.getMajiangHuResult(0);
			MajiangHuResult r2 = mjHuResults.getMajiangHuResult(1);
			pss.addWinLosePlayerScore(new WinLosePlayerScore(r1.getMjPlayer(),
					r1.getMjLosePlayer(), r1.getFan()));
			pss.addWinLosePlayerScore(new WinLosePlayerScore(r2.getMjPlayer(),
					r2.getMjLosePlayer(), r2.getFan()));
		} else if (mjHuResults.size() == 1) {
			MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(0);
			MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
			int winFan = mjHuResult.getFan();
			if (mjHuResult.getHuType() == MajiangAction.MOFRONTHU
					|| mjHuResult.getHuType() == MajiangAction.MOBACKHU) {

				MajiangPlayer daPlayer = this.isMingGangHu(mjGame, mjHuResult);
				if (daPlayer != null) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							daPlayer, winFan * 2));
				} else {
					for (int i = 0; i < mjGame.getActivePlayers(); i++) {
						Player losePlayer = mjGame.getPlayerByIndex(i);
						if (winPlayer != losePlayer) {
							pss.addWinLosePlayerScore(new WinLosePlayerScore(
									winPlayer, losePlayer, winFan));
						}
					}
				}
			} else if (mjHuResult.getHuType() == MajiangAction.FANGQIANGHU) {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(mjHuResult
						.getMjPlayer(), mjHuResult.getMjLosePlayer(),
						winFan / 2 * 3));
			} else if (mjHuResult.getHuType() == MajiangAction.QIANGGANGHU) {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(mjHuResult
						.getMjPlayer(), mjHuResult.getMjLosePlayer(),
						winFan * 2));
			}
		}
		return pss;
	}
}
