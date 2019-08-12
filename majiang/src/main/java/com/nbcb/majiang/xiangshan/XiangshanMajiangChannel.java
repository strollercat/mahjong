package com.nbcb.majiang.xiangshan;

import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.channel.MajiangChannel;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;

public class XiangshanMajiangChannel extends MajiangChannel {

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame, Room room) {

		int quan = 0, zuan = 0;
		if (null != lastGame) {
			MajiangGameInfo lastMjGameInfo = (MajiangGameInfo) lastGame
					.getGameInfo();
			MajiangGame lastMjGame = (MajiangGame) lastGame;
			int lastZuan = lastMjGameInfo.getZuan();
			int lastQuan = lastMjGameInfo.getQuan();

			if (lastZuan == roomInfo.getPlayerNum() - 1) {
				zuan = 0;
				quan = lastQuan + 1;
			} else {
				zuan = lastZuan + 1;
				quan = lastQuan;
			}

			Player lastPlayer = lastMjGame.getPlayerByIndex(lastZuan);
			for (int i = 0; i < lastMjGame.getMajiangHuResults().size(); i++) {
				MajiangHuResult mjHuResult = lastMjGame.getMajiangHuResults()
						.getMajiangHuResult(i);
				if (mjHuResult.getHuType() != MajiangAction.HUANGPAI) {
					if (lastPlayer == mjHuResult.getMjPlayer()) {
						zuan = lastZuan;
						quan = lastQuan;
					}
				}
			}
		}
		if (quan >= 4) {
			quan = 0;
		}
		MajiangGameInfo gameInfo = new MajiangGameInfo();
		gameInfo.setPlayerNum(roomInfo.getPlayerNum());
		gameInfo.setQuan(quan);
		gameInfo.setZuan(zuan);
		return new MajiangGame(gameInfo);
	}
}
