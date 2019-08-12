package com.nbcb.majiang.helper;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.Actions;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class DefaultMajiangGangHelper implements MajiangGangHelper {

	@Override
	public int sequenceGangSize(MajiangGame mjGame, MajiangPlayer mjPlayer,
			int fromIndex, boolean anGang, boolean mingGang, boolean xianGang,
			boolean hua) {

		int totalSize = 0;

		MajiangPlayer mp = mjPlayer;
		Actions historyActions = mjGame.getHistoryActions();

		int index = fromIndex;
		Action action = historyActions.getLastActionByIndex(index);

		if (!this.samePlayerAndType(action, mp, MajiangAction.MOBACK)) {
			return 0;
		}

		index += 1;
		boolean shoot = false;
		while (true) {
			action = historyActions.getLastActionByIndex(index);
			if (action == null) {
				break;
			}
			if (action.getType() == MajiangAction.GEN) {
				index += 1;
				continue;
			}
			if (action.getType() == MajiangAction.NO
					&& null != action.getAttribute("bugen")) {
				index += 1;
				continue;
			}
			if (action.getType() == MajiangAction.MINGGANG
					&& action.getPlayer() == mp && mingGang) {
				totalSize += 1;
				shoot = true;
			}

			if (action.getType() == MajiangAction.ANGANG
					&& action.getPlayer() == mp && anGang) {
				totalSize += 1;
				shoot = true;
			}

			if (action.getType() == MajiangAction.XIANGANG
					&& action.getPlayer() == mp && xianGang) {
				totalSize += 1;
				shoot = true;
			}

			if (action.getType() == MajiangAction.DAHUA
					&& action.getPlayer() == mp && hua) {
				shoot = true;
			}
			if (shoot) {
				Action beforeAction = historyActions
						.getLastActionByIndex(index + 1);
				if (!this.samePlayerAndType(beforeAction, mp,
						MajiangAction.MOBACK)) {
					break;
				}
				index += 2;
			} else {
				break;
			}
		}
		return totalSize;
	}

	@Override
	public MajiangPlayer isMingGangDa(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int fromIndex) {
		MajiangPlayer mp = mjPlayer;
		Actions historyActions = mjGame.getHistoryActions();

		int index = fromIndex;
		Action action = historyActions.getLastActionByIndex(index);
		if (!this.samePlayerAndType(action, mp, MajiangAction.MOBACK)) {
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
				MajiangMingGangUnitCards ucs = (MajiangMingGangUnitCards) mp
						.getMajiangMiddleCards()
						.getLatestNonHuaMajiangUnitCards();
				return ucs.getMjDaPlayer();
			}
			if (action.getType() == MajiangAction.GEN) {
				index += 1;
				continue;
			}
			if (action.getType() == MajiangAction.NO
					&& null != action.getAttribute("bugen")) {
				index += 1;
				continue;
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

	private boolean samePlayerAndType(Action action, Player player, int type) {
		if (action == null) {
			return false;
		}
		return action.getPlayer() == player && action.getType() == type;
	}

}
