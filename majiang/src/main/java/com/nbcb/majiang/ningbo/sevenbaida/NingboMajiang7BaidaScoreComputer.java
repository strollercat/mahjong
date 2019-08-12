package com.nbcb.majiang.ningbo.sevenbaida;

import java.util.ArrayList;
import java.util.List;

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

public class NingboMajiang7BaidaScoreComputer extends
		AbstractMajiangScoreComputer {

	@Override
	protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {

		NingboMajiang7BaidaGame ningboMajiang7BaidaGame = (NingboMajiang7BaidaGame) mjGame;
		PlayerScores pss = new DefaultPlayerScores();

		if (mjHuResults.size() == 1) { // 只有一个人胡的情况下
			MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(0);
			MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
			int winFan = mjHuResult.getFan();

			if (!ningboMajiang7BaidaGame.isBao()) { // 没有包的情况下

				if (mjHuResult.getHuType() == MajiangAction.QIANGGANGHU) { // 抢杠胡
					Player xianGangPlayer = mjGame.getHistoryActions()
							.getLastActionByType(MajiangAction.XIANGANG)
							.getPlayer();
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							xianGangPlayer, winFan * 5));
				} else { // 没有抢杠胡
					for (int i = 0; i < mjGame.getActivePlayers(); i++) {
						if (winPlayer != mjGame.getPlayerByIndex(i)) {
							pss.addWinLosePlayerScore(new WinLosePlayerScore(
									winPlayer, mjGame.getPlayerByIndex(i),
									winFan));
						}
					}
				}
			} else { // 包的情况下
				for (int i = 0; i < mjGame.getActivePlayers(); i++) {
					Player losePlayer = mjGame.getPlayerByIndex(i);
					if (winPlayer != losePlayer
							&& Boolean.TRUE.equals(ningboMajiang7BaidaGame
									.getGenByAccount(losePlayer.getAccount()))) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, mjGame.getPlayerByIndex(i),
								winFan * 5));
					}
				}
			}
			return pss;
		} else { // 只有抢杠胡的时候才有可能一炮多响
			MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(0);
			if (mjHuResult.getHuType() != MajiangAction.QIANGGANGHU) { // 不可能出现的情况，滚蛋吧
				return null;
			}

			if (!ningboMajiang7BaidaGame.isBao()) { // 没有包的情况下
				Player xianGangPlayer = mjGame.getHistoryActions()
						.getLastActionByType(MajiangAction.XIANGANG)
						.getPlayer();
				for (int i = 0; i < mjHuResults.size(); i++) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(
							mjHuResults.getMajiangHuResult(i).getMjPlayer(),
							xianGangPlayer, mjHuResults.getMajiangHuResult(i)
									.getFan() * 5));
				}
			} else { // 包的情况下
				Player xianGangPlayer = mjGame.getHistoryActions()
						.getLastActionByType(MajiangAction.XIANGANG)
						.getPlayer();
				if (mjHuResults.size() == 3) { // 3个人胡
					for (int i = 0; i < mjHuResults.size(); i++) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								mjHuResults.getMajiangHuResult(i).getMjPlayer(),
								xianGangPlayer, mjHuResults.getMajiangHuResult(
										i).getFan() * 5));
					}
				} else if (mjHuResults.size() == 2) { // 2个人胡，确定一下剩余的人是否跟了
					List<Player> losePlayers = new ArrayList<Player>();
					losePlayers.add(xianGangPlayer);
					Player player1 = mjHuResults.getMajiangHuResult(0).getMjPlayer();
					Player player2 =  mjHuResults.getMajiangHuResult(1).getMjPlayer();
					Player losePlayer1 = xianGangPlayer;
					Player losePlayer2 = null;
					for (int i = 0; i < mjGame.getActivePlayers(); i++) {
						Player tmpPlayer =  mjGame.getPlayerByIndex(i);
						if(tmpPlayer == xianGangPlayer || tmpPlayer  == player1 || tmpPlayer == player2){
							continue;
						}
						if(Boolean.TRUE.equals(ningboMajiang7BaidaGame
								.getGenByAccount(tmpPlayer.getAccount()))){
							losePlayer2 = tmpPlayer;
							break;
						}
					}
					for (int i = 0; i < mjHuResults.size(); i++) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								mjHuResults.getMajiangHuResult(i).getMjPlayer(),
								losePlayer1, mjHuResults.getMajiangHuResult(
										i).getFan() * 5));
					}
					if(losePlayer2 != null){
						for (int i = 0; i < mjHuResults.size(); i++) {
							pss.addWinLosePlayerScore(new WinLosePlayerScore(
									mjHuResults.getMajiangHuResult(i).getMjPlayer(),
									losePlayer2, mjHuResults.getMajiangHuResult(
											i).getFan() * 5));
						}
					}
				} else { // 不可能出现
					return null;
				}

			}
			return pss;
		}
	}

}
