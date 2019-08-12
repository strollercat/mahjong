package com.nbcb.poker.card;

import com.nbcb.common.util.RandomUtil;

public class PokerAllCards extends PokerCards {

	public void start() {
		int index = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				String type = i + "_" + j;
				this.addTailCard(new PokerCard(index++, type));
			}
		}
		this.addTailCard(new PokerCard(index++, "0_16"));
		this.addTailCard(new PokerCard(index++, "1_17"));
	}

	public PokerCard getPokerCardByColorAndPokerNumber(PokerCard.Color color,
			int pokerNumber) {
		for (int i = 0; i < this.size(); i++) {
			PokerCard pc = (PokerCard) this.getCard(i);
			if (pc.getPokerColor() == color
					&& pc.getPokerNumber() == pokerNumber) {
				return pc;
			}
		}
		return null;
	}

	public PokerCards getRandomPokerCards(int size) {
		PokerCards pcs = new PokerCards();

		int start = 0;
		int end = this.size();
		int[] randoms = RandomUtil.getRandom(start, end - 1);
		for (int i = 0; i < size; i++) {
			pcs.addTailCard(this.getCard(randoms[i]));
		}
		return pcs;

	}

	public static void main(String[] args) {
		PokerAllCards pacs = new PokerAllCards();
		pacs.start();
		System.out.println(pacs);
	}

}
