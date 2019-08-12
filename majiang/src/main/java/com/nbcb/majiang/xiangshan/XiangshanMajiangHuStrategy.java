package com.nbcb.majiang.xiangshan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.DefaultMajiangHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger.QuanWeiFeng;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHunyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangPaiHuJudger.PaihuDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangQingyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangZfbHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class XiangshanMajiangHuStrategy extends DefaultMajiangHuStrategy {

	protected Map<String, Integer> mapHuFan;

	public void setMapHuFan(Map<String, Integer> mapHuFan) {
		this.mapHuFan = mapHuFan;
	}

	@Override
	protected void evoluateKazhangPaihuHuanda(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		FanDetail fanDetail = listFd.get(0);
		if (!fanDetail.getDetail().contains(MajiangHuJudger.PAIHU)) {
			return;
		}
		if (fanDetail.getDetail().contains(MajiangHuJudger.DANDIAO)) {
			fanDetail.substract(MajiangHuJudger.DANDIAO, 1);
		} else if (fanDetail.getDetail().contains(MajiangHuJudger.KAZHANG)) {
			fanDetail.substract(MajiangHuJudger.KAZHANG, 1);
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
					HuaDetail huaDetail = (HuaDetail) objHuType.getDetail();
					int unitFan = 0;
					if (huaDetail.getSequence() == 0) {
						unitFan = huaDetail.getZhengHua() * 2
								+ huaDetail.getYeHua();
					} else if (huaDetail.getSequence() == 1) {
						unitFan = (huaDetail.getZhengHua() - 1) * 2
								+ (huaDetail.getYeHua() - 3) * 1 + 10;
					} else if (huaDetail.getSequence() == 2) {
						unitFan = 20;
					}
					if (unitFan > 0) {
						fd.add(mjHuJudger.getName(), unitFan);
					}
				} else if (mjHuJudger instanceof MajiangPaiHuJudger) {
					PaihuDetail paihuDetail = (PaihuDetail) objHuType
							.getDetail();
					if (paihuDetail.getDuiziType() == PaihuDetail.NORMAL) {
						fd.add(mjHuJudger.getName(), 1);
					}
				} else if (mjHuJudger instanceof MajiangZfbHuJudger) {
					int unitFan = (Integer) objHuType.getDetail();
					if (unitFan < 3 && unitFan > 0) {
						fd.add(mjHuJudger.getName(), unitFan);
					} else if (unitFan == 3) {
						fd.add(mjHuJudger.getName(), 25);
					}
				} else if (mjHuJudger instanceof MajiangDnxbHuJudger) {
					QuanWeiFeng qwf = (QuanWeiFeng) objHuType.getDetail();
					fd.add(MajiangHuJudger.QUANFENG, qwf.getQuanfeng());
					fd.add(MajiangHuJudger.WEIFENG, qwf.getWeifeng());
				} else if (mjHuJudger instanceof MajiangHunyiseHuJudger) {
					if (!fd.includeHuType(MajiangQingyiseHuJudger.QINGYISE)) {
						fd.add(mjHuJudger.getName(), mapHuFan.get(mjHuJudger.getName()));
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

	@Override
	protected MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {
		return listHuResult.get(listHuResult.size() - 1);
	}

}
