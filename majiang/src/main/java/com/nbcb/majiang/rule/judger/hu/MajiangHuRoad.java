package com.nbcb.majiang.rule.judger.hu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangUnitCards;

public class MajiangHuRoad {
	
	private static final Logger logger = LoggerFactory.getLogger(MajiangHuRoad.class);


	private List<MajiangUnitCards> listUnitCards = new ArrayList<MajiangUnitCards>();

	private List<MajiangAnPengUnitCards> listAnPengUnitCards = new ArrayList<MajiangAnPengUnitCards>();

	private List<MajiangAnChiUnitCards> listAnChiUnitCards = new ArrayList<MajiangAnChiUnitCards>();

	private MajiangCard huMjCard;

	public List<MajiangUnitCards> getListUnitCards() {
		return listUnitCards;
	}
	
	public MajiangHuRoad(MajiangUnitCards mucs,MajiangCard huMjCard){
		this.listUnitCards.add(mucs);
		this.huMjCard = huMjCard;
		
		if (mucs instanceof MajiangAnPengUnitCards) {
			listAnPengUnitCards.add((MajiangAnPengUnitCards) mucs);
		} else if (mucs instanceof MajiangAnChiUnitCards) {
			listAnChiUnitCards.add((MajiangAnChiUnitCards) mucs);
		}
	}

	public MajiangHuRoad(List<MajiangUnitCards> listUnitCards,
			MajiangCard huMjCard) {
		this.listUnitCards.addAll(listUnitCards);
		this.huMjCard = huMjCard;
		for (MajiangUnitCards mucs : listUnitCards) {
			if (mucs instanceof MajiangAnPengUnitCards) {
				listAnPengUnitCards.add((MajiangAnPengUnitCards) mucs);
			} else if (mucs instanceof MajiangAnChiUnitCards) {
				listAnChiUnitCards.add((MajiangAnChiUnitCards) mucs);
			}
		}
	}

	public List<MajiangAnPengUnitCards> getListAnPengUnitCards() {
		return listAnPengUnitCards;
	}

	public List<MajiangAnChiUnitCards> getListAnChiUnitCards() {
		return listAnChiUnitCards;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (MajiangUnitCards mucs : listUnitCards) {
			for (int i = 0; i < mucs.size(); i++) {
				Card c = mucs.getCard(i);
				hash += c.getType().hashCode();
//				if(!((MajiangCard)c).isBaida()){
//					hash += c.getType().hashCode();
//				}
			}
		}
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof MajiangHuRoad)) {
			return false;
		}
		MajiangHuRoad other = (MajiangHuRoad) o;
		List<MajiangUnitCards> listOther = other.getListUnitCards();
		if (listOther.size() != this.listUnitCards.size()) {
			return false;
		}
		if (listOther.size() == 0) {
			return false;
		}
		if (other.getListAnChiUnitCards().size() != this
				.getListAnChiUnitCards().size()) {
			return false;
		}
		if (other.getListAnPengUnitCards().size() != this
				.getListAnPengUnitCards().size()) {
			return false;
		}

		Collections.sort(this.listUnitCards);
		Collections.sort(listOther);

		for (int i = 0; i < listOther.size(); i++) {
			MajiangUnitCards mucsThis = this.listUnitCards.get(i);
			MajiangUnitCards mucsOther = listOther.get(i);
			
			if (mucsThis.getClass() != mucsOther.getClass()) {
				return false;
			}
			if (!mucsThis.getName().equals(mucsOther.getName())) {
				return false;
			}
			
			boolean thisContain = mucsThis.containsCard(this.huMjCard);
			boolean otherContain = mucsOther.containsCard(this.huMjCard);
			if (thisContain != otherContain) {
				return false;
			}
			if (!mucsThis.sameByCardType(mucsOther)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "road:" + listUnitCards.toString() + " huMjCard[" + huMjCard
				+ "] ";
	}

}
