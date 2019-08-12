package com.nbcb.majiang.hongzhong;

import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.channel.MajiangChannel;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;
import com.nbcb.majiang.user.MajiangPlayer;

public class HongzhongMajiangChannel extends MajiangChannel {

	private boolean inPlayers(Player[] players, Player player) {
		for (int i = 0; i < players.length; i++) {
			if (player == players[i]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame, Room room) {

		HongzhongMajiangRoomInfo hongzhongMajiangRoomInfo = (HongzhongMajiangRoomInfo) roomInfo;

		int quan = 0, zuan = 0;
		if (null != lastGame) {
			MajiangGame lastMjGame = (MajiangGame) lastGame;

			MajiangHuResults mhrs = lastMjGame.getMajiangHuResults();
			if (mhrs.size() == 1) {

				if (mhrs.getMajiangHuResult(0).getHuType() == MajiangAction.HUANGPAI) {
					zuan = lastMjGame.nextPlayer(lastMjGame.getDealer())
							.getPlayerOrder();
				} else {
					MajiangPlayer huPlayer = mhrs.getMajiangHuResult(0)
							.getMjPlayer();
					zuan = huPlayer.getPlayerOrder();
				}
			} else if (mhrs.size() >= 2) {
				MajiangPlayer losePlayer = mhrs.getMajiangHuResult(0)
						.getMjLosePlayer();
				Player[] players = new Player[mhrs.size()];
				for (int i = 0; i < mhrs.size(); i++) {
					players[i] = mhrs.getMajiangHuResult(i).getMjPlayer();
				}
				Player nextPlayer = losePlayer;
				while (true) {
					nextPlayer = lastMjGame.nextPlayer(nextPlayer);
					if (this.inPlayers(players, nextPlayer)) {
						zuan = nextPlayer.getPlayerOrder();
						break;
					}
				}
			}
		}
		HongzhongMajiangGameInfo gameInfo = new HongzhongMajiangGameInfo();
		gameInfo.setPlayerNum(hongzhongMajiangRoomInfo.getPlayerNum());
		gameInfo.setZuan(zuan);
		gameInfo.setQuan(quan);
		gameInfo.setDiScore(hongzhongMajiangRoomInfo.getDiScore());

		HongzhongMajiangGame hongzhongGame = new HongzhongMajiangGame(gameInfo);
		return hongzhongGame;
	}

	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		MajiangCards mcs = (MajiangCards) this.applicationContext
				.getBean("majiangAllCardsOfFull");
		mcs.removeCards(mcs.getCardsByUnit(MajiangCard.HUA));
		mcs.removeCards(mcs.getCardsByUnit(MajiangCard.DNXB));
		mcs.removeCards(mcs.findCardsByType("发"));
		mcs.removeCards(mcs.findCardsByType("白"));
		return mcs;
	}
}
