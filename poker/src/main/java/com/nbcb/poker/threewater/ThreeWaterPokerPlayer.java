package com.nbcb.poker.threewater;

import java.util.ArrayList;
import java.util.List;

import com.nbcb.poker.user.PokerPlayer;

public class ThreeWaterPokerPlayer extends PokerPlayer {

	private List<ThreeWaterPokerCards> listCandidate = new ArrayList<ThreeWaterPokerCards>();

	private ThreeWaterPokerCards threeWaterPokerCards = new ThreeWaterPokerCards();
	
	

	public List<ThreeWaterPokerCards> getListCandidate() {
		return listCandidate;
	}

	public void setListCandidate(List<ThreeWaterPokerCards> listCandidate) {
		this.listCandidate = listCandidate;
	}

	public ThreeWaterPokerCards getThreeWaterPokerCards() {
		return threeWaterPokerCards;
	}

	public void setThreeWaterPokerCards(
			ThreeWaterPokerCards threeWaterPokerCards) {
		this.threeWaterPokerCards = threeWaterPokerCards;
	}

	@Override
	public String toString() {
		String str = "### " + super.toString();
		str += "\r\n";
		str += threeWaterPokerCards;
		return str;
	}

	public ThreeWaterPokerPlayer(String account) {
		super(account);
	}

}
