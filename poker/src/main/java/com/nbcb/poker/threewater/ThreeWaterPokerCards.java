package com.nbcb.poker.threewater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.card.Card;
import com.nbcb.core.server.Formatter;
import com.nbcb.poker.card.PokerCard;
import com.nbcb.poker.card.PokerCards;
import com.nbcb.poker.card.PokerUnitCards;

public class ThreeWaterPokerCards extends PokerCards implements Formatter {

	private PokerUnitCards specialCards;
	private PokerUnitCards firstPokerCards;
	private PokerUnitCards secondPokerCards;
	private PokerUnitCards thirdPokerCards;

	private boolean shooted = false;

	public boolean isShooted() {
		return shooted;
	}

	public void setShooted(boolean shooted) {
		this.shooted = shooted;
	}

	public boolean isSpecial() {
		return specialCards != null;
	}

	public PokerUnitCards getSpecialCards() {
		return specialCards;
	}

	public void setSpecialCards(PokerUnitCards specialCards) {
		this.specialCards = specialCards;
	}

	public PokerUnitCards getFirstPokerCards() {
		return firstPokerCards;
	}

	public void setFirstPokerCards(PokerUnitCards firstPokerCards) {
		this.firstPokerCards = firstPokerCards;
	}

	public PokerUnitCards getSecondPokerCards() {
		return secondPokerCards;
	}

	public void setSecondPokerCards(PokerUnitCards secondPokerCards) {
		this.secondPokerCards = secondPokerCards;
	}

	public PokerUnitCards getThirdPokerCards() {
		return thirdPokerCards;
	}

	public void setThirdPokerCards(PokerUnitCards thirdPokerCards) {
		this.thirdPokerCards = thirdPokerCards;
	}

	public void clear() {
		super.clear();
		this.firstPokerCards = null;
		this.secondPokerCards = null;
		this.thirdPokerCards = null;
		this.specialCards = null;
		this.shooted = false;
	}

	private String toString(Card[] cards) {
		String str = "";
		for (Card c : cards) {
			PokerCard pc = (PokerCard) c;
			str += pc.toString() + " ";
		}
		return str;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str+="特殊:";
		if (this.specialCards != null) {
			str += this.specialCards.getName() + toString(this.specialCards.toArray());
		}
		str+="\r\n";
		
		str+="头蹲:";
		if (this.firstPokerCards != null) {
			str += this.firstPokerCards.getName()
					+ toString(this.firstPokerCards.toArray()) ;
		}
		str+="\r\n";
		
		str+="中蹲:";
		if (this.secondPokerCards != null) {
			str += this.secondPokerCards.getName()
					+ toString(this.secondPokerCards.toArray()) ;
		}
		str+="\r\n";
		
		
		str+="尾蹲:";
		if (this.thirdPokerCards != null) {
			str += this.thirdPokerCards.getName()
					+ toString(this.thirdPokerCards.toArray());
		}
		str+="\r\n";
		return str;
	}
	
	@Override
	public void sort(){
		super.sort();
		if(this.specialCards!=null){
			this.specialCards.sort();
		}
		if(this.firstPokerCards!=null){
			this.firstPokerCards.sort();
		}
		if(this.secondPokerCards!=null){
			this.secondPokerCards.sort();
		}
		if(this.thirdPokerCards!=null){
			this.thirdPokerCards.sort();
		}
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		if (this.specialCards == null) {
			list.add(null);
		} else {
			list.add(this.specialCards.format());
		}
		if (this.firstPokerCards == null) {
			list.add(null);
		} else {
			list.add(this.firstPokerCards.format());
		}
		if (this.secondPokerCards == null) {
			list.add(null);
		} else {
			list.add(this.secondPokerCards.format());
		}
		if (this.thirdPokerCards == null) {
			list.add(null);
		} else {
			list.add(this.thirdPokerCards.format());
		}
		map.put("cards", list);
		return map;
	}

}
