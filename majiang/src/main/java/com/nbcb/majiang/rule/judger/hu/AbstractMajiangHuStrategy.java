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
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.finder.NonThreeTwoMajiangHuFinder;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.user.MajiangPlayer;

public abstract class AbstractMajiangHuStrategy implements MajiangHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractMajiangHuStrategy.class);

	private List<NonThreeTwoMajiangHuFinder> nonThreeTwoMajiangHuFinders = null;

	public void setNonThreeTwoMajiangHuFinders(
			List<NonThreeTwoMajiangHuFinder> nonThreeTwoMajiangHuFinders) {
		this.nonThreeTwoMajiangHuFinders = nonThreeTwoMajiangHuFinders;
	}

	private List<MajiangHuCards> findListHuCards(MajiangCards majiangCards,
			MajiangCard mjHuCard) {
		logger.info("#####################    start to find the legal hu cards      ################");
		if (majiangCards.size() % 3 != 2) {
			return null;
		}
		MajiangCards otherMajiangCards = new MajiangCards();
		otherMajiangCards.addTailCards(majiangCards.toArray());
		otherMajiangCards.sort();
		MajiangCards otherMajiangCards1 = new MajiangCards();
		for (int i = 0; i < otherMajiangCards.size(); i++) {
			MajiangCard tmpMc = (MajiangCard) otherMajiangCards.getCard(i);
			if (!tmpMc.isBaida()) {
				otherMajiangCards1.addHeadCard(tmpMc);
			} else {
				otherMajiangCards1.addTailCard(tmpMc);
			}
		}
		// logger.info("### majiangCards " + otherMajiangCards1);
		List<MajiangHuCards> listHuCards = new ArrayList<MajiangHuCards>();
		List<MajiangUnitCards> listUnitCards = new ArrayList<MajiangUnitCards>();
		MajiangHuRoads huRoads = new MajiangHuRoads(mjHuCard);
		long startTime = System.currentTimeMillis();
		this.findListHuCards(otherMajiangCards1, listUnitCards, listHuCards,
				huRoads);
		logger.info("################   end to find hu card listHuCards 耗费["
				+ (System.currentTimeMillis() - startTime) / 1000
				+ "]秒时间    ##########################");
		if (listHuCards != null && listHuCards.size() > 0) {
			logger.info("### 共找到[" + listHuCards.size() + "]种33332牌型");
			// for(int i = 0;i<listHuCards.size();i++){
			// logger.info(listHuCards.get(i)+"\r\n");
			// }
		} else {
			logger.info("### 未找到listHuCards");
		}

		return listHuCards.size() == 0 ? null : listHuCards;
	}

	private void findListHuCards(MajiangCards majiangCards,
			List<MajiangUnitCards> listUnitCards,
			List<MajiangHuCards> listHuCards, MajiangHuRoads huRoads) {
		// majiangInnerCards.sort();
		// System.out.println("majiangInnerCards " + majiangInnerCards);
		// System.out.println("listUnitCards " + listUnitCards);
		// System.out.println(" listHuCards " + listHuCards);
		// majiangInnerCards.sort();
		if (majiangCards.size() == 2) {
			MajiangCard mc1 = (MajiangCard) majiangCards.getHeadCard();
			MajiangCard mc2 = (MajiangCard) majiangCards.getTailCard();
			if (mc1.isBaida()
					|| mc2.isBaida()
					|| majiangCards.getHeadCard().getType()
							.equals(majiangCards.getTailCard().getType())) {
				MajiangDuiziUnitCards mdzucs = new MajiangDuiziUnitCards(mc1,
						mc2);
				// logger.info("mdzucs " + mdzucs);
				listUnitCards.add(mdzucs);
				MajiangHuCards mhcs = new MajiangHuCards();
				mhcs.setListMjUnitCards(listUnitCards);
				// logger.info("mhcs:" + mhcs);
				listHuCards.add(mhcs);
				listUnitCards.remove(mdzucs);
			}
			return;
		}
		// logger.info("####  door majiangCards " + majiangCards);
		Card[] arrCards = majiangCards.toArray();

		for (Card ccc : arrCards) {
			MajiangCard mjCard = (MajiangCard) ccc;
			// System.out.println(" mjCard " + mjCard);
			List<MajiangAnPengUnitCards> listMapucs = this
					.findMajiangAnPengUnitCards(majiangCards, mjCard);
			if (listMapucs != null && listMapucs.size() > 0) {
				for (MajiangAnPengUnitCards mapucs : listMapucs) {
					// logger.info("found the mapucs " + mapucs);
					listUnitCards.add(mapucs);
					// System.out.println("listUnitCards " + listUnitCards);
					// System.out.println("majiangInnerCards " +
					// majiangInnerCards);
					// System.out.println("mapucs.toArray() " +
					// (mapucs.toArray()==null?null:mapucs.toArray().length));
					// System.out.println(listUnitCards.size());
					if (huRoads.addListUnitCards(listUnitCards)) {
						// logger.info("huRoads\r\n" + huRoads);
						majiangCards.removeCards(mapucs.toArray());
						// System.out.println("majiangCards " +
						// majiangCards);
						this.findListHuCards(majiangCards, listUnitCards,
								listHuCards, huRoads);
						// listUnitCards.remove(mapucs);
						majiangCards.addTailCards(mapucs.toArray());
					}
					listUnitCards.remove(mapucs);
				}
			}

			List<MajiangAnChiUnitCards> listAnChiUnitCards = this
					.findMajiangAnChiUnitCards(majiangCards, mjCard);
			if (listAnChiUnitCards != null && listAnChiUnitCards.size() > 0) {
				for (MajiangAnChiUnitCards macucs : listAnChiUnitCards) {
					// logger.info("found the macucs " + macucs);
					listUnitCards.add(macucs);
					if (huRoads.addListUnitCards(listUnitCards)) {
						majiangCards.removeCards(macucs.toArray());
						// System.out.println("majiangCards " +
						// majiangCards);
						this.findListHuCards(majiangCards, listUnitCards,
								listHuCards, huRoads);
						majiangCards.addTailCards(macucs.toArray());
					}
					listUnitCards.remove(macucs);
				}
			}
		}
		return;
	}

	private List<MajiangAnPengUnitCards> findMajiangAnPengUnitCards(
			MajiangCards majiangCards, MajiangCard mjCard) {

		List<MajiangAnPengUnitCards> listAnPengUnitCards = new ArrayList<MajiangAnPengUnitCards>();
		Card[] pengCards = majiangCards.findCardsByType(mjCard.getType());
		if (mjCard.isBaida()) {
			if (pengCards == null || pengCards.length < 3) {
				return null;
			} else if (pengCards.length == 3) {
				listAnPengUnitCards.add(new MajiangAnPengUnitCards(
						pengCards[0], pengCards[1], pengCards[2]));
			} else if (pengCards.length == 4) {
				MajiangCards copyMcs = new MajiangCards();
				copyMcs.addTailCards(pengCards);
				copyMcs.removeCard(mjCard);
				listAnPengUnitCards.add(new MajiangAnPengUnitCards(copyMcs
						.getCard(0), copyMcs.getCard(1), mjCard));
			}
		} else {
			if ((majiangCards.totalBaida() + pengCards.length) >= 3) {
				MajiangCards copyMcs = new MajiangCards();
				copyMcs.addTailCards(pengCards);
				copyMcs.addTailCards(majiangCards.baidaCards().toArray());
				listAnPengUnitCards.addAll(this.chooseAllAnPeng(copyMcs));
			}
		}
		return listAnPengUnitCards.size() == 0 ? null : listAnPengUnitCards;
	}

	private List<MajiangAnPengUnitCards> chooseAllAnPeng(MajiangCards mjCards) {
		List<MajiangAnPengUnitCards> listAnPengUnitCards = new ArrayList<MajiangAnPengUnitCards>();
		int size = mjCards.size();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					if (i != j && j != k && k != i) {
						listAnPengUnitCards.add(new MajiangAnPengUnitCards(
								mjCards.getCard(i), mjCards.getCard(j), mjCards
										.getCard(k)));
					}
				}
			}
		}
		return listAnPengUnitCards;
	}

	private List<MajiangAnChiUnitCards> findMajiangAnChiUnitCards(
			MajiangCard mjCard, Card[] card1, Card[] card2) {

		List<MajiangAnChiUnitCards> listAnChiUnitCards = new ArrayList<MajiangAnChiUnitCards>();
		if (card1 == null || card1.length == 0) {
			return null;
		}
		if (card2 == null || card2.length == 0) {
			return null;
		}
		for (Card c1 : card1) {
			for (Card c2 : card2) {
				MajiangCard mc1 = (MajiangCard) c1;
				MajiangCard mc2 = (MajiangCard) c2;
				if (mc1 == mc2) {
					continue;
				}
				listAnChiUnitCards.add(new MajiangAnChiUnitCards(mjCard, mc1,
						mc2));
			}
		}
		return listAnChiUnitCards.size() == 0 ? null : listAnChiUnitCards;
	}

	private List<MajiangAnChiUnitCards> findMajiangAnChiUnitCards(
			MajiangCards majiangCards, MajiangCard mjCard) {
		List<MajiangAnChiUnitCards> retList = new ArrayList<MajiangAnChiUnitCards>();

		if (mjCard.isUnit(MajiangCard.DNXB) || mjCard.isUnit(MajiangCard.ZFB)
				|| mjCard.isUnit(MajiangCard.HUA)) {
			return null;
		}

		int number = mjCard.getFirstNumber();
		String unit = mjCard.getSecondType();
		int numMin2 = number - 2;
		int numMin1 = number - 1;
		int numAdd1 = number + 1;
		int numAdd2 = number + 2;
		String strMin2 = numMin2 + unit;
		String strMin1 = numMin1 + unit;
		String strAdd2 = numAdd2 + unit;
		String strAdd1 = numAdd1 + unit;
		Card[] cardMin2 = majiangCards.findCardsByType(strMin2);
		Card[] cardMin1 = majiangCards.findCardsByType(strMin1);
		Card[] cardAdd1 = majiangCards.findCardsByType(strAdd1);
		Card[] cardAdd2 = majiangCards.findCardsByType(strAdd2);
		List<MajiangAnChiUnitCards> tmpList = null;
		if (mjCard.isBaida()) {
			tmpList = this
					.findMajiangAnChiUnitCards(mjCard, cardMin2, cardMin1);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this
					.findMajiangAnChiUnitCards(mjCard, cardMin1, cardAdd1);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this
					.findMajiangAnChiUnitCards(mjCard, cardAdd1, cardAdd2);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
		} else {
			Card[] baidaCards = majiangCards.baidaCards().toArray();
			tmpList = this
					.findMajiangAnChiUnitCards(mjCard, cardMin2, cardMin1);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this
					.findMajiangAnChiUnitCards(mjCard, cardMin1, cardAdd1);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this
					.findMajiangAnChiUnitCards(mjCard, cardAdd1, cardAdd2);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}

			// baida start
			tmpList = this.findMajiangAnChiUnitCards(mjCard, cardMin2,
					baidaCards);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this.findMajiangAnChiUnitCards(mjCard, cardMin1,
					baidaCards);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this.findMajiangAnChiUnitCards(mjCard, cardAdd1,
					baidaCards);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this.findMajiangAnChiUnitCards(mjCard, cardAdd2,
					baidaCards);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
			tmpList = this.findMajiangAnChiUnitCards(mjCard, baidaCards,
					baidaCards);
			if (tmpList != null) {
				retList.addAll(tmpList);
			}
		}
		return retList.size() == 0 ? null : retList;
	}

	@Override
	public List<MajiangHuCards> findLegalHuCards(MajiangGame mjGame,
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard) {

		List<MajiangHuCards> list = this.findListHuCards(mjCards, mjCard);
		if (list == null) {
			list = new ArrayList<MajiangHuCards>();
		}
		for (MajiangHuCards mhcs : list) {
			mhcs.setMjHuCard(mjCard);
			if (listMiddleMjUnitCards != null
					&& listMiddleMjUnitCards.size() != 0) {
				for (MajiangUnitCards mucs : listMiddleMjUnitCards) {
					mhcs.addMajiangUnitCards(mucs);
				}
			}
		}
		if (nonThreeTwoMajiangHuFinders != null) {
			for (NonThreeTwoMajiangHuFinder finder : nonThreeTwoMajiangHuFinders) {
				List<MajiangHuCards> nonThreeList = finder.findLegalHuCards(
						listMiddleMjUnitCards, mjCards, mjCard);
				if (nonThreeList != null && nonThreeList.size() != 0) {
					list.addAll(nonThreeList);
				}
			}
		}
		logger.info("### all mjhucard " + list);
		return list;
	}

	@Override
	public MajiangHuResult findLegalHuResult(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {
		// TODO Auto-generated method stub
		if (listHuResult == null || listHuResult.size() == 0) {
			return null;
		}
		Collections.sort(listHuResult);
		if (listHuResult.get(0).getFan() <= 0) {
			return null;
		}
		return this.findLegalHuResultInner(mjGame, listHuResult);
	}

	@Override
	public List<MajiangHuResult> calHuResultFan(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType,
			List<MajiangHuCards> listMjHuCards) {
		List<MajiangHuResult> listRet = new ArrayList<MajiangHuResult>();
		for (MajiangHuCards mhcs : listMjHuCards) {
			listRet.add(this.calHuResultFan(mjGame, mjPlayer, huType, mhcs));
		}
		return listRet;
	}

	protected MajiangHuResult calHuResultFan(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		List<FanDetail> listFd = this.calculateFanDetail(mjGame, mjPlayer,
				huType, mjHuCards);
		FanDetail finalFd;
		if (mjHuCards.getHuFinderType() == HuFinderEnum.THREETWO) {
			this.evoluateKazhangPaihuHuanda(mjGame, mjPlayer, mjHuCards,
					huType, listFd);
			this.evoluate23BaidaSame(mjGame, mjPlayer, mjHuCards, huType,
					listFd);
			finalFd = this.decideFinalFanDetail(listFd);
		} else {
			finalFd = listFd.get(0);
		}

		MajiangHuResult mjHuResult = new MajiangHuResult();
		mjHuResult.setDetails(finalFd.getDetail());
		mjHuResult.setFan(finalFd.getFan());
		mjHuResult.setHuType(huType);
		mjHuResult.setMjHuCards(mjHuCards);
		mjHuResult.setMjPlayer(mjPlayer);
		mjHuResult.setPaoda(finalFd.isPaoda());
		return mjHuResult;
	}

	/**
	 * 具体计算相应的fandetail
	 * 
	 * @param mjGame
	 * @param mjPlayer
	 * @param huType
	 * @param mjHuCards
	 * @return
	 */
	abstract protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards);

	/**
	 * 具体最后显示给用户的FanDetail
	 * 
	 * @param listFd
	 * @return
	 */
	abstract protected FanDetail decideFinalFanDetail(List<FanDetail> listFd);

	/**
	 * 重新评估一下卡张牌胡还搭三者的关系，对一些矛盾的要进行还原 如 2 3 4，2是百搭，打或者摸了4
	 * 
	 * @param mjGame
	 * @param mjPlayer
	 * @param mjHuCards
	 * @param listFd
	 */
	abstract protected void evoluateKazhangPaihuHuanda(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd);

	/**
	 * 重新评估一下两个一样的对子白搭 或者三个一样的对子白搭，对前面有差错的进行复原
	 * 
	 * @param mjGame
	 * @param mjPlayer
	 * @param mjHuCards
	 * @param listFd
	 */
	abstract protected void evoluate23BaidaSame(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd);

	/**
	 * 选出最大的并且合法的huresult
	 * 
	 * @param mjGame
	 * @param listHuResult
	 * @return
	 */
	abstract protected MajiangHuResult findLegalHuResultInner(
			MajiangGame mjGame, List<MajiangHuResult> listHuResult);
}
