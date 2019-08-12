package com.nbcb.majiang.card;

import java.util.HashMap;
import java.util.Map;

import com.nbcb.core.server.Formatter;

public class MajiangAnGangUnitCards extends MajiangUnitCards {
	public MajiangAnGangUnitCards(MajiangCard mc1, MajiangCard mc2,
			MajiangCard mc3, MajiangCard mc4) {
		this.addTailCard(mc1);
		this.addTailCard(mc2);
		this.addTailCard(mc3);
		this.addTailCard(mc4);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "暗杠";
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		int cns[] = this.toNumberArray();
		map.put("a", "angang");
		map.put("cns", cns);
		map.put("who", -1);
		map.put("cn", -1);
		return map;
	}

}
