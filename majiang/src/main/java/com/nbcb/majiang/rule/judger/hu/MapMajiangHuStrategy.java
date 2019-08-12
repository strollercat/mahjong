package com.nbcb.majiang.rule.judger.hu;

import java.util.List;
import java.util.Map;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MapMajiangHuStrategy implements MajiangHuStrategy {

	private Map<String, MajiangHuStrategy> mapHuStrategy;

	public void setMapHuStrategy(Map<String, MajiangHuStrategy> mapHuStrategy) {
		this.mapHuStrategy = mapHuStrategy;
	}

	@Override
	public List<MajiangHuCards> findLegalHuCards(MajiangGame mjGame,
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard) {
		// TODO Auto-generated method stub
		MajiangHuStrategy mjHuStrategy = mapHuStrategy.get(mjGame.getRoom()
				.getRoomInfo().getName());
		return mjHuStrategy.findLegalHuCards(mjGame, listMiddleMjUnitCards,
				mjCards, mjCard);
	}

	@Override
	public List<MajiangHuResult> calHuResultFan(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType,
			List<MajiangHuCards> listMjHuCards) {
		// TODO Auto-generated method stub
		MajiangHuStrategy mjHuStrategy = mapHuStrategy.get(mjGame.getRoom()
				.getRoomInfo().getName());
		return mjHuStrategy.calHuResultFan(mjGame, mjPlayer, huType,
				listMjHuCards);
	}

	@Override
	public MajiangHuResult findLegalHuResult(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {
		// TODO Auto-generated method stub
		MajiangHuStrategy mjHuStrategy = mapHuStrategy.get(mjGame.getRoom()
				.getRoomInfo().getName());
		return mjHuStrategy.findLegalHuResult(mjGame, listHuResult);
	}

}
