package com.nbcb.majiang.tiantai;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangAnGangUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.DefaultMajiangHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDandiaoHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger.QuanWeiFeng;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDuiduiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHunyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangKadaoHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger.PaihuDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaodeHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangQingyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangZfbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.ZiHuaOrder;
import com.nbcb.majiang.user.MajiangPlayer;

public class TiantaiMajiangHuStrategy extends DefaultMajiangHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(TiantaiMajiangHuStrategy.class);

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		FanDetail fd = new FanDetail();
		if (mjHuCards.getHuFinderType() == HuFinderEnum.SIBAIDA) {
			logger.info("### sibaida");
			if (huType != MajiangAction.FANGQIANGHU) {
				fd.add(MajiangHuJudger.SIBAIDA,
						((TiantaiMajiangGameInfo) mjGame.getGameInfo())
								.getMaxFan());
			}
			List<FanDetail> listFd = new ArrayList<FanDetail>();
			listFd.add(fd);
			return listFd;
		} else {
			return this.cal32FanDetail(mjGame, mjPlayer, huType, mjHuCards);
		}
	}

	@Override
	protected FanDetail decideFinalFanDetail(List<FanDetail> listFd) {
		FanDetail taiFd = listFd.get(0);
		FanDetail huFd = listFd.get(1);
		int lastFan = huFd.getFan();
		for (int i = 0; i < taiFd.getFan(); i++) {
			lastFan = lastFan * 2;
		}
		if (lastFan % 10 != 0) {
			lastFan = (lastFan / 10 + 1) * 10;
		}

		String details = "";
		if (taiFd.getFan() > 0) {
			details = "(" + taiFd.getDetail() + ")(" + huFd.getDetail() + ")";

		} else {
			details = "(" + huFd.getDetail() + ")";
		}
		FanDetail finalFd = new FanDetail(details, lastFan);
		finalFd.setPaoda(taiFd.isPaoda());
		return finalFd;
	}

	@Override
	public MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {

		for (MajiangHuResult huResult : listHuResult) {
			logger.info("### huResult[" + huResult + "]");
			if (huResult.isPaoda()) {
				if (huResult.getHuType() == MajiangAction.FANGQIANGHU) {
					return null;
				}
			}
		}

		MajiangHuResult maxResult = listHuResult.get(0);
		TiantaiMajiangGame ttGame = (TiantaiMajiangGame) mjGame;
		TiantaiMajiangGameInfo ttGameInfo = (TiantaiMajiangGameInfo) ttGame
				.getGameInfo();
		if (maxResult.getFan() == 0) {
			return null;
		}
		String details = maxResult.getDetails();
		if (maxResult.getFan() > ttGameInfo.getMaxFan()) {
			maxResult.setFan(ttGameInfo.getMaxFan());
			details = details + " " + ttGameInfo.getMaxFan() + "胡";
			maxResult.setDetails(details);
		} else {
			details = details + " " + maxResult.getFan() + "胡";
			maxResult.setDetails(details);
		}
		return maxResult;
	}

	private boolean ziOr19(MajiangCard mc) {
		if (mc.isUnit(MajiangCard.ZFB)) {
			return true;
		}
		if (mc.isUnit(MajiangCard.DNXB)) {
			return true;
		}
		if (mc.isUnit(MajiangCard.HUA)) {
			return false;
		}
		int number = Integer.parseInt(mc.getType().substring(0, 1));
		if (number == 1 || number == 9) {
			return true;
		}
		return false;
	}

	private void evoluatePaihu(MajiangHuCards mjHuCards, FanDetail taiFd,
			FanDetail huFd) {
		if (taiFd.getDetail().contains(MajiangHuJudger.PAIHU)) {
			if (huFd.getDetail().contains(MajiangHuJudger.DANDIAO)
					|| huFd.getDetail().contains(MajiangHuJudger.KAZHANG)) {
				taiFd.substract(MajiangHuJudger.PAIHU, 1);
			}
		}
	}

	private List<FanDetail> cal32FanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {

		HuType objHuType = null;
		FanDetail huFd = new FanDetail();
		huFd.add("底", 20);
		FanDetail taiFd = new FanDetail();

		if (huType == MajiangAction.MOBACKHU) {
			taiFd.add(MajiangHuJudger.GANGTOUKAIHUA, 1);
		}
		if (huType == MajiangAction.QIANGGANGHU) {
			taiFd.add(MajiangHuJudger.QIANGGANGHU, 1);
		}

		objHuType = this.getMajiangHuJudger(MajiangQingyiseHuJudger.class)
				.judge(mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			taiFd.add(MajiangHuJudger.QINGYISE, 3);
		}
		objHuType = this.getMajiangHuJudger(MajiangHuaHuJudger.class).judge(
				mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			HuaDetail hd = (HuaDetail) objHuType.getDetail();
			int huaTai = hd.getSequence() + hd.getZhengHua();
			if (huaTai != 0) {
				taiFd.add(MajiangHuJudger.HUA, huaTai);
			}
			huaTai = hd.getYeHua() + hd.getZhengHua();
			if (huaTai != 0) {
				huFd.add(MajiangHuJudger.HUA, 4 * huaTai);
			}
		}
		if (!taiFd.getDetail().contains(MajiangHuJudger.QINGYISE)) {
			objHuType = this.getMajiangHuJudger(MajiangHunyiseHuJudger.class)
					.judge(mjGame, mjPlayer, huType, mjHuCards);
			if (objHuType.isShooted()) {
				taiFd.add(MajiangHuJudger.HUNYISE, 1);
			}
		}
		objHuType = this.getMajiangHuJudger(MajiangDuiduiHuJudger.class).judge(
				mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			taiFd.add(MajiangHuJudger.DUIDUIHU, 1);
		}
		objHuType = this.getMajiangHuJudger(MajiangPaiHuJudger.class).judge(
				mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			PaihuDetail paihuDetail = (PaihuDetail) objHuType.getDetail();
			if (paihuDetail.getDuiziType() == PaihuDetail.NORMAL
					|| paihuDetail.getDuiziType() == PaihuDetail.TWOBAIDA
					|| paihuDetail.getDuiziType() == PaihuDetail.QF) {
				taiFd.add(MajiangHuJudger.PAIHU, 1);
			}
		}

		objHuType = this.getMajiangHuJudger(MajiangZfbHuJudger.class).judge(
				mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			int fan = ((Integer) objHuType.getDetail()).intValue();
			if (fan > 0) {
				taiFd.add(MajiangHuJudger.ZFB, fan);
			}
		}
		objHuType = this.getMajiangHuJudger(MajiangDnxbHuJudger.class).judge(
				mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			QuanWeiFeng qwf = (QuanWeiFeng) objHuType.getDetail();
			if (qwf.getWeifeng() != 0) {
				taiFd.add(MajiangHuJudger.WEIFENG, qwf.getWeifeng());
			}
		}

		if (huType != MajiangAction.FANGQIANGHU) {
			huFd.add(MajiangHuJudger.ZIMO, 2);
		}
		objHuType = this.getMajiangHuJudger(MajiangKadaoHuJudger.class).judge(
				mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			huFd.add(MajiangHuJudger.KAZHANG, 2);
		}

		objHuType = this.getMajiangHuJudger(MajiangDandiaoHuJudger.class)
				.judge(mjGame, mjPlayer, huType, mjHuCards);
		if (objHuType.isShooted()) {
			huFd.add(MajiangHuJudger.DANDIAO, 2);
		}

		MajiangCard mc = null;
		int gangHu = 0;
		int pengHu = 0;
		int duiziHu = 0;
		for (MajiangUnitCards mucs : mjHuCards.getListMjUnitCards()) {
			if (mucs instanceof MajiangAnGangUnitCards) {
				mc = mucs.firstNonBaidaCard();
				if (this.ziOr19(mc)) {
					gangHu += 32;
				} else {
					gangHu += 16;
				}
			} else if (mucs instanceof MajiangMingGangUnitCards
					|| mucs instanceof MajiangXianGangUnitCards) {
				mc = mucs.firstNonBaidaCard();
				if (this.ziOr19(mc)) {
					gangHu += 16;
				} else {
					gangHu += 8;
				}
			} else if (mucs instanceof MajiangAnPengUnitCards) {
				if (mucs.baidaCards().size() != 3) {
					mc = mucs.firstNonBaidaCard();
					if (this.ziOr19(mc)) {
						if (mucs.containsCard(mjHuCards.getMjHuCard())
								&& huType == MajiangAction.FANGQIANGHU) {
							pengHu += 4;
						} else {
							pengHu += 8;
						}
					} else {
						if (mucs.containsCard(mjHuCards.getMjHuCard())
								&& huType == MajiangAction.FANGQIANGHU) {
							pengHu += 2;
						} else {
							pengHu += 4;
						}
					}
				}
			} else if (mucs instanceof MajiangPengUnitCards) {
				mc = mucs.firstNonBaidaCard();
				if (this.ziOr19(mc)) {
					pengHu += 4;
				} else {
					pengHu += 2;
				}
			} else if (mucs instanceof MajiangDuiziUnitCards) {
				if (mucs.baidaCards().size() != 2) {
					mc = mucs.firstNonBaidaCard();
					if (mc.isUnit(MajiangCard.ZFB)) {
						duiziHu += 2;
					} else if (mc.isUnit(MajiangCard.DNXB)) {
						Integer order = ZiHuaOrder.getDnxbOrder(mc.getType());
						if (order.intValue() == ((mjPlayer.getPlayerOrder()
								- mjGame.getDealer().getPlayerOrder() + 4) % 4)) {
							duiziHu += 2;
						}
					}
				}
			}
		}
		if (gangHu != 0) {
			huFd.add(MajiangHuJudger.GANG, gangHu);
		}
		if (pengHu != 0) {
			huFd.add(MajiangHuJudger.PENG, pengHu);
		}
		if (duiziHu != 0) {
			huFd.add(MajiangHuJudger.DUIZI, duiziHu);
		}

		if (this.getMajiangHuJudger(MajiangPaodeHuJudger.class)
				.judge(mjGame, mjPlayer, huType, mjHuCards).isShooted()) {
			taiFd.setPaoda(true);
		}
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(taiFd);
		listFd.add(huFd);
		return listFd;
	}

	@Override
	protected void evoluateKazhangPaihuHuanda(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		FanDetail taiFd = listFd.get(0);
		FanDetail huFd = listFd.get(1);

		if (!taiFd.getDetail().contains(MajiangHuJudger.PAIHU)) {
			return;
		}
		if (taiFd.getDetail().contains(MajiangHuJudger.DANDIAO)) {
			taiFd.substract(MajiangHuJudger.PAIHU, 1);
			return;
		}

		/**
		 * 纠正一下234，打了4, 2是百搭的情况
		 */

		MajiangUnitCards mucs = mjHuCards.findHuCardUnitCards();
		if (!(mucs instanceof MajiangAnChiUnitCards)) {
			this.evoluatePaihu(mjHuCards, taiFd, huFd);
			return;
		}
		MajiangCards copyCards = new MajiangCards();
		MajiangCard huCard = mjHuCards.getMjHuCard();

		copyCards.addTailCards(mucs.toArray());
		copyCards.removeCard(huCard);

		if (copyCards.size() != 2) {
			this.evoluatePaihu(mjHuCards, taiFd, huFd);
			return;
		}
		if (mjHuCards.getMjHuCard().isBaida()) {
			this.evoluatePaihu(mjHuCards, taiFd, huFd);
			return;
		}
		if (copyCards.totalBaida() == 2) {
			// 如果有排胡
			if (taiFd.getDetail().contains(MajiangHuJudger.PAIHU)) {
				if (huCard.getType().length() == 2
						&& (huCard.getFirstNumber() == 1 || huCard
								.getFirstNumber() == 9)) { // 不可能卡张,该怎么着怎么着
					return;
				} else { // 这个时候肯定算卡张了,把卡张去掉
					huFd.substract(MajiangHuJudger.KAZHANG, 2);
					return;
				}
			} else { // 没有排胡，该怎么着，怎么着
				return;
			}
		}

		if (copyCards.totalBaida() == 0) {
			this.evoluatePaihu(mjHuCards, taiFd, huFd);
			return;
		}
		// 接下来就是百搭个数为1的情况了
		// 不存在卡张的情况
		if (huCard.getFirstNumber() == 1 || huCard.getFirstNumber() == 9) {
			this.evoluatePaihu(mjHuCards, taiFd, huFd);
			return;
		}

		MajiangCard mc1 = copyCards.firstNonBaidaCard();
		MajiangCard mc2 = (MajiangCard) copyCards.baidaCards().getHeadCard();

		// 肯定是卡张,因为之前的卡张肯定算过了
		if (Math.abs(mc1.getFirstNumber() - huCard.getFirstNumber()) == 1) {
			if (taiFd.getDetail().contains(MajiangHuJudger.PAIHU)) {
				if (mc1.getFirstNumber() > huCard.getFirstNumber()) {
					if (huCard.getFirstNumber() == 7) { // 肯定卡张
						this.evoluatePaihu(mjHuCards, taiFd, huFd);
					} else { // 把卡张的干掉
						huFd.substract(MajiangHuJudger.KAZHANG, 2);
					}
				} else {
					if (huCard.getFirstNumber() == 3) { // 肯定卡张
						this.evoluatePaihu(mjHuCards, taiFd, huFd);
					} else { // 把卡张的干掉
						huFd.substract(MajiangHuJudger.KAZHANG, 2);
					}
				}
			} else { // 没有排胡啥都不要干
				return;
			}
		}

	}

	@Override
	protected void evoluate23BaidaSame(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		// TODO Auto-generated method stub

		FanDetail taiFd = listFd.get(0);
		FanDetail huFd = listFd.get(1);

		MajiangDuiziUnitCards dzCards = mjHuCards.findDuiziUnitCards();
		List<MajiangAnPengUnitCards> listPengCards = mjHuCards
				.find3BaidaAnPengUnitCards();

		if (dzCards.totalBaida() == 2) {
			if (!taiFd.getDetail().contains(MajiangHuJudger.QINGYISE)
					&& !taiFd.getDetail().contains(MajiangHuJudger.PAIHU)) {
				huFd.add(MajiangHuJudger.DUIZI, 2);
			}
		} else if (listPengCards != null && listPengCards.size() > 0) {
			MajiangUnitCards mucs = listPengCards.get(0);
			if (!taiFd.getDetail().contains(MajiangHuJudger.QINGYISE)) {
				taiFd.add(MajiangHuJudger.ZFB, 1);
			}
			if (mucs.containsCard(mjHuCards.getMjHuCard())
					&& huType == MajiangAction.FANGQIANGHU) {
				huFd.add(MajiangHuJudger.PENG, 4);
			} else {
				huFd.add(MajiangHuJudger.PENG, 8);
			}
		}
	}

}
