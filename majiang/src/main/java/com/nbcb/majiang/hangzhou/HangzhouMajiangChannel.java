package com.nbcb.majiang.hangzhou;

import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.channel.MajiangChannel;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;

public class HangzhouMajiangChannel extends MajiangChannel {

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame, Room room) {

		int quan = 0, zuan = 0;
		if (null != lastGame) {
			MajiangGameInfo lastGameInfo = (MajiangGameInfo) lastGame
					.getGameInfo();
			MajiangGame lastMjGame = (MajiangGame) lastGame;

			MajiangHuResults mhrs = lastMjGame.getMajiangHuResults();
			if (mhrs.size() == 1) {
				if (mhrs.getMajiangHuResult(0).getHuType() == MajiangAction.HUANGPAI) {
					zuan = lastGameInfo.getZuan();
				} else {
					zuan = mhrs.getMajiangHuResult(0).getMjPlayer()
							.getPlayerOrder();
				}
			}
		}
		HangzhouMajiangRoomInfo hzRoomInfo = (HangzhouMajiangRoomInfo) room
				.getRoomInfo();
		HangzhouMajiangGameInfo gameInfo = new HangzhouMajiangGameInfo();
		gameInfo.setPlayerNum(roomInfo.getPlayerNum());
		gameInfo.setZuan(zuan);
		gameInfo.setQuan(quan);
		gameInfo.setBaiBanBaida(hzRoomInfo.isBaiBanBaida());
		gameInfo.setBaidaHaoqi(hzRoomInfo.isBaidaHaoqi());
		gameInfo.setBaidaiKaoxiang(hzRoomInfo.isBaidaiKaoxiang());
		gameInfo.setPengTan(hzRoomInfo.isPengTan());
		gameInfo.setSanLao(hzRoomInfo.isSanLao());
		gameInfo.setSanTan(hzRoomInfo.isSanTan());
		gameInfo.setZimoHu(hzRoomInfo.isZimoHu());

		MajiangGame mjGame = new HangzhouMajiangGame(gameInfo);
		return mjGame;
	}

	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		MajiangCards mcs = (MajiangCards) this.applicationContext
				.getBean("majiangAllCardsOfFull");
		mcs.removeCards(mcs.getCardsByUnit(MajiangCard.HUA));
		return mcs;
	}
}
