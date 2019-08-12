package com.nbcb.majiang.card;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangPengUnitCards extends MajiangUnitCards {

	private MajiangCard mjDaCard;

	private MajiangPlayer mjDaPlayer;

	public MajiangCard getMjDaCard() {
		return mjDaCard;
	}

	public MajiangPlayer getMjDaPlayer() {
		return mjDaPlayer;
	}

	public MajiangPengUnitCards(MajiangPlayer mjDaPlayer, MajiangCard mjDaCard,
			MajiangCard card1, MajiangCard card2) {
		this.addTailCard(mjDaCard);
		this.addTailCard(card1);
		this.addTailCard(card2);

		this.mjDaPlayer = mjDaPlayer;
		this.mjDaCard = mjDaCard;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "碰";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		int cn = this.mjDaCard.getNumber();
		int cns[] = this.toNumberArray();
		map.put("a", "peng");
		map.put("cns", this.orderedCardNumber(cns, cn));
		map.put("who", this.mjDaPlayer.getPlayerOrder());
		map.put("cn", cn);
		return map;
	}
}
