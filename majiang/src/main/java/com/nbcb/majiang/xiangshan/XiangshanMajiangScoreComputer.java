package com.nbcb.majiang.xiangshan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.game.AbstractMajiangScoreComputer;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.ningbo.fourbaida.NingboMajiang4BaidaScoreComputer;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.rule.judger.hu.MajiangThreeBaoCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class XiangshanMajiangScoreComputer extends AbstractMajiangScoreComputer {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang4BaidaScoreComputer.class);

	private MajiangThreeBaoCalculator majiangThreeBaoCalculator;

	public void setMajiangThreeBaoCalculator(
			MajiangThreeBaoCalculator majiangThreeBaoCalculator) {
		this.majiangThreeBaoCalculator = majiangThreeBaoCalculator;
	}

	private void printMapBao(Map<String, List<MajiangPlayer>> mapBao) {
		if (mapBao == null || mapBao.size() == 0) {
			logger.info("### map bao is null");
			return;
		}
		for (String key : mapBao.keySet()) {
			String str = "account[" + key + "]包了的人:";
			List<MajiangPlayer> set = mapBao.get(key);
			if (set == null || set.size() == 0) {
				str += "没有";
			} else {
				for (MajiangPlayer mp : set) {
					str += mp.getAccount() + " ";
				}
			}
			logger.info("### " + str);
		}

	}

	private Map<String, List<MajiangPlayer>> calBaos(MajiangGame mjGame,
			MajiangHuResults mjHuResults, int huType) {
		Map<String, List<MajiangPlayer>> mapBao = new HashMap<String, List<MajiangPlayer>>();
		for (int i = 0; i < mjHuResults.size(); i++) {
			MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(i);
			List<MajiangPlayer> listPlayer = majiangThreeBaoCalculator
					.calculatorMajiangBaos(mjGame, mjHuResult,
							mjHuResult.getMjPlayer());
			if (listPlayer != null && listPlayer.size() != 0) {
				mapBao.put(mjHuResult.getMjPlayer().getAccount(), listPlayer);
			}
		}
		return mapBao;
	}

	private PlayerScores fangqianghuMany(MajiangHuResults mjHuResults,
			Map<String, List<MajiangPlayer>> mapBao) {
		PlayerScores pss = new DefaultPlayerScores();
		for (int i = 0; i < mjHuResults.size(); i++) {
			MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(i);
			Player winPlayer = mjHuResult.getMjPlayer();
			int winFan = mjHuResult.getFan();
			List<MajiangPlayer> listPlayer = mapBao.get(winPlayer.getAccount());
			
			if (mjHuResult.getAttribute("FANGQIANGMOFRONTHU") != null) {
				if (listPlayer == null) {
					listPlayer = new ArrayList<MajiangPlayer>();
				}
				listPlayer.add(mjHuResult.getMjLosePlayer());
				if (listPlayer != null && listPlayer.size() > 0) {
					for (MajiangPlayer mp : listPlayer) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, mp, winFan * 5));
					}
				}
			}else{
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						mjHuResult.getMjLosePlayer(), winFan * 2));
				if (listPlayer != null && listPlayer.size() > 0) {
					for (MajiangPlayer mp : listPlayer) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, mp, winFan * 2));
					}
				}
			}
		}
		return pss;
	}

	private PlayerScores qiangganghuMany(MajiangHuResults mjHuResults,
			Map<String, List<MajiangPlayer>> mapBao) {
		PlayerScores pss = new DefaultPlayerScores();
		for (int i = 0; i < mjHuResults.size(); i++) {
			MajiangHuResult mjHuResult = mjHuResults.getMajiangHuResult(i);
			Player winPlayer = mjHuResult.getMjPlayer();
			Player losePlayer = mjHuResult.getMjLosePlayer();
			int winFan = mjHuResult.getFan();
			List<MajiangPlayer> listBao = mapBao.get(winPlayer.getAccount());

			pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
					losePlayer, winFan * 5));
			if (listBao != null && listBao.size() != 0) {
				for (Player baoPlayer : listBao) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							baoPlayer, winFan * 5));
				}
			}
		}
		return pss;
	}

	private PlayerScores computerScoreMany(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {

		int huType = mjHuResults.getMajiangHuResult(0).getHuType();
		if (huType != MajiangAction.FANGQIANGHU
				&& huType != MajiangAction.QIANGGANGHU) {
			return null;
		}
		Map<String, List<MajiangPlayer>> mapBao = this.calBaos(mjGame,
				mjHuResults, huType);
		this.printMapBao(mapBao);
		if (huType == MajiangAction.FANGQIANGHU) {
			return this.fangqianghuMany(mjHuResults, mapBao);
		} else {
			return this.qiangganghuMany(mjHuResults, mapBao);
		}
	}

	private PlayerScores computerScoreSingle(MajiangGame mjGame,
			MajiangHuResult mjHuResult) {

		PlayerScores pss = new DefaultPlayerScores();

		MajiangPlayer winPlayer = mjHuResult.getMjPlayer();
		int winFan = mjHuResult.getFan();
		List<MajiangPlayer> listPlayer = majiangThreeBaoCalculator
				.calculatorMajiangBaos(mjGame, mjHuResult, winPlayer);

		if (mjHuResult.getHuType() == MajiangAction.MOFRONTHU
				|| mjHuResult.getHuType() == MajiangAction.MOBACKHU) {

			if (mjHuResult.getHuType() == MajiangAction.MOBACKHU) {
				MajiangPlayer daPlayer = this.isMingGangHu(mjGame, mjHuResult);
				if (daPlayer != null) {
					if (listPlayer == null) {
						listPlayer = new ArrayList<MajiangPlayer>();
					}
					listPlayer.add(daPlayer);
				}
			}
			if (listPlayer != null && listPlayer.size() > 0) {
				for (Player p : listPlayer) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							p, winFan * 5));
				}
			} else {
				MajiangPlayer nextPlayer = winPlayer;
				while ((nextPlayer = (MajiangPlayer) mjGame
						.nextPlayer(nextPlayer)) != winPlayer) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							nextPlayer, winFan * 1));
				}
			}
		} else if (mjHuResult.getHuType() == MajiangAction.FANGQIANGHU) {
			if (mjHuResult.getAttribute("FANGQIANGMOFRONTHU") != null) {
				if (listPlayer == null) {
					listPlayer = new ArrayList<MajiangPlayer>();
				}
				listPlayer.add(mjHuResult.getMjLosePlayer());
				if (listPlayer != null && listPlayer.size() > 0) {
					for (MajiangPlayer mp : listPlayer) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, mp, winFan * 5));
					}
				}
			} else {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						mjHuResult.getMjLosePlayer(), winFan * 2));
				if (listPlayer != null && listPlayer.size() > 0) {
					for (MajiangPlayer mp : listPlayer) {
						pss.addWinLosePlayerScore(new WinLosePlayerScore(
								winPlayer, mp, winFan * 2));
					}
				}
			}
		} else {
			pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
					mjHuResult.getMjLosePlayer(), winFan * 5));
			if (listPlayer != null && listPlayer.size() > 0) {
				for (MajiangPlayer mp : listPlayer) {
					pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
							mp, winFan * 5));
				}
			}
		}
		return pss;
	}

	@Override
	protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {

		if (mjHuResults.size() == 1) {
			return this.computerScoreSingle(mjGame,
					mjHuResults.getMajiangHuResult(0));
		} else {
			return this.computerScoreMany(mjGame, mjHuResults);
		}
	}

}
