package com.nbcb.majiang.hongzhong;

import java.util.List;

import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.AbstractMajiangScoreComputer;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public class HongzhongMajiangScoreComputer extends AbstractMajiangScoreComputer {

	private PlayerScores computerScoreZimo(MajiangGame mjGame,
			MajiangHuResult mjHuResult, int diScore) {
		PlayerScores pss = new DefaultPlayerScores();
		int maima = (Integer) mjHuResult
				.getAttribute(MajiangHuResult.ZHONGMANUMBER);
		Player winPlayer = mjHuResult.getMjPlayer();

		int winScore = diScore * 2;
		winScore += maima * diScore * 2;

		pss.addPlayerScores(this.giveOtherPlayerScoreToWinPlayer(mjGame,
				winPlayer, winScore));
		return pss;
	}

	private PlayerScores computerScoreQiangGang(MajiangGame mjGame,
			MajiangHuResult mjHuResult, int diScore) {
		PlayerScores pss = new DefaultPlayerScores();
		int maima = (Integer) mjHuResult
				.getAttribute(MajiangHuResult.ZHONGMANUMBER);
		Player winPlayer = mjHuResult.getMjPlayer();
		int winScore = diScore * 2;
		winScore += maima * diScore * 2;
		pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer, mjHuResult
				.getMjLosePlayer(), winScore * 3));
		return pss;

	}

	private PlayerScores computerExtraGang(MajiangGame mjGame, int diScore) {
		PlayerScores pss = new DefaultPlayerScores();
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			pss.addPlayerScores(this.computerExtraGang(mjGame,
					(MajiangPlayer) mjGame.getPlayerByIndex(i), diScore));
		}
		return pss;
	}

	private PlayerScores computerExtraGang(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int diScore) {

		PlayerScores pss = new DefaultPlayerScores();

		MajiangMiddleCards mjMiddleCards = mjPlayer.getMajiangMiddleCards();
		List<MajiangUnitCards> listUcs = mjMiddleCards.getListUnitCards();
		if (listUcs == null || listUcs.size() == 0) {
			return null;
		}
		for (MajiangUnitCards ucs : listUcs) {
			if (ucs instanceof MajiangAnGangUnitCards) {
				pss.addPlayerScores(this.giveOtherPlayerScoreToWinPlayer(
						mjGame, mjPlayer, diScore * 2));
			} else if (ucs instanceof MajiangXianGangUnitCards) {
				pss.addPlayerScores(this.giveOtherPlayerScoreToWinPlayer(
						mjGame, mjPlayer, diScore * 1));
			} else if (ucs instanceof MajiangMingGangUnitCards) {
				MajiangMingGangUnitCards mgUcs = (MajiangMingGangUnitCards) ucs;
				pss.addWinLosePlayerScore(new WinLosePlayerScore(mjPlayer,
						mgUcs.getMjDaPlayer(), diScore * 3));
			}
		}
		if (mjGame.getMajiangHuResults().getMajiangHuResult(0).getHuType() == MajiangAction.QIANGGANGHU) {
			if (mjGame.getMajiangHuResults().getMajiangHuResult(0)
					.getMjLosePlayer() == mjPlayer) {
				pss.addPlayerScores(this.giveOtherPlayerScoreToWinPlayer(
						mjGame, mjPlayer, -diScore * 1));
			}
		}
		return pss;
	}

	private void setHuDetail(MajiangGame mjGame, MajiangHuResult mjHuResult) {

		String details = "";

		int huType = mjHuResult.getHuType();
		if (huType == MajiangAction.QIANGGANGHU) {
			details = "拉杠 ";
		} else {
			details = "自摸 ";
		}

		details += "(买马+"
				+ (Integer) mjHuResult
						.getAttribute(MajiangHuResult.ZHONGMANUMBER) + " ";

//		MajiangPlayer mjPlayer = mjHuResult.getMjPlayer();
//
//		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
//			MajiangPlayer tmpPlayer = (MajiangPlayer)mjGame.getPlayerByIndex(i);
//			if(tmpPlayer == mjPlayer){
//				continue;
//			}
//		}
//		MajiangMiddleCards mjMiddleCards = mjPlayer.getMajiangMiddleCards();
//		List<MajiangUnitCards> listUcs = mjMiddleCards.getListUnitCards();
//		if (listUcs == null || listUcs.size() == 0) {
//			return;
//		}
//		int anGang = 0;
//		int xianGang = 0;
//		int mingGang = 0;
//		for (MajiangUnitCards ucs : listUcs) {
//			if (ucs instanceof MajiangAnGangUnitCards) {
//				anGang += 1;
//			} else if (ucs instanceof MajiangXianGangUnitCards) {
//				xianGang += 1;
//			} else if (ucs instanceof MajiangMingGangUnitCards) {
//				mingGang += 1;
//			}
//		}
//		if (mingGang != 0) {
//			details += ("包杠+" + mingGang + " ");
//		}
//		if (anGang != 0) {
//			details += ("暗杠+" + anGang + " ");
//		}
//		if (xianGang != 0) {
//			details += ("风险杠+" + xianGang + " ");
//		}

		details += ")";
		mjHuResult.setDetails(details);
	}

	@Override
	protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults) {

		for (int i = 0; i < mjHuResults.size(); i++) {
			MajiangHuResult huResult = mjHuResults.getMajiangHuResult(i);
			this.setHuDetail(mjGame, huResult);
		}

		PlayerScores pss = new DefaultPlayerScores();
		HongzhongMajiangGameInfo hongzhongMajiangGameInfo = (HongzhongMajiangGameInfo) mjGame
				.getGameInfo();
		for (int i = 0; i < mjHuResults.size(); i++) {
			MajiangHuResult huResult = mjHuResults.getMajiangHuResult(i);
			if (huResult.getHuType() == MajiangAction.QIANGGANGHU) {
				pss.addPlayerScores(this.computerScoreQiangGang(mjGame,
						huResult, hongzhongMajiangGameInfo.getDiScore()));
			} else {
				pss.addPlayerScores(this.computerScoreZimo(mjGame, huResult,
						hongzhongMajiangGameInfo.getDiScore()));
			}
		}
		pss.addPlayerScores(this.computerExtraGang(mjGame,
				hongzhongMajiangGameInfo.getDiScore()));
		return pss;
	}

}
