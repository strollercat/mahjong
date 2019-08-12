package com.nbcb.majiang.ningbo.sevenbaida;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.core.io.MessageListener;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.calculator.MajiangMingGangActionCalculator;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang7BaidaMingGangActionCalculator extends
		MajiangMingGangActionCalculator {

	private MessageListener messageListener;

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	@Override
	protected Actions calculateActionInner(MajiangGame mjGame,
			MajiangAction mjAction) {
		// TODO Auto-generated method stub
		Actions actions = new MajiangActions();

		Cards cards = new MajiangCards();
		cards.addTailCard(mjGame.getMajiangBlackCards().getTailCard());
		actions.addAction(new MajiangAction(mjAction.getPlayer(),
				MajiangAction.MOBACK, cards, false));

		NingboMajiang7BaidaGame ningboMajiang7BaidaGame = (NingboMajiang7BaidaGame) mjGame;

		if (ningboMajiang7BaidaGame.isBao()) { // 已经是包的状态，直接下去了
			return actions;
		}

		ningboMajiang7BaidaGame.setBao(true);

		String gangAccount = mjAction.getPlayer().getAccount();
		ningboMajiang7BaidaGame.putGen(gangAccount, true);
//		this.sendMessage(gangAccount);

		Actions lastActions = ningboMajiang7BaidaGame.getHistoryActions();
		String daAccount = lastActions.getAction(lastActions.size() - 2)
				.getPlayer().getAccount();
		ningboMajiang7BaidaGame.putGen(daAccount, true);

		int total = ningboMajiang7BaidaGame.getActivePlayers();
		for (int i = 0; i < total; i++) {
			MajiangPlayer mp = (MajiangPlayer) ningboMajiang7BaidaGame
					.getPlayerByIndex(i);
			if (mp.getAccount().equals(daAccount)) {
				continue;
			}
			if (mp.getAccount().equals(gangAccount)) {
				continue;
			}
			actions.addAction(new MajiangAction(mp, MajiangAction.GEN, null,
					true));
		}
		return actions;
	}

	private void sendMessage(String gangAccount) {
		String tintMessage = "杠后打的第一张牌算自摸，请慎重出牌";
		this.messageListener.listen(gangAccount, tintMessage);
	}

}
