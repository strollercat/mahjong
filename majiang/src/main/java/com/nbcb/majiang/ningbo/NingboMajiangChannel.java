package com.nbcb.majiang.ningbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.DefaultRoom;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.rule.Rule;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.channel.MajiangChannel;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.ningbo.sevenbaida.NingboMajiang7BaidaGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;

public class NingboMajiangChannel extends MajiangChannel {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiangChannel.class);

	private Rule fourBaidaRule;

	private Rule sevenBaidaRule;

	private Rule threeBaidaRule;

	public void setSevenBaidaRule(Rule sevenBaidaRule) {
		this.sevenBaidaRule = sevenBaidaRule;
	}

	public void setThreeBaidaRule(Rule threeBaidaRule) {
		this.threeBaidaRule = threeBaidaRule;
	}

	public void setFourBaidaRule(Rule fourBaidaRule) {
		this.fourBaidaRule = fourBaidaRule;
	}

	@Override
	public Room createRoom(String account, RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		if (!(roomInfo instanceof NingboMajiangRoomInfo)) {
			logger.error("### NingboMajiangChannel create room illegal");
			return null;
		}
		String id = server.generateId();
		Room ningboMajiangRoom = new DefaultRoom(roomInfo, id);
		ningboMajiangRoom.setChannel(this);
		return ningboMajiangRoom;
	}

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame, Room room) {
		// TODO Auto-generated method stub

		NingboMajiangRoomInfo nbMjRoomInfo = (NingboMajiangRoomInfo) roomInfo;

		int quan = 0, zuan = 0;
		if (null != lastGame) {
			NingboMajiangGameInfo lastNbMjGameInfo = (NingboMajiangGameInfo) lastGame
					.getGameInfo();
			MajiangGame lastMjGame = (MajiangGame) lastGame;
			int lastZuan = lastNbMjGameInfo.getZuan();
			int lastQuan = lastNbMjGameInfo.getQuan();

			if (lastZuan == nbMjRoomInfo.getPlayerNum() - 1) {
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
		NingboMajiangGameInfo nbMjGameInfo = new NingboMajiangGameInfo();
		nbMjGameInfo.setPlayerNum(nbMjRoomInfo.getPlayerNum());
		nbMjGameInfo.setBaida(nbMjRoomInfo.getBaida());
		nbMjGameInfo.setStartFan(nbMjRoomInfo.getStartFan());
		nbMjGameInfo.setHupengqing(nbMjRoomInfo.isHunpengqing());
		nbMjGameInfo.setQuan(quan);
		nbMjGameInfo.setZuan(zuan);
		nbMjGameInfo.setJinHua(nbMjRoomInfo.getJinhua());
		nbMjGameInfo.setYeHua(nbMjRoomInfo.getYehua());
		logger.info("### nbMjGameInfo " + nbMjGameInfo);
		NingboMajiangGame nbMjGame = null;
		if (nbMjGameInfo.getBaida() == 7) {
			nbMjGame = new NingboMajiang7BaidaGame(nbMjGameInfo);
		} else {
			nbMjGame = new NingboMajiangGame(nbMjGameInfo);
		}
		return nbMjGame;
	}

	@Override
	public Rule getRule(RoomInfo roomInfo) {
		if (!(roomInfo instanceof NingboMajiangRoomInfo)) {
			throw new RuntimeException(
					"### NingboMajiangChannel create room illegal");
		}
		NingboMajiangRoomInfo nbMjRoomInfo = (NingboMajiangRoomInfo) roomInfo;
		if (nbMjRoomInfo.getBaida() == 4) {
			return this.fourBaidaRule;
		} else if (nbMjRoomInfo.getBaida() == 7) {
			return this.sevenBaidaRule;
		} else if (nbMjRoomInfo.getBaida() == 3) {
			return this.threeBaidaRule;
		}
		return null;
	}
	
	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		MajiangAllCards macs = (MajiangAllCards) this.applicationContext.getBean("majiangAllCardsOfFull");
		if(roomInfo.getPlayerNum() == 2){
			Card[] cards = macs.getCardsByUnit(MajiangCard.WAN);
			macs.removeCards(cards);
		}
		return macs;
	}

}
