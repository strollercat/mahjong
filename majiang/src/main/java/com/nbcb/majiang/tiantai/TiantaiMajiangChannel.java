package com.nbcb.majiang.tiantai;

import com.nbcb.core.card.Card;
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

public class TiantaiMajiangChannel extends MajiangChannel {

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame,Room room) {

		TiantaiMajiangRoomInfo tiantaiMajiangRoomInfo = (TiantaiMajiangRoomInfo) roomInfo;

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
			} else if (mhrs.size() == 2) {
				int hu1 = mhrs.getMajiangHuResult(0).getMjPlayer()
						.getPlayerOrder();
				int hu2 = mhrs.getMajiangHuResult(1).getMjPlayer()
						.getPlayerOrder();
				for (int i = 0; i < roomInfo.getPlayerNum(); i++) {
					if (i == hu1 || i == hu2) {
						continue;
					}
					zuan = i;
					break;
				}
			}
		}
		TiantaiMajiangGameInfo gameInfo = new TiantaiMajiangGameInfo();
		gameInfo.setPlayerNum(tiantaiMajiangRoomInfo.getPlayerNum());
		gameInfo.setZuan(zuan);
		gameInfo.setQuan(quan);
		gameInfo.setHasBaida(tiantaiMajiangRoomInfo.isHasBaida());
		gameInfo.setMaxFan(tiantaiMajiangRoomInfo.getMaxFan());

		TiantaiMajiangGame tiantaiGame = new TiantaiMajiangGame(gameInfo);
		return tiantaiGame;
	}

	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		// TODO Auto-generated method stub		
		MajiangCards mcs = (MajiangCards) this.applicationContext
				.getBean("majiangAllCardsOfFull");
		Card[] cards = mcs.getCardsByUnit(MajiangCard.TIAO);
		mcs.removeCards(cards);
		return mcs;
	}
}
