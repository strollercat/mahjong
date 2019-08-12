package com.nbcb.majiang.card;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangXianGangUnitCards extends MajiangUnitCards {

	private MajiangCard mjDaCard;

	private MajiangPlayer mjDaPlayer;

	public MajiangCard getMjDaCard() {
		return mjDaCard;
	}

	public MajiangPlayer getMjDaPlayer() {
		return mjDaPlayer;
	}

	public MajiangXianGangUnitCards(MajiangPlayer mjDaPlayer, MajiangCard mjDaCard,
			MajiangCard card1, MajiangCard card2, MajiangCard card3,
			MajiangCard card4) {
		this.addTailCard(card1);
		this.addTailCard(card2);
		this.addTailCard(card3);
		this.addTailCard(card4);

		this.mjDaPlayer = mjDaPlayer;
		this.mjDaCard = mjDaCard;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "险杠";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		int cns[] = this.toNumberArray();
		map.put("a", "xiangang");
		map.put("cns", cns);
		map.put("who", -1);
		map.put("cn", -1);
		return map;
	}

}
