package com.nbcb.majiang.ningbo.threebaida;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.ningbo.NingboMajiangGameInfo;
import com.nbcb.majiang.ningbo.fourbaida.NingboMajiang4BaidaHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger.QuanWeiFeng;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDuiduiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuandaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHunyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangMenqingHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger.PaihuDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaodeHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangQingyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangZfbHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang3BaidaHuStrategy extends
		NingboMajiang4BaidaHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang3BaidaHuStrategy.class);

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
						if (fanDetail.getDetail().contains(MajiangHuJudger.KAZHANG)) {
							fanDetail.substract(MajiangHuJudger.KAZHANG, 1);
						}
					}
				} else if (huCard.getFirstNumber() < mc1.getFirstNumber()) {
					if (mc2.getFirstNumber() > mc1.getFirstNumber()
							&& huCard.getFirstNumber() != 7) {
						if (fanDetail.getDetail().contains(MajiangHuJudger.KAZHANG)) {
							fanDetail.substract(MajiangHuJudger.KAZHANG, 1);
						}
					}
				}
			}
		}
		this.evoluatePaihu(mjHuCards, fanDetail);
	}

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		NingboMajiangGameInfo nbGameInfo = (NingboMajiangGameInfo) mjGame
				.getGameInfo();
		if (nbGameInfo.getJinHua() == 2 && nbGameInfo.getYeHua() == 1) {
			return super
					.calculateFanDetail(mjGame, mjPlayer, huType, mjHuCards);
		}
		logger.info("### NingboMajiang3BaidaHuStrategy calHuResultFan");
		FanDetail fd = new FanDetail();

		for (MajiangHuJudger mjHuJudger : listMjHuJudger) {
			HuType objHuType = mjHuJudger.judge(mjGame, mjPlayer, huType,
					mjHuCards);
			objHuType.setHuTypeName(mjHuJudger.getName());
			if (objHuType.isShooted()) {
				if (mjHuJudger instanceof MajiangMenqingHuJudger) {
					continue;
				}

				if (mjHuJudger instanceof MajiangHuaHuJudger) {
					HuaDetail huaDetail = (HuaDetail) objHuType.getDetail();
					int unitFan = huaDetail.getZhengHua()
							* nbGameInfo.getJinHua() + huaDetail.getYeHua()
							* nbGameInfo.getYeHua() + huaDetail.getSequence();
					if (unitFan > 0) {
						fd.add(MajiangHuJudger.HUA, unitFan);
					}
					continue;
				}
				if (mjHuJudger instanceof MajiangPaiHuJudger) {
					PaihuDetail paihuDetail = (PaihuDetail) objHuType
							.getDetail();
					if (paihuDetail.getDuiziType() == PaihuDetail.NORMAL
							|| paihuDetail.getDuiziType() == PaihuDetail.TWOBAIDA) {
						fd.add(MajiangHuJudger.PAIHU, 1);
					}
					continue;
				}
				if (mjHuJudger instanceof MajiangHuandaHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					fd.add(MajiangHuJudger.HUANDA, unitFan);
					continue;
				}
				if (mjHuJudger instanceof MajiangZfbHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					if (unitFan > 0) {
						fd.add(MajiangHuJudger.ZFB, unitFan);
					}
					continue;
				}
				if (mjHuJudger instanceof MajiangPaodeHuJudger) {
					fd.setPaoda(true);
					continue;
				}
				if (mjHuJudger instanceof MajiangDnxbHuJudger) {
					QuanWeiFeng qwf = (QuanWeiFeng) objHuType.getDetail();
					fd.add(MajiangHuJudger.QUANFENG, qwf.getQuanfeng());
					fd.add(MajiangHuJudger.WEIFENG, qwf.getWeifeng());
					continue;
				}
				if (mjHuJudger instanceof MajiangQingyiseHuJudger) {
					fd.add(mjHuJudger.getName(), 4);
					continue;
				}
				if (mjHuJudger instanceof MajiangDuiduiHuJudger) {
					fd.add(mjHuJudger.getName(), 2);
					continue;
				}
				if (mjHuJudger instanceof MajiangHunyiseHuJudger) {
					if (fd.includeHuType(MajiangHuJudger.QINGYISE)) {
						continue;
					}
					fd.add(mjHuJudger.getName(), 2);
					continue;
				}
				fd.add(mjHuJudger.getName(), mapHuFan.get(mjHuJudger.getName()));
			}
		}
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(fd);
		return listFd;
	}

	@Override
	public MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {

		NingboMajiangGameInfo nbGameInfo = (NingboMajiangGameInfo) mjGame
				.getGameInfo();
		if (nbGameInfo.getJinHua() == 2 && nbGameInfo.getYeHua() == 1) {
			return super.findLegalHuResultInner(mjGame, listHuResult);
		}

		int huType = listHuResult.get(0).getHuType();
		String huAccount = listHuResult.get(0).getMjPlayer().getAccount();
		NingboMajiangGameInfo gameInfo = (NingboMajiangGameInfo) mjGame
				.getGameInfo();

		boolean hasPaode = false;
		boolean has3Baida = false;

		for (MajiangHuResult mjHuResult : listHuResult) {
			logger.info("### mjHuResult[" + mjHuResult + "]");
			if (mjHuResult.isPaoda()) {
				hasPaode = true;
			}
			if (mjHuResult.getDetails().contains(MajiangHuJudger.SANBAIDA)) {
				has3Baida = true;
			}
		}
		if (huType == MajiangAction.FANGQIANGHU && (hasPaode || has3Baida)) {
			if (has3Baida) {
				messageListener.listen(huAccount, "三百搭放枪不能胡，只能自摸");
			} else if (hasPaode) {
				messageListener.listen(huAccount, "抛搭情况下放枪不能胡，只能自摸");
			}
			return null;
		}
		List<MajiangHuResult> listHuResult1 = new ArrayList<MajiangHuResult>();
		for (MajiangHuResult mjHuResult : listHuResult) {
			if (gameInfo.isHupengqing()) {
				if (!this.isHunPengQing(mjHuResult.getDetails())) {
					listHuResult1.add(mjHuResult);
					continue;
				}
				if (mjHuResult.getDetails().contains(MajiangHuJudger.SANBAIDA)) {
					if (mjHuResult.getFan() - 5 > gameInfo.getStartFan()) {
						listHuResult1.add(mjHuResult);
						continue;
					}
				}
			}
		}
		listHuResult.removeAll(listHuResult1);
		MajiangHuResult maxHuResult = listHuResult.get(0);
		if (maxHuResult.getFan() < gameInfo.getStartFan()) {
			messageListener.listen(huAccount, "台数不足" + gameInfo.getStartFan()
					+ "台(" + maxHuResult.getDetails() + ")");
			return null;
		}
		return listHuResult.get(0);
	}
}
