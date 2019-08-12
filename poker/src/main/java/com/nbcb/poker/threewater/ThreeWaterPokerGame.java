package com.nbcb.poker.threewater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.game.GameInfo;
import com.nbcb.poker.card.PokerAllCards;
import com.nbcb.poker.card.PokerBlackCards;
import com.nbcb.poker.game.PokerGame;
import com.nbcb.poker.user.PokerPlayer;

public class ThreeWaterPokerGame extends PokerGame {

	private ThreeWaterPokerResults threeWaterPokerResults;

	public ThreeWaterPokerResults getThreeWaterPokerResults() {
		return threeWaterPokerResults;
	}

	public void setThreeWaterPokerResults(
			ThreeWaterPokerResults threeWaterPokerResults) {
		this.threeWaterPokerResults = threeWaterPokerResults;
	}

	public ThreeWaterPokerGame(GameInfo gameInfo) {
		super(gameInfo);
	}

	protected List<Map<String, Object>> formatCards() {
		List<Map<String, Object>> cards = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < this.players.getActivePlayers(); i++) {
			ThreeWaterPokerPlayer player = (ThreeWaterPokerPlayer) this.players
					.getPlayerByIndex(i);
			String account = player.getAccount();
			Map<String, Object> card = new HashMap<String, Object>();
			card.put("dir", player.getPlayerOrder());
			card.put("i", player.getThreeWaterPokerCards().toNumberArray());

			if (player.getListCandidate() != null
					&& player.getListCandidate().size() != 0) {
				List<Map> listTmp = new ArrayList<Map>();
				for (ThreeWaterPokerCards twpcs : player.getListCandidate()) {
					listTmp.add(twpcs.format());
				}
				card.put("candite", listTmp);
			}
			if (player.getThreeWaterPokerCards().getSpecialCards() != null) {
				card.put("special", player.getThreeWaterPokerCards()
						.getSpecialCards().format());
			}
			cards.add(card);
		}
		return cards;
	}

	@Override
	public Map format() {
		Map<String, Object> mapRet = new HashMap<String, Object>();
		mapRet.put("cards", this.formatCards());
		return mapRet;
	}

	@Override
	public void start() {
		super.start();
		pokerBlackCards = new PokerBlackCards((PokerAllCards) this.allCards);
		for (int i = 0; i < this.players.getActivePlayers(); i++) {
			ThreeWaterPokerPlayer mp = (ThreeWaterPokerPlayer) this.players.getPlayerByIndex(i);
			mp.setThreeWaterPokerCards(new ThreeWaterPokerCards());
			mp.getListCandidate().clear();
		}
		ThreeWaterPokerAction action = new ThreeWaterPokerAction(null,
				ThreeWaterPokerAction.ALLOCATE, null, false);
		rule.next(this, action);
	}

	@Override
	public String toString() {
		String str = "--------------------ThreeWaterPokerGame start---------------------\r\n";
		str += "### gameInfo:" + getGameInfo() + " roomIdForQuery["
				+ this.getRoom().getId() + "]";
		str += "\r\n";
		str += "### playerScores:" + this.playerScores;
		str += "\r\n";
		str += "### blackCards:" + this.getPokerBlackCards();
		str += "\r\n";
		str += players;
		str += "---------------------ThreeWaterPokerGame end----------------------\r\n";
		return str;
	}

}
