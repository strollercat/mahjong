package com.nbcb.poker.card;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.DefaultCards;

public class PokerCards extends DefaultCards {

	/**
	 * 发现所有的PokerNumber
	 * 
	 * @return
	 */
	public Set<Integer> findAllPokerNumber() {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < this.size(); i++) {
			PokerCard pc = (PokerCard) this.getCard(i);
			set.add(pc.getPokerNumber());
		}
		return set;
	}

	/**
	 * 1 -> A
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public PokerCards findSingleNumberSequencePokerCards(int min, int max) {

		if (min >= max) {
			return null;
		}
		if (max > 14) {
			return null;
		}
		if (min < 1) {
			return null;
		}
		if (this.size() < (max - min + 1)) {
			return null;
		}
		PokerCards retPcs = new PokerCards();
		for (int i = min; i <= max; i++) {
			PokerCards findPcs;
			if (i == 14) {
				findPcs = findPokerCardsByPokerNumber(1);
			} else {
				findPcs = findPokerCardsByPokerNumber(i);
			}
			Card findPc = this.getUnAppearPokerCard(retPcs, findPcs);
			if (findPc != null) {
				retPcs.addTailCard(findPc);
			} else {
				return null;
			}
		}
		return retPcs;
	}

	private PokerCard getUnAppearPokerCard(PokerCards appearPcs,
			PokerCards findPcs) {
		if (findPcs == null || findPcs.size() == 0) {
			return null;
		}
		if (appearPcs == null || appearPcs.size() == 0) {
			return (PokerCard) findPcs.getHeadCard();
		}
		for (int i = 0; i < findPcs.size(); i++) {
			PokerCard pc = (PokerCard) findPcs.getCard(i);
			if (!appearPcs.containsCard(pc)) {
				return pc;
			}
		}
		return null;
	}

	public boolean sameCardsByPokerNumber(PokerCards pokerCards) {
		if (pokerCards == null) {
			return false;
		}
		if (this.size() != pokerCards.size()) {
			return false;
		}
		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(this.toArray());
		copyPcs.sort();
		pokerCards.sort();
		for (int i = 0; i < copyPcs.size(); i++) {
			PokerCard pc1 = (PokerCard) copyPcs.getCard(i);
			PokerCard pc2 = (PokerCard) pokerCards.getCard(i);
			if (pc1.getPokerNumber() != pc2.getPokerNumber()) {
				return false;
			}
		}
		return true;
	}

	public boolean sameCardsByPokerNumberAndPokerColor(PokerCards pokerCards) {
		if (pokerCards == null) {
			return false;
		}
		if (this.size() != pokerCards.size()) {
			return false;
		}
		PokerCards copyPcs = new PokerCards();
		copyPcs.addTailCards(this.toArray());
		copyPcs.sort();
		pokerCards.sort();
		for (int i = 0; i < copyPcs.size(); i++) {
			PokerCard pc1 = (PokerCard) copyPcs.getCard(i);
			PokerCard pc2 = (PokerCard) pokerCards.getCard(i);
			if (pc1.getPokerNumber() != pc2.getPokerNumber()
					|| pc1.getPokerColor() != pc2.getPokerColor()) {
				return false;
			}
		}
		return true;
	}

	public PokerCards findPokerCardsByPokerNumber(int pokerNumber) {
		PokerCards pcs = new PokerCards();
		for (int i = 0; i < this.size(); i++) {
			PokerCard pc = (PokerCard) this.getCard(i);
			if (pc.getPokerNumber() == pokerNumber) {
				pcs.addTailCard(pc);
			}
		}
		return pcs;
	}

	public PokerCards findPokerCardsByPokerColor(PokerCard.Color color) {
		PokerCards pcs = new PokerCards();
		for (int i = 0; i < this.size(); i++) {
			PokerCard pc = (PokerCard) this.getCard(i);
			if (pc.getPokerColor() == color) {
				pcs.addTailCard(pc);
			}
		}
		return pcs;
	}

	public PokerCards findPokerBigKingCards() {
		PokerCards pcs = new PokerCards();
		for (int i = 0; i < this.size(); i++) {
			PokerCard pc = (PokerCard) this.getCard(i);
			if (pc.isPokerBigKing()) {
				pcs.addTailCard(pc);
			}
		}
		return pcs;
	}

	public PokerCards findPokerSmallKingCards() {
		PokerCards pcs = new PokerCards();
		for (int i = 0; i < this.size(); i++) {
			PokerCard pc = (PokerCard) this.getCard(i);
			if (pc.isPokerSmallKing()) {
				pcs.addTailCard(pc);
			}
		}
		return pcs;
	}

	public PokerCards findPokerKingCards() {
		PokerCards pcs = new PokerCards();
		pcs.addTailCards(this.findPokerBigKingCards().toArray());
		pcs.addTailCards(this.findPokerSmallKingCards().toArray());
		return pcs;
	}

	public boolean containedPokerCardsByPokerNumber(List list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		for (Object obj : list) {
			PokerCards tmpPcs = (PokerCards) obj;
			if (tmpPcs.sameCardsByPokerNumber(this)) {
				return true;
			}
		}
		return false;
	}

	public boolean containedPokerCardsByPokerNumberAndPokerColor(List list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		for (Object obj : list) {
			PokerCards tmpPcs = (PokerCards) obj;
			if (tmpPcs.sameCardsByPokerNumberAndPokerColor(this)) {
				return true;
			}
		}
		return false;
	}

	// public static void main(String[] args) {
	// PokerAllCards pokerAllCards = new PokerAllCards();
	// pokerAllCards.start();
	//
	// System.out.println(pokerAllCards.findSingleNumberSequencePokerCards(13,
	// 13));
	//
	// PokerCards pcs = new PokerCards();
	// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
	// PokerCard.Color.HEITAO, 1));
	// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
	// PokerCard.Color.HONGTAO, 1));
	// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
	// PokerCard.Color.HEITAO, 2));
	// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
	// PokerCard.Color.HONGTAO, 2));
	// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
	// PokerCard.Color.HEITAO, 3));
	// pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
	// PokerCard.Color.HONGTAO, 4));
	//
	// // System.out.println(pcs.chooseAllNotSamePokerNumberCardsBySize(2));
	// //
	// System.out.println(pokerAllCards.chooseAllNotSamePokerNumberCardsBySize(1));
	// }

	public static void main(String[] args) {
		PokerAllCards pokerAllCards = new PokerAllCards();
		pokerAllCards.start();

		PokerCards pcs = new PokerCards();
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 3));
		pcs.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 1));

		PokerCards pcs1 = new PokerCards();
		pcs1.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.HEITAO, 1));
		pcs1.addTailCard(pokerAllCards.getPokerCardByColorAndPokerNumber(
				PokerCard.Color.CAOHUA, 3));

//		System.out.println(pcs.sameCardsByPokerNumber(pcs1));
		pcs.sort();
		System.out.println(pcs);

	}

}
