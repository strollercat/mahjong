package com.nbcb.majiang.fenhua;

import java.util.List;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.room.Room;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.AbstractMajiangScoreComputer;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangBaoCalculator;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public class FenhuaMajiangScoreComputer extends AbstractMajiangScoreComputer {

	private MajiangBaoCalculator majiangBaoCalculator;

	public void setMajiangBaoCalculator(
			MajiangBaoCalculator majiangBaoCalculator) {
		this.majiangBaoCalculator = majiangBaoCalculator;
	}

	private PlayerScores computerScore(MajiangGame mjGame,
			MajiangHuResult mjHuResult) {

		PlayerScores pss = new DefaultPlayerScores();

		MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
		int winFan = mjHuResult.getFan();

		List<MajiangPlayer> listPlayer = majiangBaoCalculator
				.calculatorMajiangBaos(mjGame, mjHuResult, winPlayer);

		int huType = mjHuResult.getHuType();
		// 包的状态
		if (listPlayer != null && listPlayer.size() != 0) {
			MajiangPlayer losePlayer = listPlayer.get(0);

			if (huType == MajiangAction.MOFRONTHU
					|| huType == MajiangAction.MOBACKHU
					|| huType == MajiangAction.QIANGGANGHU) {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						losePlayer, winFan * 3));
			} else {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						losePlayer, winFan * 2));
			}
		} else { // 未包
			if (huType == MajiangAction.MOFRONTHU
					|| huType == MajiangAction.MOBACKHU) {
				MajiangPlayer nextPlayer = winPlayer;
				while ((nextPlayer = (MajiangPlayer) mjGame
						.nextPlayer(nextPlayer)) != winPlayer) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							nextPlayer, winFan * 1));
				}
			} else if (huType == MajiangAction.QIANGGANGHU) {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						mjHuResult.getMjLosePlayer(), winFan * 3));
			} else {
				MajiangPlayer nextPlayer = winPlayer;
				while ((nextPlayer = (MajiangPlayer) mjGame
						.nextPlayer(nextPlayer)) != winPlayer) {
					if (nextPlayer == mjHuResult.getMjLosePlayer()) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, nextPlayer, winFan * 1));
					} else {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, nextPlayer, (int) (winFan * 0.5)));
					}
				}
			}
		}
		return pss;
	}

	/**
	 * 冲刺麻将的分数计算方法
	 * 
	 * @param mjGame
	 * @param mjHuResult
	 * @return
	 */
	private PlayerScores computerScoreChongci(MajiangGame mjGame,
			MajiangHuResult mjHuResult) {

		PlayerScores pss = new DefaultPlayerScores();
		Room room = mjGame.getRoom();
		MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
		int winFan = mjHuResult.getFan();

		List<MajiangPlayer> listPlayer = majiangBaoCalculator
				.calculatorMajiangBaos(mjGame, mjHuResult, winPlayer);

		int huType = mjHuResult.getHuType();
		// 包的状态
		if (listPlayer != null && listPlayer.size() != 0) {
			MajiangPlayer losePlayer = listPlayer.get(0);

			if (huType == MajiangAction.MOFRONTHU
					|| huType == MajiangAction.MOBACKHU
					|| huType == MajiangAction.QIANGGANGHU) {
				int money = winFan * 3;
				int loseMoney = room.getPlayerScoreByAccount(losePlayer
						.getAccount());
				money = money > loseMoney ? loseMoney : money;
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						losePlayer, money));
			} else {
				int money = winFan * 2;
				int loseMoney = room.getPlayerScoreByAccount(losePlayer
						.getAccount());
				money = money > loseMoney ? loseMoney : money;
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						losePlayer, winFan * 2));
			}
		} else { // 未包
			if (huType == MajiangAction.MOFRONTHU
					|| huType == MajiangAction.MOBACKHU) {
				MajiangPlayer nextPlayer = winPlayer;
				while ((nextPlayer = (MajiangPlayer) mjGame
						.nextPlayer(nextPlayer)) != winPlayer) {
					int money = winFan;
					int loseMoney = room.getPlayerScoreByAccount(nextPlayer
							.getAccount());
					money = money > loseMoney ? loseMoney : money;
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							nextPlayer, money));
				}
			} else if (huType == MajiangAction.QIANGGANGHU) {
				int money = winFan * 3;
				int loseMoney = room.getPlayerScoreByAccount(mjHuResult
						.getMjLosePlayer().getAccount());
				money = money > loseMoney ? loseMoney : money;
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						mjHuResult.getMjLosePlayer(), money));
			} else {
				MajiangPlayer nextPlayer = winPlayer;
				while ((nextPlayer = (MajiangPlayer) mjGame
						.nextPlayer(nextPlayer)) != winPlayer) {
					if (nextPlayer == mjHuResult.getMjLosePlayer()) {
						int money = winFan;
						int loseMoney = room.getPlayerScoreByAccount(nextPlayer
								.getAccount());
						money = money > loseMoney ? loseMoney : money;
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, nextPlayer, money));
					} else {
						int money = (int) (winFan * 0.5);
						int loseMoney = room.getPlayerScoreByAccount(nextPlayer
								.getAccount());
						money = money > loseMoney ? loseMoney : money;
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, nextPlayer, money));
					}
				}
			}
		}
		return pss;
	}

	@Override
	protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {
		// TODO Auto-generated method stub
		if (mjHuResults.size() != 1) {
			return null;
		}
		MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(0);
		FenhuaMajiangRoomInfo fhMjRoomInfo = (FenhuaMajiangRoomInfo) mjGame
				.getRoom().getRoomInfo();
		if (fhMjRoomInfo.isPinghu()) {
			return this.computerScore(mjGame, mjHuResult);
		} else {
			return this.computerScoreChongci(mjGame, mjHuResult);
		}
	}

}
