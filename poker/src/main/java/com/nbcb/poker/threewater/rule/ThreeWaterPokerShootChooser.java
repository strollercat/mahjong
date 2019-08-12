package com.nbcb.poker.threewater.rule;

import java.util.List;

import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.threewater.ThreeWaterPokerCards;

public interface ThreeWaterPokerShootChooser {
	
	public List<ThreeWaterPokerCards> chooseBetter(PokerCards pokerCards);
	
	public boolean legal(ThreeWaterPokerCards twPcs);
	
}
