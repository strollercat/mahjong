package com.nbcb.majiang.rule.judger.hu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.majiang.card.MajiangChiUnitCards;
import com.nbcb.majiang.card.MajiangMiddleCards;
import com.nbcb.majiang.card.MajiangMingGangUnitCards;
import com.nbcb.majiang.card.MajiangPengUnitCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.card.MajiangXianGangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangThreeActiveBao implements MajiangBaoCalculator {

	private int times = 3;

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public List<MajiangPlayer> calculatorMajiangBaos(MajiangGame mjGame,
			MajiangHuResult mjHuResult, MajiangPlayer mjPlayer) {
		// TODO Auto-generated method stub
		List<MajiangPlayer> listMjPlayer = new ArrayList<MajiangPlayer>();

		MajiangMiddleCards mmcs = mjPlayer.getMajiangMiddleCards();
		List<MajiangUnitCards> listUnitCards = mmcs.getListUnitCards();
		if (listUnitCards == null || listUnitCards.size() == 0) {
			return null;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (MajiangUnitCards mucs : listUnitCards) {
			if (mucs instanceof MajiangPengUnitCards) {
				MajiangPengUnitCards tmpMucs = (MajiangPengUnitCards) mucs;
				String account = tmpMucs.getMjDaPlayer().getAccount();
				Integer intRet = map.get(account);
				if (intRet == null) {
					map.put(account, 1);
				} else {
					map.put(account, intRet.intValue() + 1);
				}
			}
			if (mucs instanceof MajiangChiUnitCards) {
				MajiangChiUnitCards tmpMucs = (MajiangChiUnitCards) mucs;
				String account = tmpMucs.getMjDaPlayer().getAccount();
				Integer intRet = map.get(account);
				if (intRet == null) {
					map.put(account, 1);
				} else {
					map.put(account, intRet.intValue() + 1);
				}
			}
			if (mucs instanceof MajiangMingGangUnitCards) {
				MajiangMingGangUnitCards tmpMucs = (MajiangMingGangUnitCards) mucs;
				String account = tmpMucs.getMjDaPlayer().getAccount();
				Integer intRet = map.get(account);
				if (intRet == null) {
					map.put(account, 1);
				} else {
					map.put(account, intRet.intValue() + 1);
				}
			}
			if (mucs instanceof MajiangXianGangUnitCards) {
				MajiangXianGangUnitCards tmpMucs = (MajiangXianGangUnitCards) mucs;
				String account = tmpMucs.getMjDaPlayer().getAccount();
				Integer intRet = map.get(account);
				if (intRet == null) {
					map.put(account, 1);
				} else {
					map.put(account, intRet.intValue() + 1);
				}
			}
		}

		for (String key : map.keySet()) {
			Integer intRet = map.get(key);
			if (intRet != null && intRet.intValue() >= this.times) {
				listMjPlayer
						.add((MajiangPlayer) mjGame.getPlayerByAccount(key));
			}
		}
		return listMjPlayer.size() == 0 ? null : listMjPlayer;
	}
}
