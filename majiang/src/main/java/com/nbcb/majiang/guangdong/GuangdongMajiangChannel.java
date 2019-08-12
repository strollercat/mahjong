package com.nbcb.majiang.guangdong;

import com.nbcb.core.card.Cards;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.hongzhong.HongzhongMajiangChannel;

public class GuangdongMajiangChannel extends HongzhongMajiangChannel {
	
	
	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		MajiangCards mcs = (MajiangCards) this.applicationContext
				.getBean("majiangAllCardsOfFull");
		mcs.removeCards(mcs.getCardsByUnit(MajiangCard.HUA));
		return mcs;
	}
}
