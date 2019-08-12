package com.nbcb.majiang.card;

import java.util.Map;

public class MajiangDuiziUnitCards extends MajiangUnitCards {
	public MajiangDuiziUnitCards(MajiangCard mc1,MajiangCard mc2) {
		this.addTailCard(mc1);
		this.addTailCard(mc2);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "对子";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		return null;
	}
}
