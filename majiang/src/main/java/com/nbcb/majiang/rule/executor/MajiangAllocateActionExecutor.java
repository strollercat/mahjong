package com.nbcb.majiang.rule.executor;

import com.nbcb.core.action.Action;
import com.nbcb.core.game.Game;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangAllocateActionExecutor extends MajiangActionExecutor {

	public void exec(Game game, Action action) {

		MajiangGame mg = (MajiangGame) game;

		MajiangBlackCards bcs = mg.getMajiangBlackCards();
		for (int i = 0; i < 3; i++) {
			MajiangPlayer dealer = mg.getDealer();
			MajiangPlayer nextPlayer = dealer;
			do {
				nextPlayer.getMajiangInnerCards().addTailCards(
						bcs.removeHeadCards(4));
				nextPlayer = (MajiangPlayer) mg.nextPlayer(nextPlayer);
			} while (nextPlayer != dealer);
		}
		MajiangPlayer dealer = mg.getDealer();
		MajiangPlayer nextPlayer = dealer;
		do {
			nextPlayer.getMajiangInnerCards().addTailCards(
					bcs.removeHeadCards(1));
			nextPlayer = (MajiangPlayer) mg.nextPlayer(nextPlayer);
		} while (nextPlayer != dealer);

		// MajiangGame mg = (MajiangGame) game;
		//
		// MajiangBlackCards bcs = mg.getMajiangBlackCards();
		// MajiangPlayer nextPlayer = mg.getDealer();
		// while(true){
		// nextPlayer.getMajiangInnerCards().addTailCards(
		// mg.getMajiangBlackCards().removeHeadCards(13));
		// nextPlayer = (MajiangPlayer)mg.nextPlayer(nextPlayer);
		// if(nextPlayer == mg.getDealer()){
		// break;
		// }
		// }
	}

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub

	}
}
