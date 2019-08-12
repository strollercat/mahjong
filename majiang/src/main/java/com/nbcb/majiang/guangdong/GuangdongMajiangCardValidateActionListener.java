package com.nbcb.majiang.guangdong;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.ActionListener;
import com.nbcb.core.card.Card;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangInnerCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.card.MajiangOutterCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class GuangdongMajiangCardValidateActionListener implements
		ActionListener {

	private Card[] initArrCards(Game game) {
		return new Card[144];
	}

	private void copy(MajiangCards mjCards, Card[] arrCards) {
		int len = mjCards.size();
		if (len < 0) {
			return;
		}
		for (int i = 0; i < len; i++) {
			arrCards[mjCards.getCard(i).getNumber()] = mjCards.getCard(i);
		}
	}

	private void copy(Card c, Card[] arrCards) {
		if (c == null) {
			return;
		}
		arrCards[c.getNumber()] = c;
	}

	private void validate(MajiangGame mjGame, Card[] arrCards) {

		for (int i = 0; i < arrCards.length; i++) {
			MajiangCard c = (MajiangCard) arrCards[i];
			if (i >= 136 && i < 144) { // 花
				continue;
			}
			if (c == null) {
				throw new RuntimeException("### card is null!! i [" + i + "]");
			}
			if (c.getNumber() != i) {
				throw new RuntimeException(
						"### card number do not match the sequence! number["
								+ c.getNumber() + "] i[" + i + "]");
			}
		}
	}

	public void listen(Game game, Action action) {

		Card[] arrCards = this.initArrCards(game);

		int total = 0;
		MajiangGame mjGame = (MajiangGame) game;

		MajiangBlackCards mbcs = mjGame.getMajiangBlackCards();
		this.copy(mbcs, arrCards);
		total += mbcs.size();

		for (int i = 0; i < game.getActivePlayers(); i++) {
			MajiangPlayer nextPlayer = (MajiangPlayer) game.getPlayerByIndex(i);
			MajiangInnerCards mics = nextPlayer.getMajiangInnerCards();
			MajiangMiddleCards mmcs = nextPlayer.getMajiangMiddleCards();
			MajiangOutterCards mocs = nextPlayer.getMajiangOutterCards();
			this.copy(mics, arrCards);
			this.copy(mmcs, arrCards);
			this.copy(mocs, arrCards);
			total += mics.size();
			total += mmcs.size();
			total += mocs.size();
		}
		if (total != mjGame.getAllCards().size()) {
			throw new RuntimeException("### total is " + total);
		}
		this.validate(mjGame, arrCards);
	}

}
