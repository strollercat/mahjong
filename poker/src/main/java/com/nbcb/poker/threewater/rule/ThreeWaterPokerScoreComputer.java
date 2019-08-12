package com.nbcb.poker.threewater.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.game.Game;
import com.nbcb.core.game.PlayerScores;
import com.nbcb.core.game.ScoreComputer;
import com.nbcb.core.game.WinLosePlayerScore;
import com.nbcb.core.user.Player;
import com.nbcb.poker.card.PokerUnitCards;
import com.nbcb.poker.card.PokerUnitCardsCompare;
import com.nbcb.poker.threewater.ThreeWaterPlayerScores;
import com.nbcb.poker.threewater.ThreeWaterPokerCards;
import com.nbcb.poker.threewater.ThreeWaterPokerPlayer;

public class ThreeWaterPokerScoreComputer implements ScoreComputer {

	private Map<String, Integer> mapSpecial;

	public void setMapSpecial(Map<String, Integer> mapSpecial) {
		this.mapSpecial = mapSpecial;
	}

	private PokerUnitCardsCompare normalPokerUnitCardsCompare;

	private PokerUnitCardsCompare specialPokerUnitCardsCompare;

	public void setNormalPokerUnitCardsCompare(
			PokerUnitCardsCompare normalPokerUnitCardsCompare) {
		this.normalPokerUnitCardsCompare = normalPokerUnitCardsCompare;
	}

	public void setSpecialPokerUnitCardsCompare(
			PokerUnitCardsCompare specialPokerUnitCardsCompare) {
		this.specialPokerUnitCardsCompare = specialPokerUnitCardsCompare;
	}

	private List<ThreeWaterPokerPlayer> getNormalPlayers(Game game) {
		List<ThreeWaterPokerPlayer> listRet = new ArrayList<ThreeWaterPokerPlayer>();
		for (int i = 0; i < game.getActivePlayers(); i++) {
			ThreeWaterPokerPlayer twP = (ThreeWaterPokerPlayer) game
					.getPlayerByIndex(i);
			if (!twP.getThreeWaterPokerCards().isSpecial()) {
				listRet.add(twP);
			}
		}
		return listRet;
	}

	@Override
	public PlayerScores computerScore(Game game, Object object) {

		ThreeWaterPlayerScores retScores = new ThreeWaterPlayerScores();

		this.compareSpecial(game, retScores);

		List<ThreeWaterPokerPlayer> listNormal = this.getNormalPlayers(game);
		if (listNormal == null || listNormal.size() <= 1) {
			return retScores;
		}

		this.compareNormal(listNormal, retScores,
				!(listNormal.size() == game.getActivePlayers()));

		return retScores;
	}

	private void compareSpecial(Game game, ThreeWaterPlayerScores retScores) {

		List<WinLosePlayerScore> listRet = new ArrayList<WinLosePlayerScore>();
		ThreeWaterPokerPlayer pp1, pp2;
		ThreeWaterPokerCards twPcs1, twPcs2;
		int compareRet;

		for (int i = 0; i < game.getActivePlayers(); i++) {

			pp1 = (ThreeWaterPokerPlayer) game.getPlayerByIndex(i);
			twPcs1 = pp1.getThreeWaterPokerCards();

			for (int j = (i + 1); j < game.getActivePlayers(); j++) {

				pp2 = (ThreeWaterPokerPlayer) game.getPlayerByIndex(j);
				twPcs2 = pp2.getThreeWaterPokerCards();

				if (twPcs1.isSpecial() && twPcs2.isSpecial()) {
					compareRet = specialPokerUnitCardsCompare.compare(
							twPcs1.getSpecialCards(), twPcs2.getSpecialCards());
					if (compareRet == 0) {
						continue;
					}
					if (compareRet > 0) {
						WinLosePlayerScore wlPs = new WinLosePlayerScore(pp1,
								pp2, this.mapSpecial.get(twPcs1
										.getSpecialCards().getName()));
						listRet.add(wlPs);
					} else {
						WinLosePlayerScore wlPs = new WinLosePlayerScore(pp2,
								pp1, this.mapSpecial.get(twPcs2
										.getSpecialCards().getName()));
						listRet.add(wlPs);
					}
				} else if (twPcs1.isSpecial() && !twPcs2.isSpecial()) {
					WinLosePlayerScore wlPs = new WinLosePlayerScore(pp1, pp2,
							this.mapSpecial.get(twPcs1.getSpecialCards()
									.getName()));
					listRet.add(wlPs);
				} else if (!twPcs1.isSpecial() && twPcs2.isSpecial()) {
					WinLosePlayerScore wlPs = new WinLosePlayerScore(pp2, pp1,
							this.mapSpecial.get(twPcs2.getSpecialCards()
									.getName()));
					listRet.add(wlPs);
				}

			}
		}
		this.calculateFinalScores(retScores, listRet);
	}

