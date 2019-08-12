package com.nbcb.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbstractCards implements Cards {

	protected List<Card> cards = new ArrayList<Card>();

	@Override
	public int size() {
		return cards.size();
	}

	@Override
	public void addHeadCard(Card card) {
		cards.add(0, card);
	}

	@Override
	public void addTailCard(Card card) {
		cards.add(card);
	}

	@Override
	public void addCard(int i, Card card) {
		cards.add(i, card);
	}

	@Override
	public void addHeadCards(Card[] cards) {
		if (cards == null || cards.length == 0) {
			return;
		}
		for (int i = 0; i < cards.length; i++) {
			Card c = cards[i];
			this.cards.add(0, c);
		}
	}

	@Override
	public void addTailCards(Card[] cards) {
		if (cards == null || cards.length == 0) {
			return;
		}
		for (int i = 0; i < cards.length; i++) {
			Card c = cards[i];
			this.cards.add(c);
		}
	}

	@Override
	public Card removeCard(Card card) {
		if (cards.remove(card)) {
			return card;
		}
		return null;
	}

	@Override
	public Card removeHeadCard() {
		if (this.cards.size() == 0) {
			throw new IllegalStateException("the cards size is 0");
		}
		return cards.remove(0);
	}

	@Override
	public Card removeTailCard() {
		if (this.cards.size() == 0) {
			throw new IllegalStateException("the cards size is 0");
		}
		return cards.remove(cards.size() - 1);
	}

	@Override
	public void removeCards(Card[] cards) {
		if (cards == null || cards.length == 0) {
			return;
		}
		for (int i = 0; i < cards.length; i++) {
			this.removeCard(cards[i]);
		}
	}

	@Override
	public Card getCard(int i) {
		return cards.get(i);
	}

	@Override
	public Card getHeadCard() {
		if (this.cards.size() == 0) {
			return null;
		}
		return cards.get(0);
	}

	@Override
	public Card getTailCard() {
		if (this.cards.size() == 0) {
			return null;
		}
		return cards.get(cards.size() - 1);
	}

	@Override
	public boolean containsCard(Card card) {
		return cards.contains(card);
	}

	@Override
	public Card findCardByType(String type) {
		for (Card c : this.cards) {
			if (c.getType().equals(type)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Card[] findCardsByType(String type) {
		List<Card> list = new ArrayList<Card>();
		for (Card c : this.cards) {
			if (c.getType().equals(type)) {
				list.add(c);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Card[] cards = new Card[list.size()];
		list.toArray(cards);
		return cards;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Card c : cards) {
			sb.append(c);
		}
		return "total[" + this.size() + "] " + sb.toString();
	}

	@Override
	public Card[] toArray() {
		if (this.cards.size() == 0) {
			return null;
		}
		Card[] arrCards = new Card[this.cards.size()];
		this.cards.toArray(arrCards);
		return arrCards;
	}

	@Override
	public void sort() {
		Collections.sort(this.cards);
	}

	@Override
	public Card[] removeHeadCards(int size) {

		if (cards.size() < size) {
			throw new IllegalArgumentException("size[" + size
					+ "] is less than the cards.size[" + cards.size() + "]");
		}
		Card[] retCards = new Card[size];
		for (int i = 0; i < size; i++) {
			retCards[i] = cards.remove(0);
		}
		return retCards;
	}

	@Override
	public Card[] removeTailCards(int size) {

		if (cards.size() < size) {
			throw new IllegalArgumentException("size[" + size
					+ "] is less than the cards.size[" + cards.size() + "]");
		}
		Card[] retCards = new Card[size];
		for (int i = 0; i < size; i++) {
			retCards[i] = cards.remove(this.cards.size() - 1);
		}
		return retCards;
	}

	@Override
	public void clear() {
		this.cards.clear();
	}

	@Override
	public boolean sameByCardNumberAndType(Cards cards) {

		if (!(cards instanceof AbstractCards)) {
			return false;
		}
		AbstractCards other = (AbstractCards) cards;
		if (other.size() != this.size()) {
			return false;
		}
		if (other.size() == 0) {
			return true;
		}
		this.sort();
		other.sort();
		for (int i = 0; i < other.size(); i++) {
			if (this.getCard(i).getNumber() != other.getCard(i).getNumber()) {
				return false;
			}
			if (!this.getCard(i).getType().equals(other.getCard(i).getType())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean sameByCardType(Cards cards) {
		if (!(cards instanceof AbstractCards)) {
			return false;
		}
		AbstractCards other = (AbstractCards) cards;
		if (other.size() != this.size()) {
			return false;
		}
		if (other.size() == 0) {
			return true;
		}
		this.sort();
		other.sort();
		for (int i = 0; i < other.size(); i++) {
			if (!this.getCard(i).getType().equals(other.getCard(i).getType())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int[] toNumberArray() {
		int[] ints = new int[this.cards.size()];
		for (int i = 0; i < this.cards.size(); i++) {
			ints[i] = this.getCard(i).getNumber();
		}
		return ints;
	}

	@Override
	public Card findCardByNumber(int number) {
		for (Card card : this.cards) {
			if (card.getNumber() == number) {
				return card;
			}
		}
		return null;
	}

	@Override
	public boolean hasSameCard(Cards cards) {
		if (cards == null || cards.size() == 0) {
			return false;
		}
		for(int i = 0;i<cards.size();i++){
			if(this.containsCard(cards.getCard(i))){
				return true;
			}
		}
		return false;
	}

}
