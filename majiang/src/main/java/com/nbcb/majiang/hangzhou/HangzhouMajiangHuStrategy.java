package com.nbcb.majiang.hangzhou;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.card.Card;
import com.nbcb.majiang.action.MajiangAction;
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
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.user.MajiangPlayer;

public class HangzhouMajiangHuStrategy extends DefaultMajiangHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(HangzhouMajiangHuStrategy.class);

	private MajiangHuJudger paodeHuJudger;

	public void setPaodeHuJudger(MajiangHuJudger paodeHuJudger) {
		this.paodeHuJudger = paodeHuJudger;
	}

	private int findAnGang(HangzhouMajiangGameInfo hzMjGameInfo,
			MajiangHuCards mcs) {

		List<MajiangUnitCards> listUcs = mcs.getListMjUnitCards();

		List<MajiangDuiziUnitCards> zeroBaidaUnitCards = new ArrayList<MajiangDuiziUnitCards>();
		List<MajiangDuiziUnitCards> oneBaidaUnitCards = new ArrayList<MajiangDuiziUnitCards>();
		List<MajiangDuiziUnitCards> twoBaidaUnitCards = new ArrayList<MajiangDuiziUnitCards>();

		for (MajiangUnitCards ucs : listUcs) {
			MajiangDuiziUnitCards duiziUcs = (MajiangDuiziUnitCards) ucs;
			logger.info("### duiziUcs " + duiziUcs);
			int size = duiziUcs.baidaCards().size();
			if (size == 2) {
				twoBaidaUnitCards.add(duiziUcs);
			} else if (size == 1) {
				oneBaidaUnitCards.add(duiziUcs);
			} else {
				zeroBaidaUnitCards.add(duiziUcs);
			}
		}

		int total = 0;
		Map<String, MajiangDuiziUnitCards> map = new HashMap<String, MajiangDuiziUnitCards>();

		for (MajiangDuiziUnitCards duizi : zeroBaidaUnitCards) {
			String type = duizi.getHeadCard().getType();
			MajiangDuiziUnitCards findDuiziUcs = map.get(type);
			if (findDuiziUcs == null) {
				map.put(type, duizi);
			} else {
				total += 1;
				map.remove(type);
			}
		}

		if (hzMjGameInfo.isBaidaHaoqi()) {
			for (MajiangDuiziUnitCards duizi : oneBaidaUnitCards) {
				String type = duizi.firstNonBaidaCard().getType();
				MajiangDuiziUnitCards findDuiziUcs = map.get(type);
				if (findDuiziUcs == null) {
					map.put(type, duizi);
				} else {
					total += 1;
					map.remove(type);
				}
			}
			if (twoBaidaUnitCards == null || twoBaidaUnitCards.size() == 0) {
				return total;
			} else if (twoBaidaUnitCards.size() == 1) {
				if (map.keySet().size() > 1) {
					total += 1;
				}
			} else if (twoBaidaUnitCards.size() == 2) {
				if (map.keySet().size() > 2) {
					total += 2;
				} else {
					total += 1;
				}
			}
		} else {
			if (twoBaidaUnitCards != null && twoBaidaUnitCards.size() == 2) {
				total += 1;
			}
		}
		return total;
	}

	private int findAnGang(MajiangHuCards mcs) {
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
		// 排除4个都是百搭的
		for (String key : map.keySet()) {
			if (map.get(key) != null) {
				for (MajiangUnitCards mucs : mcs.getListMjUnitCards()) {
					MajiangDuiziUnitCards duiziCards = (MajiangDuiziUnitCards) mucs;
					MajiangCard mc1 = (MajiangCard) duiziCards.getHeadCard();
					MajiangCard mc2 = (MajiangCard) duiziCards.getTailCard();
					if (mc1.getType().equals(key) || mc2.getType().equals(key)) {
						if (!mc1.getType().equals(mc2.getType())) {
							total--;
							break;
						}
					}
				}
			}
		}

		return total;
	}

	private String translateDetail(String detail) {
		String retStr = "";
		if (detail.contains(MajiangHuJudger.GANGTOUKAIHUA)) {
			if (detail.contains(MajiangHuJudger.PIAOCAI)) {
				retStr += "杠飘";
			} else if (detail.contains(MajiangHuJudger.PAODA)) {
				retStr += "杠爆";
			}
		} else {
			if (detail.contains(MajiangHuJudger.PIAOCAI)) {
				retStr += "财飘";
			} else if (detail.contains(MajiangHuJudger.PAODA)) {
				retStr += "爆头";
			}
		}

		if (detail.contains(MajiangHuJudger.QIDUIZI)) {
			if (detail.contains(MajiangHuJudger.GANG)) {
				retStr = "豪七" + retStr;
			} else {
				retStr = "七对子" + retStr;
			}
		}
		if (retStr.equals("")) {
			retStr = "平和";
		}
		return retStr;
	}

	private MajiangHuResult translate(HangzhouMajiangGame hzMjGame,
			MajiangHuResult huResult) {
		int unit = hzMjGame.getLaoShu() == 3 ? 8 : 4;
		String detail = huResult.getDetails();
		if (huResult.getFan() == 0) {
			huResult.setDetails("+" + unit);
			huResult.setFan(unit);
		} else {
			int fan = huResult.getFan();
			int initFan = unit;
			for (int i = 0; i < fan; i++) {
				initFan *= 2;
			}
			huResult.setDetails("+" + initFan);
			// huResult.setDetails("(" + huResult.getDetails() + ")+" +
			// initFan);
			huResult.setFan(initFan);
		}
		huResult.setDetails(this.translateDetail(detail)
				+ huResult.getDetails());
		return huResult;
	}

	@Override
	public MajiangHuResult findLegalHuResult(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {

		if (listHuResult == null || listHuResult.size() == 0) {
			return null;
		}
		Collections.sort(listHuResult);
		logger.info("### listHuResult " + listHuResult);

		HangzhouMajiangGame hzMjGame = (HangzhouMajiangGame) mjGame;
		HangzhouMajiangGameInfo gameInfo = (HangzhouMajiangGameInfo) mjGame
				.getGameInfo();
		MajiangHuResult maxHuResult = listHuResult.get(0);
		int huType = maxHuResult.getHuType();

		if (huType == MajiangAction.FANGQIANGHU) {
			logger.info("### 放冲胡");
			if (gameInfo.isZimoHu()) { // 只能自摸胡
				messageListener.listen(maxHuResult.getMjPlayer().getAccount(),
						"放冲不能胡");
				return null;
			}

			if (this.isPaoda(listHuResult)) {
				messageListener.listen(maxHuResult.getMjPlayer().getAccount(),
						"拷响放冲不能胡");
				return null;
			}

			if (mjGame.getDealer() != maxHuResult.getMjPlayer()
					&& mjGame.getDealer() != maxHuResult.getMjLosePlayer()) { // 闲家之间放枪
				messageListener.listen(maxHuResult.getMjPlayer().getAccount(),
						"闲家之间放冲不能胡");
				return null;
			}
			if (hzMjGame.getLaoShu() != 3) {
				messageListener.listen(maxHuResult.getMjPlayer().getAccount(),
						"二老放冲不能胡");
				return null;
			}
		}

		MajiangCards baidaCards = maxHuResult.getMjPlayer()
				.getMajiangInnerCards().baidaCards();
		if (baidaCards == null || baidaCards.size() == 0) { // 没有白搭
			return this.translate(hzMjGame, maxHuResult);
		}
		if (!gameInfo.isBaidaiKaoxiang()) {
			return this.translate(hzMjGame, maxHuResult);
		}

		// 有财必烤响的逻辑
		for (MajiangHuResult mjHuResult : listHuResult) {
			if (mjHuResult.isPaoda()) {
				return this.translate(hzMjGame, mjHuResult);
			}
		}
		messageListener.listen(maxHuResult.getMjPlayer().getAccount(),
				"有财神必须拷响才能够胡!");
		return null;
	}

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		HangzhouMajiangGameInfo hzMjGameInfo = (HangzhouMajiangGameInfo) mjGame
				.getGameInfo();

		FanDetail fd = new FanDetail();
		if (huType == MajiangAction.MOBACKHU) {
			int gangSize = majiangGangHelper.sequenceGangSize(mjGame, mjPlayer,
					1, true, true, true, true);
			fd.add(MajiangHuJudger.GANGTOUKAIHUA, gangSize);
		}
		HangzhouMajiangGame hzMjGame = (HangzhouMajiangGame) mjGame;
		if (hzMjGame.getBaidaPlayer() == mjPlayer) {
			fd.add(MajiangHuJudger.PIAOCAI, hzMjGame.getBaidaTimes());
			if (hzMjGame.getPiaocaiGangTimes() != 0) {
				fd.add(MajiangHuJudger.GANGTOUKAIHUA,
						hzMjGame.getPiaocaiGangTimes());
			}
		}
		if (mjHuCards.getHuFinderType() == HuFinderEnum.SEVENDUIZI) {
			fd.add(MajiangHuJudger.QIDUIZI, 1);
			for (MajiangUnitCards cs : mjHuCards.getListMjUnitCards()) {
				MajiangDuiziUnitCards duiziCs = (MajiangDuiziUnitCards) cs;
				MajiangCard c0 = (MajiangCard) duiziCs.getHeadCard();
				MajiangCard c1 = (MajiangCard) duiziCs.getTailCard();
				if (mjHuCards.getMjHuCard() == c0) {
					if (c1.isBaida()) {
						fd.add(MajiangHuJudger.PAODA, 1);
						fd.setPaoda(true);
						break;
					}
				}
				if (mjHuCards.getMjHuCard() == c1) {
					if (c0.isBaida()) {
						fd.add(MajiangHuJudger.PAODA, 1);
						fd.setPaoda(true);
						break;
					}
				}
			}
			int anGangSize = this.findAnGang(hzMjGameInfo, mjHuCards);
			if (anGangSize != 0) {
				fd.add(MajiangHuJudger.GANG, anGangSize);
			}

			if (mjHuCards.baidaCards().size() == 0) {
				fd.add(MajiangHuJudger.WUDA, 1);
			}
		} else {
			HuType mjHuType = paodeHuJudger.judge(mjGame, mjPlayer, huType,
					mjHuCards);
			if (mjHuType.isShooted()) {
				fd.add(MajiangHuJudger.PAODA, 1);
				fd.setPaoda(true);
			}
		}
		logger.info("### fd " + fd);
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(fd);
		return listFd;
	}

}
