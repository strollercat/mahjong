package com.nbcb.majiang.fenhua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAllBaidaUnitCards;
import com.nbcb.majiang.card.MajiangAllDongUnitCards;
import com.nbcb.majiang.card.MajiangAllTiaoUnitCards;
import com.nbcb.majiang.card.MajiangAllWanUnitCards;
import com.nbcb.majiang.card.MajiangAllZiUnitCards;
import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.DefaultMajiangHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.finder.CombinedMajiangHuFinder;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDadiaoHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHunyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangQingyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangSanbaidaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger.QuanWeiFeng;
import com.nbcb.majiang.rule.judger.hu.type.MajiangGangHuJudger.GangDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger.PaihuDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuandaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangZfbHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class FenhuaMajiangHuStrategy extends DefaultMajiangHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(FenhuaMajiangHuStrategy.class);

	private List<MajiangHuJudger> listMjHuJudger1;

	public void setListMjHuJudger1(List<MajiangHuJudger> listMjHuJudger1) {
		this.listMjHuJudger1 = listMjHuJudger1;
	}

	@Override
	public MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {
		// TODO Auto-generated method stub
		for (MajiangHuResult huResult : listHuResult) {
			logger.info("### huResult[" + huResult + "]");
			if (huResult.isPaoda()) {
				if (huResult.getHuType() == MajiangAction.FANGQIANGHU) {
					return null;
				}
			}
		}
		MajiangHuResult maxResult = listHuResult.get(0);
		if (maxResult.getFan() == 0) {
			return null;
		}
		String details = maxResult.getDetails();
		if (!details.contains("垃圾胡")) {
			details = "(" + details + ")" + "+" + maxResult.getFan();
		}
		maxResult.setDetails(details);
		return maxResult;
	}

	private Map<String, HuType> runHuJudger(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		Map<String, HuType> shootedHuTypes = new HashMap<String, HuType>();

		for (MajiangHuJudger mjHuJudger : listMjHuJudger) {
			HuType objHuType = mjHuJudger.judge(mjGame, mjPlayer, huType,
					mjHuCards);
			objHuType.setHuTypeName(mjHuJudger.getName());
			if (objHuType.isShooted()) {
				shootedHuTypes.put(objHuType.getHuTypeName(), objHuType);
			}
		}
		return shootedHuTypes;
	}

	private void evoluatePaihu(MajiangHuCards mjHuCards, FanDetail fanDetail) {
		if (fanDetail.getDetail().contains(MajiangHuJudger.PAIHU)) {
			if (fanDetail.getDetail().contains(MajiangHuJudger.DANDIAO)
					|| fanDetail.getDetail().contains(MajiangHuJudger.KAZHANG)) {
				fanDetail.substract(MajiangHuJudger.PAIHU, 1);
			}
		}
	}

	@Override
	protected void evoluateKazhangPaihuHuanda(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		FanDetail fanDetail = listFd.get(0);
		/**
		 * 纠正一下234，打了4, 2是百搭的情况
		 */

		MajiangUnitCards mucs = mjHuCards.findHuCardUnitCards();
		if (!(mucs instanceof MajiangAnChiUnitCards)) {
			this.evoluatePaihu(mjHuCards, fanDetail);
			return;
		}
		MajiangCards copyCards = new MajiangCards();
		MajiangCard huCard = mjHuCards.getMjHuCard();

		copyCards.addTailCards(mucs.toArray());
		copyCards.removeCard(huCard);

		if (copyCards.size() != 2) {
			this.evoluatePaihu(mjHuCards, fanDetail);
			return;
		}

		if (copyCards.totalBaida() != 1) {
			this.evoluatePaihu(mjHuCards, fanDetail);
			return;
		}
		if (mjHuCards.getMjHuCard().isBaida()) {
			this.evoluatePaihu(mjHuCards, fanDetail);
			return;
		}
		// 不存在卡张的情况
		if (huCard.getFirstNumber() == 1 || huCard.getFirstNumber() == 9) {
			return;
		}

		MajiangCard mc1 = copyCards.firstNonBaidaCard();
		MajiangCard mc2 = (MajiangCard) copyCards.baidaCards().getHeadCard();

		// 肯定是卡张,因为之前的卡张肯定算过了
		if (Math.abs(mc1.getFirstNumber() - huCard.getFirstNumber()) == 1) {
			if (mc2.getType().length() != 1) {
				if (huCard.getFirstNumber() > mc1.getFirstNumber()) {
					// 这种情况下,卡张和还搭一起算了 ,把卡张干掉
					if (mc2.getFirstNumber() < mc1.getFirstNumber()
							&& mc2.getFirstNumber() != 1) {
						if (fanDetail.getDetail().contains(
								MajiangHuJudger.KAZHANG)) {
							fanDetail.substract(MajiangHuJudger.KAZHANG, 1);
						}
					}
				} else if (huCard.getFirstNumber() < mc1.getFirstNumber()) {
					if (mc2.getFirstNumber() > mc1.getFirstNumber()
							&& huCard.getFirstNumber() != 7) {
						if (fanDetail.getDetail().contains(
								MajiangHuJudger.KAZHANG)) {
							fanDetail.substract(MajiangHuJudger.KAZHANG, 1);
						}
					}
				}
			}
		}
		this.evoluatePaihu(mjHuCards, fanDetail);
	}

	private FanDetail big3(Map<String, HuType> shootedHuTypes,
			MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		FanDetail fanDetail = new FanDetail();

		for (MajiangHuJudger mjHuJudger : listMjHuJudger1) {
			HuType objHuType = mjHuJudger.judge(mjGame, mjPlayer, huType,
					mjHuCards);
			objHuType.setHuTypeName(mjHuJudger.getName());
			if (objHuType.isShooted()) {
				if (mjHuJudger instanceof MajiangPaiHuJudger) {
					PaihuDetail paihuDetail = (PaihuDetail) objHuType
							.getDetail();
					if (paihuDetail.getDuiziType() == PaihuDetail.NORMAL
							|| paihuDetail.getDuiziType() == PaihuDetail.TWOBAIDA) {
						fanDetail.add(MajiangHuJudger.PAIHU, 1);
					}
					continue;
				}
				if (mjHuJudger instanceof MajiangHuandaHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					fanDetail.add(MajiangHuJudger.HUANDA, unitFan);
					continue;
				}
				if (mjHuJudger instanceof MajiangZfbHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					if (unitFan != 0) {
						fanDetail.add(MajiangHuJudger.ZFB, unitFan);
					}
					continue;
				}
				if (mjHuJudger instanceof MajiangDnxbHuJudger) {
					QuanWeiFeng qwf = (QuanWeiFeng) objHuType.getDetail();
					fanDetail.add(MajiangHuJudger.QUANFENG, qwf.getQuanfeng());
					fanDetail.add(MajiangHuJudger.WEIFENG, qwf.getWeifeng());
					continue;
				}
				fanDetail.add(mjHuJudger.getName(), 1);
			}
		}
		MajiangCards baidaCards = mjHuCards.baidaCards();
		if (baidaCards.size() != 0) {
			fanDetail.add("百搭", baidaCards.size());
		}
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(fanDetail);
		this.evoluateKazhangPaihuHuanda(mjGame, mjPlayer, mjHuCards, huType,
				listFd);
		logger.info("### 垃圾胡detail[" + fanDetail + "]");
		return fanDetail.getFan() >= 3 ? fanDetail : null;

	}

	private int findAnGang(MajiangCards mcs) {
		Map<String, Object> map = new HashMap();
		int total = 0;
		for (int i = 0; i < mcs.size(); i++) {
			MajiangCard mc = (MajiangCard) mcs.getCard(i);
			Card cards[] = mcs.findCardsByType(mc.getType());
			if (cards != null && cards.length == 4) {
				if (map.get(mc.getType()) == null) {
					total++;
					map.put(mc.getType(), true);
				}
			}
		}
		return total;
	}

	private FanDetail calHuResultFan32(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {

		Map<String, HuType> shootedHuTypes = this.runHuJudger(mjGame, mjPlayer,
				huType, mjHuCards);

		FanDetail fanDetail = new FanDetail();
		if (shootedHuTypes.get(MajiangHuJudger.PAODA) != null) {
			fanDetail.setPaoda(true);
		}
		fanDetail.add(this.dadiaocheHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.qiangGangHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.sanBaidaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.huaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail
				.add(this.gangtoukaihuaHu(mjGame, mjPlayer, huType, mjHuCards));

		if (shootedHuTypes.get(MajiangHuJudger.GANG) != null) {
			HuType objHuType = shootedHuTypes.get(MajiangHuJudger.GANG);
			GangDetail gangDetail = (GangDetail) objHuType.getDetail();
			int gangFan = 0;
			if (gangDetail.getAnGang() != 0) {
				gangFan += 100 * gangDetail.getAnGang();
			}
			if (gangDetail.getXianGang() != 0) {
				gangFan += 100 * gangDetail.getXianGang();
			}
			if (gangDetail.getMingGang() != 0) {
				gangFan += 50 * gangDetail.getMingGang();
			}
			fanDetail.add(MajiangHuJudger.GANG, gangFan);
		}
		if (shootedHuTypes.get(MajiangHuJudger.FENGYISE) != null) {
			// logger.info("### 是清风");
			fanDetail.add(MajiangHuJudger.QINGLAOTOU, 1000);
		} else {
			if (shootedHuTypes.get(MajiangHuJudger.DUIDUIHU) != null) {
				MajiangCards baidaCards = mjHuCards.baidaCards();
				if (baidaCards == null || baidaCards.size() == 0) {
					fanDetail.add(MajiangHuJudger.DUIDUIHU, 100);
				} else {
					fanDetail.add(MajiangHuJudger.DUIDUIHU, +50);
				}
			}
			if (shootedHuTypes.get(MajiangHuJudger.QINGYISE) != null) {
				fanDetail.add(MajiangHuJudger.QINGYISE, 150);
			} else if (shootedHuTypes.get(MajiangHuJudger.HUNYISE) != null) {
				fanDetail.add(MajiangHuJudger.HUNYISE, 70);
			}
		}
		// logger.info("### fanDetail[" + fanDetail + "]");
		String details = fanDetail.getDetail();
		if (huType == MajiangAction.FANGQIANGHU
				&& !(details.contains(MajiangHuJudger.QINGYISE)
						|| details.contains(MajiangHuJudger.HUNYISE)
						|| details.contains(MajiangHuJudger.DADIAO)
						|| details.contains(MajiangHuJudger.QINGLAOTOU) || details
							.contains(MajiangHuJudger.DUIDUIHU))) {

			if (mjHuCards.baidaCards().size() == 3) {
				FanDetail retFd = new FanDetail();
				retFd.setPaoda(fanDetail.isPaoda());
				return retFd;
			} else {
				FanDetail big3FanDetail = this.big3(shootedHuTypes, mjGame,
						mjPlayer, huType, mjHuCards);
				if (big3FanDetail != null) {
					if (fanDetail.getFan() == 0) {
						FanDetail retFd = new FanDetail(MajiangHuJudger.LAJIHU,
								20);
						retFd.setPaoda(fanDetail.isPaoda());
						return retFd;
					} else {
						fanDetail.add("补", 10);
					}
				} else { // 未凑足基本三台，不能胡
					FanDetail retFd = new FanDetail();
					retFd.setPaoda(fanDetail.isPaoda());
					return retFd;
				}
			}
		} else {
			if (fanDetail.getFan() == 0) {
				FanDetail retFd = new FanDetail(MajiangHuJudger.LAJIHU, 20);
				retFd.setPaoda(fanDetail.isPaoda());
				return retFd;
			} else {
				if (fanDetail.getDetail().contains(MajiangHuJudger.QINGLAOTOU)
						&& fanDetail.getFan() == 1000) {

				} else {
					fanDetail.add("补", 10);
				}
			}
		}
		return fanDetail;
	}

	private FanDetail calHuResultFan7duizi(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {

		FanDetail fanDetail = new FanDetail();

		for (MajiangUnitCards cs : mjHuCards.getListMjUnitCards()) {
			MajiangDuiziUnitCards duiziCs = (MajiangDuiziUnitCards) cs;
			MajiangCard c0 = (MajiangCard) duiziCs.getHeadCard();
			MajiangCard c1 = (MajiangCard) duiziCs.getTailCard();
			if (mjHuCards.getMjHuCard() == c0) {
				if (c1.isBaida()) {
					fanDetail.setPaoda(true);
					break;
				}
			}
			if (mjHuCards.getMjHuCard() == c1) {
				if (c0.isBaida()) {
					fanDetail.setPaoda(true);
					break;
				}
			}
		}

		fanDetail.add(this.qiangGangHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.sanBaidaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.huaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail
				.add(this.gangtoukaihuaHu(mjGame, mjPlayer, huType, mjHuCards));

		int anGangSize = this.findAnGang(mjHuCards);
		if (anGangSize != 0) {
			fanDetail.add(MajiangHuJudger.GANG, anGangSize * 100);
		}
		if (this.getMajiangHuJudger(MajiangQingyiseHuJudger.class)
				.judge(mjGame, mjPlayer, huType, mjHuCards).isShooted()) {
			fanDetail.add(MajiangHuJudger.QINGYISE, 150);
		} else if (this.getMajiangHuJudger(MajiangHunyiseHuJudger.class)
				.judge(mjGame, mjPlayer, huType, mjHuCards).isShooted()) {
			fanDetail.add(MajiangHuJudger.HUNYISE, 70);
		}
		MajiangCards baidaCards = mjHuCards.baidaCards();
		if (baidaCards == null || baidaCards.size() == 0) {
			fanDetail.add(MajiangHuJudger.QIDUIZI, 180);
		} else {
			fanDetail.add(MajiangHuJudger.QIDUIZI, 80);
		}
		return fanDetail;
	}

	private FanDetail calHuResultFanDadahu(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {

		FanDetail fanDetail = new FanDetail();
		fanDetail.add(this.qiangGangHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.sanBaidaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.huaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail
				.add(this.gangtoukaihuaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(MajiangHuJudger.DADAHU, 50);

		MajiangAllZiUnitCards ziCs = null;
		MajiangAllBaidaUnitCards baidaCs = null;
		MajiangAllWanUnitCards wanCs = null;
		MajiangAllTiaoUnitCards tiaoCs = null;
		MajiangAllDongUnitCards dongCs = null;
		for (MajiangUnitCards mucs : mjHuCards.getListMjUnitCards()) {
			if (mucs instanceof MajiangAllZiUnitCards) {
				ziCs = (MajiangAllZiUnitCards) mucs;
			}
			if (mucs instanceof MajiangAllBaidaUnitCards) {
				baidaCs = (MajiangAllBaidaUnitCards) mucs;
			}
			if (mucs instanceof MajiangAllWanUnitCards) {
				wanCs = (MajiangAllWanUnitCards) mucs;
			}
			if (mucs instanceof MajiangAllTiaoUnitCards) {
				tiaoCs = (MajiangAllTiaoUnitCards) mucs;
			}
			if (mucs instanceof MajiangAllDongUnitCards) {
				dongCs = (MajiangAllDongUnitCards) mucs;
			}
		}
		if (ziCs.size() == 7) {
			if (huType == MajiangAction.MOFRONTHU
					|| huType == MajiangAction.MOBACKHU
					|| huType == MajiangAction.QIANGGANGHU) {
				fanDetail.add("暗七星", 150);
			} else {
				fanDetail.add("明七星+", 50);
			}
		}
		if (wanCs.size() == 0 || tiaoCs.size() == 0 || dongCs.size() == 0) {
			fanDetail.add("缺色", 150);
		}
		fanDetail.add("补", 10);
		return fanDetail;
	}

	private FanDetail calHuResultFanAllzi(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		FanDetail fanDetail = new FanDetail();
		fanDetail.add(this.qiangGangHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.sanBaidaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(this.huaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail
				.add(this.gangtoukaihuaHu(mjGame, mjPlayer, huType, mjHuCards));
		fanDetail.add(MajiangHuJudger.LUANLAOTOU, 500);
		return fanDetail;
	}

	private FanDetail dadiaocheHu(MajiangGame mjGame, MajiangPlayer mjPlayer,
			int huType, MajiangHuCards mjHuCards) {
		MajiangHuJudger huJudger = this
				.getMajiangHuJudger(MajiangDadiaoHuJudger.class);
		HuType objHuType = huJudger.judge(mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			MajiangDuiziUnitCards mdzucs = mjHuCards.findDuiziUnitCards();
			MajiangCard hc = (MajiangCard) mdzucs.getHeadCard();
			MajiangCard tc = (MajiangCard) mdzucs.getTailCard();
			if (hc.isBaida() || tc.isBaida()) {
				return new FanDetail(MajiangHuJudger.DADIAO, 50);
			} else {
				return new FanDetail(MajiangHuJudger.DADIAO, 100);
			}
		}
		return new FanDetail();
	}

	private FanDetail qiangGangHu(MajiangGame mjGame, MajiangPlayer mjPlayer,
			int huType, MajiangHuCards mjHuCards) {
		if (huType == MajiangAction.QIANGGANGHU) {
			return new FanDetail(MajiangHuJudger.QIANGGANGHU, 100);
		}
		return new FanDetail();

	}

	private FanDetail sanBaidaHu(MajiangGame mjGame, MajiangPlayer mjPlayer,
			int huType, MajiangHuCards mjHuCards) {

		MajiangHuJudger huJudger = this
				.getMajiangHuJudger(MajiangSanbaidaHuJudger.class);
		HuType objHuType = huJudger.judge(mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			MajiangCard tmpMc = (MajiangCard) mjHuCards.baidaCards()
					.getHeadCard();
			if (tmpMc.isUnit(MajiangCard.HUA)) {
				return new FanDetail(MajiangHuJudger.SANBAIDA, 300);
			} else {
				return new FanDetail(MajiangHuJudger.SANBAIDA, 150);
			}
		}
		return new FanDetail();
	}

	private FanDetail huaHu(MajiangGame mjGame, MajiangPlayer mjPlayer,
			int huType, MajiangHuCards mjHuCards) {
		MajiangHuJudger huJudger = this
				.getMajiangHuJudger(MajiangHuaHuJudger.class);
		HuType objHuType = huJudger.judge(mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			HuaDetail huaDetail = (HuaDetail) objHuType.getDetail();
			if (huaDetail.getSequence() != 0) {
				return new FanDetail(MajiangHuJudger.HUA, 150 * huaDetail.getSequence());
			}
		}
		return new FanDetail();
	}

	private FanDetail gangtoukaihuaHu(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		if (huType == MajiangAction.MOBACKHU) {
			int lastTwoType = mjGame.getHistoryActions()
					.getLastActionByIndex(2).getType();
			if (lastTwoType == MajiangAction.XIANGANG) {
				return new FanDetail(MajiangHuJudger.GANGTOUKAIHUA, 100);
			} else {
				return new FanDetail(MajiangHuJudger.GANGTOUKAIHUA, 50);
			}
		}
		return new FanDetail();
	}

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		FanDetail fanDetail = null;
		HuFinderEnum finderEnum = mjHuCards.getHuFinderType();
		if (finderEnum == HuFinderEnum.THREETWO) {
			fanDetail = this.calHuResultFan32(mjGame, mjPlayer, huType,
					mjHuCards);
		} else if (finderEnum == HuFinderEnum.SEVENDUIZI) {
			fanDetail = this.calHuResultFan7duizi(mjGame, mjPlayer, huType,
					mjHuCards);
		} else if (finderEnum == HuFinderEnum.DADAHU) {
			fanDetail = this.calHuResultFanDadahu(mjGame, mjPlayer, huType,
					mjHuCards);
		} else if (finderEnum == HuFinderEnum.ALLZI) {
			fanDetail = this.calHuResultFanAllzi(mjGame, mjPlayer, huType,
					mjHuCards);
		} else {
			fanDetail = new FanDetail();
		}
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(fanDetail);
		return listFd;
	}

}
