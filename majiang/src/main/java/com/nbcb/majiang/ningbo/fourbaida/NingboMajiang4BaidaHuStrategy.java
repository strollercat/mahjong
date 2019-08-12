package com.nbcb.majiang.ningbo.fourbaida;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAnChiUnitCards;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangDuiziUnitCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.ningbo.NingboMajiangGameInfo;
import com.nbcb.majiang.rule.judger.hu.DefaultMajiangHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangQingyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangSibaidaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger.QuanWeiFeng;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuandaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHunyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger.PaihuDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangSanbaidaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangZfbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.ZiHuaOrder;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang4BaidaHuStrategy extends DefaultMajiangHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang4BaidaHuStrategy.class);

	protected Map<String, Integer> mapHuFan;

	public void setMapHuFan(Map<String, Integer> mapHuFan) {
		this.mapHuFan = mapHuFan;
	}

	@Override
	protected void evoluateKazhangPaihuHuanda(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		FanDetail fanDetail = listFd.get(0);
		MajiangUnitCards mucs = mjHuCards.findHuCardUnitCards();
		if (!(mucs instanceof MajiangAnChiUnitCards)) {
			return;
		}
		MajiangCards copyCards = new MajiangCards();
		MajiangCard huCard = mjHuCards.getMjHuCard();

		copyCards.addTailCards(mucs.toArray());
		copyCards.removeCard(huCard);

		if (copyCards.size() != 2) {
			return;
		}
		if (copyCards.totalBaida() != 1) {
			return;
		}
		if (mjHuCards.getMjHuCard().isBaida()) {
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
	}

	@Override
	protected void evoluate23BaidaSame(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		FanDetail fd = listFd.get(0);
		MajiangDuiziUnitCards dzCards = mjHuCards.findDuiziUnitCards();
		List<MajiangAnPengUnitCards> listPengCards = mjHuCards
				.find3BaidaAnPengUnitCards();
		if (dzCards.totalBaida() != 2
				&& (listPengCards == null || listPengCards.size() == 0)) {
			return;
		}
		if (dzCards.totalBaida() == 2) {
			String unit = mjHuCards.firstNonBaidaCard().getSecondType();
			MajiangCard baidaCard = (MajiangCard) dzCards.baidaCards()
					.getHeadCard();
			if (fd.getDetail().contains(MajiangHuJudger.QINGYISE)) {
				// 如果白搭是字或者与清一色的单位不一样
				if (baidaCard.getType().length() == 1
						|| !baidaCard.getSecondType().equals(unit)) {
					fd.substract(MajiangHuJudger.HUANDA, 2);
				}
			} else if (fd.getDetail().contains(MajiangHuJudger.HUNYISE)) {
				// 如果白搭不是字并且与混一色的单位不一样
				if (baidaCard.getType().length() == 2
						&& !baidaCard.getSecondType().equals(unit)) {
					fd.substract(MajiangHuJudger.HUANDA, 2);
				} else {
					if (baidaCard.isUnit(MajiangCard.ZFB)) {
						fd.substract(MajiangHuJudger.PAIHU, 1);
					} else if (baidaCard.isUnit(MajiangCard.DNXB)) {
						int order = ZiHuaOrder
								.getDnxbOrder(baidaCard.getType());
						if (order == Math.abs(mjGame.getDealer()
								.getPlayerOrder() - mjPlayer.getPlayerOrder())) {
							fd.substract(MajiangHuJudger.PAIHU, 1);
						} else if (order == ((MajiangGameInfo) mjGame
								.getGameInfo()).getQuan()) {
							fd.substract(MajiangHuJudger.PAIHU, 1);
						}
					}
				}
			} else {
				if (baidaCard.isUnit(MajiangCard.ZFB)) {
					fd.substract(MajiangHuJudger.PAIHU, 1);
				} else if (baidaCard.isUnit(MajiangCard.DNXB)) {
					int order = ZiHuaOrder.getDnxbOrder(baidaCard.getType());
					if (order == Math.abs(mjGame.getDealer().getPlayerOrder()
							- mjPlayer.getPlayerOrder())) {
						fd.substract(MajiangHuJudger.PAIHU, 1);
					} else if (order == ((MajiangGameInfo) mjGame.getGameInfo())
							.getQuan()) {
						fd.substract(MajiangHuJudger.PAIHU, 1);
					}
				}
			}
		} else if (listPengCards != null && listPengCards.size() > 0) {
			MajiangAnPengUnitCards pengCards = listPengCards.get(0);
			if (fd.getDetail().contains(MajiangHuJudger.QINGYISE)) {
				String unit = mjHuCards.firstNonBaidaCard().getSecondType();
				MajiangCard baidaCard = (MajiangCard) pengCards.baidaCards()
						.getHeadCard();
				// 如果白搭是字或者与清一色的单位不一样
				if (baidaCard.getType().length() == 1
						|| !baidaCard.getSecondType().equals(unit)) {
					fd.substract(MajiangHuJudger.HUANDA, 3);
					fd.add(MajiangHuJudger.WEIFENG, 1);
					if (((MajiangGameInfo) mjGame.getGameInfo()).getQuan() == Math
							.abs(mjGame.getDealer().getPlayerOrder()
									- mjPlayer.getPlayerOrder())) {
						fd.add(MajiangHuJudger.QUANFENG, 1);
					}
				}
			} else if (fd.getDetail().contains(MajiangHuJudger.HUNYISE)) {
				String unit = mjHuCards.firstNonBaidaNonZiCard()
						.getSecondType();
				MajiangCard baidaCard = (MajiangCard) pengCards.baidaCards()
						.getHeadCard();
				// 如果白搭不是字并且与混一色的单位不一样
				if (baidaCard.getType().length() == 2
						&& !baidaCard.getSecondType().equals(unit)) {
					fd.substract(MajiangHuJudger.HUANDA, 3);
					fd.add(MajiangHuJudger.WEIFENG, 1);
					if (((MajiangGameInfo) mjGame.getGameInfo()).getQuan() == Math
							.abs(mjGame.getDealer().getPlayerOrder()
									- mjPlayer.getPlayerOrder())) {
						fd.add(MajiangHuJudger.QUANFENG, 1);
					}
				}
			}
		}

	}

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		FanDetail fd = new FanDetail();

		for (MajiangHuJudger mjHuJudger : listMjHuJudger) {
			HuType objHuType = mjHuJudger.judge(mjGame, mjPlayer, huType,
					mjHuCards);
			objHuType.setHuTypeName(mjHuJudger.getName());
			if (objHuType.isShooted()) {
				if (mjHuJudger instanceof MajiangHuaHuJudger) {
					NingboMajiangGameInfo nbGameInfo = (NingboMajiangGameInfo) mjGame
							.getGameInfo();
					HuaDetail huaDetail = (HuaDetail) objHuType.getDetail();
					int unitFan = huaDetail.getZhengHua()
							* nbGameInfo.getJinHua() + huaDetail.getYeHua()
							* nbGameInfo.getYeHua() + huaDetail.getSequence();
					if (unitFan > 0) {
						fd.add(mjHuJudger.getName(), unitFan);
					}
				} else if (mjHuJudger instanceof MajiangPaiHuJudger) {
					PaihuDetail paihuDetail = (PaihuDetail) objHuType
							.getDetail();
					if (paihuDetail.getDuiziType() == PaihuDetail.NORMAL
							|| paihuDetail.getDuiziType() == PaihuDetail.TWOBAIDA) {
						fd.add(mjHuJudger.getName(), 1);
					}
				} else if (mjHuJudger instanceof MajiangHuandaHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					if (unitFan > 0) {
						fd.add(mjHuJudger.getName(), unitFan);
					}
				} else if (mjHuJudger instanceof MajiangZfbHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					if (unitFan != 0) {
						fd.add(mjHuJudger.getName(), unitFan);
					}
				} else if (mjHuJudger instanceof MajiangDnxbHuJudger) {
					QuanWeiFeng qwf = (QuanWeiFeng) objHuType.getDetail();
					fd.add(MajiangHuJudger.QUANFENG, qwf.getQuanfeng());
					fd.add(MajiangHuJudger.WEIFENG, qwf.getWeifeng());
				} else if (mjHuJudger instanceof MajiangHunyiseHuJudger) {
					if (!fd.includeHuType(MajiangQingyiseHuJudger.QINGYISE)) {
						fd.add(mjHuJudger.getName(), 3);
					}
				} else if (mjHuJudger instanceof MajiangSanbaidaHuJudger) {
					if (!fd.includeHuType(MajiangSibaidaHuJudger.SIBAIDA)) {
						fd.add(mjHuJudger.getName(), 5);
					}
				} else {
					fd.add(mjHuJudger.getName(),
							mapHuFan.get(mjHuJudger.getName()));
				}
			}
		}
		if (fd.includeHuType(MajiangHuJudger.PAODA)) {
			fd.setPaoda(true);
		}
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(fd);
		return listFd;
	}

	protected boolean isHunPengQing(String huDetail) {
		return huDetail.contains(MajiangHuJudger.QINGYISE)
				|| huDetail.contains(MajiangHuJudger.HUNYISE)
				|| huDetail.contains(MajiangHuJudger.DUIDUIHU);
	}

	@Override
	protected MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {

		int huType = listHuResult.get(0).getHuType();
		String huAccount = listHuResult.get(0).getMjPlayer().getAccount();
		NingboMajiangGameInfo gameInfo = (NingboMajiangGameInfo) mjGame
				.getGameInfo();

		boolean hasPaode = false;
		boolean has3Baida = false;
		boolean has4Baida = false;
		MajiangHuResult maxHuResult = null;
		int maxFan = -1;

		for (MajiangHuResult mjHuResult : listHuResult) {

			logger.info("### mjHuResult[" + mjHuResult + "]");

			if (mjHuResult.isPaoda()) {
				hasPaode = true;
			}
			if (mjHuResult.getDetails().contains(MajiangHuJudger.SANBAIDA)) {
				has3Baida = true;
				if (mjHuResult.getFan() - 5 < gameInfo.getStartFan()) {
					continue;
				}
			}
			if (mjHuResult.getDetails().contains(MajiangHuJudger.SIBAIDA)) {
				has4Baida = true;
				if (mjHuResult.getFan() - 10 < gameInfo.getStartFan()) {
					continue;
				}
			}
			boolean right = false;
			if (gameInfo.isHupengqing()) {
				right = mjHuResult.getFan() > maxFan
						&& this.isHunPengQing(mjHuResult.getDetails());
			} else {
				right = mjHuResult.getFan() > maxFan;
			}

			if (right) {
				maxFan = mjHuResult.getFan();
				maxHuResult = mjHuResult;
			}
		}

		// 抛搭以及三百搭或者四百搭情况下，放枪胡不能够胡
		if (huType == MajiangAction.FANGQIANGHU
				&& (hasPaode || has3Baida || has4Baida)) {
			if (has3Baida) {
				messageListener.listen(huAccount, "三百搭放枪不能胡，只能自摸");
			} else if (has4Baida) {
				messageListener.listen(huAccount, "四百搭放枪不能胡，只能自摸");
			} else if (hasPaode) {
				messageListener.listen(huAccount, "抛搭情况下放枪不能胡，只能自摸");
			}
			return null;
		}
		if (maxFan < gameInfo.getStartFan()) {
			if (maxFan > 0) {
				messageListener.listen(
						huAccount,
						"台数不足" + gameInfo.getStartFan() + "台("
								+ maxHuResult.getDetails() + ")");
			}
			return null;
		}

		return maxHuResult;

	}

}
