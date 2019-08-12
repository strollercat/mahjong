package com.nbcb.majiang.card;

import java.util.HashMap;
import java.util.Map;

public class MajiangHuaUnitCards extends MajiangUnitCards  {

	public MajiangHuaUnitCards() {

	}

	public void addHuaCard(MajiangCard mc) {
		this.addTailCard(mc);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "花";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("a", "hua");
		map.put("cns", this.toNumberArray());
		map.put("who", null);
		map.put("cn", null);
		return map;
	}
}
