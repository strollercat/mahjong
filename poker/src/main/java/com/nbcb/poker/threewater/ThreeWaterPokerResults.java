package com.nbcb.poker.threewater;

import java.util.ArrayList;
import java.util.List;

public class ThreeWaterPokerResults {
	
	private List<ThreeWaterPokerResult> list = new ArrayList<ThreeWaterPokerResult>();
	
	public void addThreeWaterPokerResult(ThreeWaterPokerResult threeWaterPokerResult){
		list.add(threeWaterPokerResult);
	}
	public ThreeWaterPokerResult getThreeWaterPokerResult(int i){
		return list.get(i);
	}

}