	private void compareNormal(List<ThreeWaterPokerPlayer> listNormal,
			ThreeWaterPlayerScores retScores, boolean hasSpecial) {

		ThreeWaterPokerPlayer pp1, pp2;
		ThreeWaterPokerCards twPcs1, twPcs2;

		Map<String, List<WinLosePlayerScore>> mapWinLosePlayerScore = new HashMap<String, List<WinLosePlayerScore>>();
		List<Map> listGun = new ArrayList<Map>();
		List<WinLosePlayerScore> listAll = new ArrayList<WinLosePlayerScore>();

		for (int i = 0; i < listNormal.size(); i++) {

			pp1 = listNormal.get(i);
			twPcs1 = pp1.getThreeWaterPokerCards();

			for (int j = (i + 1); j < listNormal.size(); j++) {

				pp2 = listNormal.get(j);
				twPcs2 = pp2.getThreeWaterPokerCards();

				int compareRet1 = this.normalPokerUnitCardsCompare.compare(
						twPcs1.getFirstPokerCards(),
						twPcs2.getFirstPokerCards());
				int compareRet2 = this.normalPokerUnitCardsCompare.compare(
						twPcs1.getSecondPokerCards(),
						twPcs2.getSecondPokerCards());
				int compareRet3 = this.normalPokerUnitCardsCompare.compare(
						twPcs1.getThirdPokerCards(),
						twPcs2.getThirdPokerCards());

				WinLosePlayerScore winLosePlayerScore;

				WinLosePlayerScore winLosePlayScore1 = this
						.getWinLosePlayerScore(compareRet1, 1, pp1, pp2);
				WinLosePlayerScore winLosePlayScore2 = this
						.getWinLosePlayerScore(compareRet2, 2, pp1, pp2);
				WinLosePlayerScore winLosePlayScore3 = this
						.getWinLosePlayerScore(compareRet3, 3, pp1, pp2);

				if (compareRet1 > 0 && compareRet2 > 0 && compareRet3 > 0) {
					winLosePlayScore1
							.setScore(winLosePlayScore1.getScore() * 2);
					winLosePlayScore2
							.setScore(winLosePlayScore2.getScore() * 2);
					winLosePlayScore3
							.setScore(winLosePlayScore3.getScore() * 2);
					Map map = new HashMap();
					map.put("gun", pp1.getAccount());
					map.put("guned", pp2.getAccount());
					listGun.add(map);
					winLosePlayerScore = new WinLosePlayerScore(pp1, pp2,
							winLosePlayScore1.getScore()
									+ winLosePlayScore2.getScore()
									+ winLosePlayScore3.getScore());

				} else if (compareRet1 < 0 && compareRet2 < 0
						&& compareRet3 < 0) {
					winLosePlayScore1
							.setScore(winLosePlayScore1.getScore() * 2);
					winLosePlayScore2
							.setScore(winLosePlayScore2.getScore() * 2);
					winLosePlayScore3
							.setScore(winLosePlayScore3.getScore() * 2);
					Map map = new HashMap();
					map.put("gun", pp2.getAccount());
					map.put("guned", pp1.getAccount());
					listGun.add(map);
					winLosePlayerScore = new WinLosePlayerScore(pp2, pp1,
							winLosePlayScore1.getScore()
									+ winLosePlayScore2.getScore()
									+ winLosePlayScore3.getScore());
				} else {
					winLosePlayerScore = this.combine(winLosePlayScore1,
							winLosePlayScore2, winLosePlayScore3);
				}
				listAll.add(winLosePlayerScore);

				List<WinLosePlayerScore> list = mapWinLosePlayerScore.get(pp1
						.getAccount());
				if (list == null) {
					list = new ArrayList<WinLosePlayerScore>();
					mapWinLosePlayerScore.put(pp1.getAccount(), list);
				}
				list.add(winLosePlayerScore);

				list = mapWinLosePlayerScore.get(pp2.getAccount());
				if (list == null) {
					list = new ArrayList<WinLosePlayerScore>();
					mapWinLosePlayerScore.put(pp2.getAccount(), list);
				}
				list.add(winLosePlayerScore);
			}
		}

		if (listGun != null && listGun.size() != 0) {
			retScores.setListGun(listGun);
		}

		if (hasSpecial) {
			this.calculateFinalScores(retScores, listAll);
			return;
		}
		if (listNormal.size() <= 3) {
			this.calculateFinalScores(retScores, listAll);
			return;
		}

		String qldAccount = this.isQuanleiDa(listGun, listNormal.size());
		if (qldAccount == null) {
			this.calculateFinalScores(retScores, listAll);
			return;
		}

		this.ajustQuanleidaScore(qldAccount, mapWinLosePlayerScore);
		this.calculateFinalScores(retScores, listAll);
		return;

	}

