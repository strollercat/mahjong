package com.nbcb.majiang.card;

import java.util.Map;

public class MajiangAnChiUnitCards extends MajiangUnitCards {
	
	public MajiangAnChiUnitCards(MajiangCard mc1, MajiangCard mc2, MajiangCard mc3) {
		this.addTailCard(mc1);
		this.addTailCard(mc2);
		this.addTailCard(mc3);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "暗吃";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		return null;
	}
}
