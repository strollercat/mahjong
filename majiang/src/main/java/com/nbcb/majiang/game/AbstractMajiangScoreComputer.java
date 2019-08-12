package com.nbcb.majiang.game;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.game.DefaultPlayerScores;
import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.ScoreComputer;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.helper.MajiangGangHelper;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public abstract class AbstractMajiangScoreComputer implements ScoreComputer {

	protected MajiangGangHelper majiangGangHelper;

	public void setMajiangGangHelper(MajiangGangHelper majiangGangHelper) {
		this.majiangGangHelper = majiangGangHelper;
	}

	abstract protected PlayerScores computerScoreInner(MajiangGame mjGame,
			MajiangHuResults mjHuResults);

	/**
	 * 其他的score给赢的人score
	 * 
	 * @param mjGame
	 * @param winPlayer
	 * @param score
	 * @return
	 */
	protected PlayerScores giveOtherPlayerScoreToWinPlayer(MajiangGame mjGame,
			Player winPlayer, int score) {
		PlayerScores pss = new DefaultPlayerScores();
		for (int i = 0; i < mjGame.getActivePlayers(); i++) {
			Player losePlayer = mjGame.getPlayerByIndex(i);
			if (winPlayer != losePlayer) {
				pss.addWinLosePlayerScore(new WinLosePlayerScore(winPlayer,
						losePlayer, score));
			}
		}
		return pss;
	}

	/**
	 * 判断是不是明杠杠开的杠头开花，包括杠开开花以后摸了花
	 * 
	 * @param mjGame
	 * @param mjHuResults
	 * @return 打牌的玩家
	 */
	protected MajiangPlayer isMingGangHu(MajiangGame mjGame,
			MajiangHuResult huResult) {

		int huType = huResult.getHuType();
		if (huType != MajiangAction.MOBACKHU) {
			return null;
		}
		MajiangPlayer mp = huResult.getMjPlayer();
		Actions historyActions = mjGame.getHistoryActions();
		Action action = historyActions.getLastActionByIndex(1);
		if (action == null || action.getPlayer() != mp
				|| action.getType() != MajiangAction.MOBACKHU) {
			return null;
		}
		int index = 2;
		action = historyActions.getLastActionByIndex(index);
		if (action == null || action.getPlayer() != mp
				|| action.getType() != MajiangAction.MOBACK) {
			return null;
		}
		index += 1;
		while (true) {
			action = historyActions.getLastActionByIndex(index);
			if (action == null) {
				break;
			}
			if (action.getType() == MajiangAction.MINGGANG
					&& action.getPlayer() == mp) {
				return (MajiangPlayer) historyActions.getLastActionByType(
						MajiangAction.DANONHUA).getPlayer();
			}
			if (action.getType() == MajiangAction.DAHUA
					&& action.getPlayer() == mp) {
				Action beforeAction = historyActions
						.getLastActionByIndex(index + 1);
				if (beforeAction == null) {
					break;
				}
				if (beforeAction.getPlayer() == mp
						&& beforeAction.getType() == MajiangAction.MOBACK) {
					index += 2;
					continue;
				}
			}
			break;
		}
		return null;

	}

	@Override
	public PlayerScores computerScore(Game game, Object object) {
		// TODO Auto-generated method stub
		MajiangGame mjGame = (MajiangGame) game;
		MajiangHuResults mjHuResults = (MajiangHuResults) object;
		PlayerScores pss = new DefaultPlayerScores();

		if (mjHuResults == null || mjHuResults.size() == 0) {
			return null;
		}

		if (mjHuResults.size() == 1
				&& mjHuResults.getMajiangHuResult(0).getHuType() == MajiangAction.HUANGPAI) {
			return null;
		}

		return this.computerScoreInner(mjGame, mjHuResults);

	}

}
