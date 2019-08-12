package com.nbcb.majiang.hangzhou;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.AbstractMajiangScoreComputer;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.helper.TanShuCalculator;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public class HangzhouMajiangScoreComputer extends AbstractMajiangScoreComputer {

	private static final Logger logger = LoggerFactory
			.getLogger(HangzhouMajiangScoreComputer.class);

	private TanShuCalculator chiTanShuCalculator;

	private TanShuCalculator pengGangTanShuCalculator;

	public void setChiTanShuCalculator(TanShuCalculator chiTanShuCalculator) {
		this.chiTanShuCalculator = chiTanShuCalculator;
	}

	public void setPengGangTanShuCalculator(
			TanShuCalculator pengGangTanShuCalculator) {
		this.pengGangTanShuCalculator = pengGangTanShuCalculator;
	}

	private List<MajiangPlayer> isChenBao(MajiangGame mjGame,
			MajiangPlayer huPlayer) {
		HangzhouMajiangGameInfo hzMjGameInfo = (HangzhouMajiangGameInfo) mjGame
				.getGameInfo();
		if (!hzMjGameInfo.isSanTan()) {
			return null;
		}

		List<MajiangPlayer> listRet = new ArrayList<MajiangPlayer>();

		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			Player tmpPlayer = mjGame.getPlayerByIndex(i);
			if (tmpPlayer == huPlayer) {
				continue;
			}
			int tanShu = chiTanShuCalculator.calculatorTanShu(huPlayer,
					(MajiangPlayer) tmpPlayer);
			if (hzMjGameInfo.isPengTan()) {
				tanShu += pengGangTanShuCalculator.calculatorTanShu(huPlayer,
						(MajiangPlayer) tmpPlayer);
			}
			if (tanShu >= 3) {
				listRet.add((MajiangPlayer) tmpPlayer);
				continue;
			}
			tanShu = 0;
			tanShu = chiTanShuCalculator.calculatorTanShu(
					(MajiangPlayer) tmpPlayer, huPlayer);
			if (hzMjGameInfo.isPengTan()) {
				tanShu += pengGangTanShuCalculator.calculatorTanShu(
						(MajiangPlayer) tmpPlayer, huPlayer);
			}
			if (tanShu >= 3) {
				listRet.add((MajiangPlayer) tmpPlayer);
			}
		}
		return listRet;
	}

	private int getUnit(MajiangGame mjGame) {
		HangzhouMajiangGame hzMjGame = (HangzhouMajiangGame) mjGame;
		if (hzMjGame.getLaoShu() == 3) {
			return 8;
		}
		return 4;
	}

	private PlayerScores computerScoreSingleOfNoChenBaoZimo(MajiangGame mjGame,
			MajiangHuResult mjHuResult) {
		PlayerScores pss = new DefaultPlayerScores();
		MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
		int winFan = mjHuResult.getFan();
		Player losePlayer = null;
		if (mjHuResult.getMjPlayer() == mjGame.getDealer()) { // 胡的人是庄家
			for (int i = 0; i < mjGame.getActivePlayers(); i++) {
				losePlayer = mjGame.getPlayerByIndex(i);
				if (winPlayer != losePlayer) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							losePlayer, winFan));
				}
			}
		} else { // 胡的人是闲家
			for (int i = 0; i < mjGame.getActivePlayers(); i++) {
				losePlayer = mjGame.getPlayerByIndex(i);
				if (winPlayer != losePlayer) {
					if (losePlayer == mjGame.getDealer()) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, losePlayer, winFan));
					} else {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, losePlayer, winFan
										/ this.getUnit(mjGame)));
					}
				}
			}
		}
		return pss;
	}

	private PlayerScores computerScoreSingleOfChenBaoZimo(MajiangGame mjGame,
			MajiangHuResult mjHuResult, List<MajiangPlayer> chenBaoPlayers) {
		PlayerScores pss = new DefaultPlayerScores();
		MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
		int winFan = mjHuResult.getFan();
		Player losePlayer = null;
		if (mjHuResult.getMjPlayer() == mjGame.getDealer()) { // 胡的人是庄家
			for (MajiangPlayer chenBaoPlayer : chenBaoPlayers) { // 有可能承包了多个闲家
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						chenBaoPlayer, winFan * (mjGame.getActivePlayers() - 1)));
			}
		} else { // 胡的人是闲家
			losePlayer = mjGame.getDealer();
			pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
					losePlayer, winFan));
			pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
					losePlayer, winFan / this.getUnit(mjGame)
							* (mjGame.getActivePlayers() - 2)));
		}
		return pss;
	}

	private PlayerScores computerScoreSingle(MajiangGame mjGame,
			MajiangHuResult mjHuResult) {

		PlayerScores pss = new DefaultPlayerScores();
		MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
		int winFan = mjHuResult.getFan();
		Player losePlayer = mjHuResult.getMjLosePlayer();

		if (mjHuResult.getHuType() == MajiangAction.FANGQIANGHU) {
			pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
					losePlayer, winFan));
			return pss;
		} else {

			List<MajiangPlayer> chenBaoPlayers = this.isChenBao(mjGame,
					winPlayer);

			logger.info("### chenBaoPlayers " + chenBaoPlayers);

			if (chenBaoPlayers == null || chenBaoPlayers.size() == 0) {
				return this.computerScoreSingleOfNoChenBaoZimo(mjGame,
						mjHuResult);
			} else {
				return this.computerScoreSingleOfChenBaoZimo(mjGame,
						mjHuResult, chenBaoPlayers);
			}
		}
	}

	@Override
	protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {
		// TODO Auto-generated method stub
		return this.computerScoreSingle(mjGame,
				mjHuResults.getMajiangHuResult(0));

	}
}
