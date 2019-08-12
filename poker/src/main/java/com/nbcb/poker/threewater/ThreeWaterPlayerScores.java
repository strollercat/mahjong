package com.nbcb.poker.threewater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.game.AbstractPlayerScores;

public class ThreeWaterPlayerScores extends AbstractPlayerScores {

	private String quanLeiDa;

	private List<Map> listGun ;
	
	

	

	public void setListGun(List<Map> listGun) {
		this.listGun = listGun;
	}

	public void setQuanLeiDa(String quanLeiDa) {
		this.quanLeiDa = quanLeiDa;
	}

	@Override
	public Map format() {
		Map map = new HashMap();
		map.put("quanLeiDa", quanLeiDa);
		map.put("scores", super.format());
		map.put("guns", listGun);
		return map;

	}

}
