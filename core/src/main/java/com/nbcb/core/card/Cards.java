package com.nbcb.core.card;

public interface Cards extends Sortable {

	public int size();

	public void addHeadCard(Card card);

	public void addTailCard(Card card);

	public void addCard(int i, Card card);

	public void addHeadCards(Card[] cards);

	public void addTailCards(Card[] cards);

	public Card removeCard(Card card);

	public Card removeHeadCard();

	public Card removeTailCard();

	public void removeCards(Card[] cards);

	public Card[] removeHeadCards(int size);

	public Card[] removeTailCards(int size);

	public Card getCard(int i);

	public Card getHeadCard();

	public Card getTailCard();

	public Card findCardByType(String type);

	public Card findCardByNumber(int number);

	public Card[] findCardsByType(String type);

	public Card[] toArray();

	public void clear();

	public boolean sameByCardNumberAndType(Cards cards);

	public boolean sameByCardType(Cards cards);

	public int[] toNumberArray();

	public boolean containsCard(Card card);
	
	/**
	 * 是否包含了大于一个一模一样的Card
	 * @param cards
	 * @return
	 */
	public boolean hasSameCard(Cards cards);
	

}