	private WinLosePlayerScore getWinLosePlayerScore(int compareRet, int index,
			ThreeWaterPokerPlayer pp1, ThreeWaterPokerPlayer pp2) {
		if (compareRet == 0) {
			return new WinLosePlayerScore(pp1, pp2, 0);
		}
		ThreeWaterPokerPlayer winP, loseP;
		if (index == 1) {
			if (compareRet > 0) {
				winP = pp1;
				loseP = pp2;
			} else {
				winP = pp2;
				loseP = pp1;
			}
			PokerUnitCards pucs = winP.getThreeWaterPokerCards()
					.getFirstPokerCards();
			if (pucs.getName().equals("三条")) {
				return new WinLosePlayerScore(winP, loseP, 3);
			} else {
				return new WinLosePlayerScore(winP, loseP, 1);
			}
		} else if (index == 2) {
			if (compareRet > 0) {
				winP = pp1;
				loseP = pp2;
			} else {
				winP = pp2;
				loseP = pp1;
			}
			PokerUnitCards pucs = winP.getThreeWaterPokerCards()
					.getSecondPokerCards();
			if (pucs.getName().equals("葫芦")) {
				return new WinLosePlayerScore(winP, loseP, 2);
			} else if (pucs.getName().equals("同花顺")) {
				return new WinLosePlayerScore(winP, loseP, 10);
			} else if (pucs.getName().equals("铁支")) {
				return new WinLosePlayerScore(winP, loseP, 8);
			} else {
				return new WinLosePlayerScore(winP, loseP, 1);
			}
		} else if (index == 3) {
			if (compareRet > 0) {
				winP = pp1;
				loseP = pp2;
			} else {
				winP = pp2;
				loseP = pp1;
			}
			PokerUnitCards pucs = winP.getThreeWaterPokerCards()
					.getThirdPokerCards();
			if (pucs.getName().equals("同花顺")) {
				return new WinLosePlayerScore(winP, loseP, 5);
			} else if (pucs.getName().equals("铁支")) {
				return new WinLosePlayerScore(winP, loseP, 4);
			} else {
				return new WinLosePlayerScore(winP, loseP, 1);
			}
		} else {
			return null;
		}

	}

	private String isQuanleiDa(List<Map> listGun, int playerSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (Map tmpMap : listGun) {
			String gun = (String) tmpMap.get("gun");
			Integer integer = map.get(gun);
			if (integer == null) {
				map.put(gun, 1);
			} else {
				map.put(gun, integer + 1);
			}
		}
		for (String key : map.keySet()) {
			if (map.get(key) == playerSize - 1) {
				return key;
			}
		}
		return null;
	}

	private WinLosePlayerScore combine(WinLosePlayerScore winLosePlayScore1,
			WinLosePlayerScore winLosePlayScore2,
			WinLosePlayerScore winLosePlayScore3) {

		Player pp = winLosePlayScore1.getWinPlayer();
		int score = winLosePlayScore1.getScore();

		if (winLosePlayScore2.getWinPlayer() == pp) {
			score += winLosePlayScore2.getScore();
		} else {
			score -= winLosePlayScore2.getScore();
		}

		if (winLosePlayScore3.getWinPlayer() == pp) {
			score += winLosePlayScore3.getScore();
		} else {
			score -= winLosePlayScore3.getScore();
		}

		if (score >= 0) {
			return new WinLosePlayerScore(pp,
					winLosePlayScore1.getLosePlayer(), score);
		} else {
			return new WinLosePlayerScore(winLosePlayScore1.getLosePlayer(),
					pp, -score);
		}

	}

	private void ajustQuanleidaScore(String qldAccount,
			Map<String, List<WinLosePlayerScore>> mapWinLosePlayerScore) {
		List<WinLosePlayerScore> list = mapWinLosePlayerScore.get(qldAccount);
		for (WinLosePlayerScore ps : list) {
			ps.setScore(ps.getScore() * 2);
		}
	}

	private void calculateFinalScores(ThreeWaterPlayerScores retScores,
			List<WinLosePlayerScore> listAll) {
		for (WinLosePlayerScore ps : listAll) {
			retScores.addWinLosePlayerScore(ps);
		}
	}

}
