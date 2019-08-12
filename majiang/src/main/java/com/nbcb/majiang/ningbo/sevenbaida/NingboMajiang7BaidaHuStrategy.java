package com.nbcb.majiang.ningbo.sevenbaida;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAnPengUnitCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.game.MajiangGameInfo;
import com.nbcb.majiang.ningbo.NingboMajiangGameInfo;
import com.nbcb.majiang.rule.judger.hu.DefaultMajiangHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangDnxbHuJudger.QuanWeiFeng;
import com.nbcb.majiang.rule.judger.hu.type.MajiangFengyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHuaHuJudger.HuaDetail;
import com.nbcb.majiang.rule.judger.hu.type.MajiangHunyiseHuJudger;
import com.nbcb.majiang.rule.judger.hu.type.MajiangZfbHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class NingboMajiang7BaidaHuStrategy extends DefaultMajiangHuStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(NingboMajiang7BaidaHuStrategy.class);

	private Map<String, Integer> mapHuFan;

	public void setMapHuFan(Map<String, Integer> mapHuFan) {
		this.mapHuFan = mapHuFan;
	}

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		FanDetail fd = new FanDetail();
		if (mjHuCards.getHuFinderType() == HuFinderEnum.ALLZI) {
			fd.add(MajiangHuJudger.LUANFENG, 20);
		} else {
			HuType objHuType = this.getMajiangHuJudger(
					MajiangFengyiseHuJudger.class).judge(mjGame, mjPlayer,
					huType, mjHuCards);
			if (objHuType.isShooted()) {
				fd.add(MajiangHuJudger.QINGFENG, 40);
			} else {
				for (MajiangHuJudger mjHuJudger : listMjHuJudger) {
					objHuType = mjHuJudger.judge(mjGame, mjPlayer, huType,
							mjHuCards);
					if (objHuType.isShooted()) {
						// logger.info("### shooted huJudger "
						// + mjHuJudger.getClass().getName());
						if (mjHuJudger instanceof MajiangHuaHuJudger) {
							HuaDetail huaDetail = (HuaDetail) objHuType
									.getDetail();
							NingboMajiangGameInfo nbGameInfo = (NingboMajiangGameInfo) mjGame
									.getGameInfo();
							int unitFan = huaDetail.getZhengHua()
									* nbGameInfo.getJinHua()
									+ huaDetail.getYeHua()
									* nbGameInfo.getYeHua()
									+ huaDetail.getSequence();
							if (unitFan != 0) {
								fd.add(MajiangHuJudger.HUA, unitFan);
							}
						} else if (mjHuJudger instanceof MajiangZfbHuJudger) {
							int unitFan = (Integer) objHuType.getDetail();
							if (unitFan != 0) {
								fd.add(MajiangHuJudger.ZFB, unitFan);
							}
						} else if (mjHuJudger instanceof MajiangDnxbHuJudger) {
							QuanWeiFeng qwf = (QuanWeiFeng) objHuType
									.getDetail();
							fd.add(MajiangHuJudger.QUANFENG, qwf.getQuanfeng());
							fd.add(MajiangHuJudger.WEIFENG, qwf.getWeifeng());
						} else if (mjHuJudger instanceof MajiangHunyiseHuJudger) {
							if (!fd.getDetail().contains(
									MajiangHuJudger.QINGYISE)) {
								fd.add(MajiangHuJudger.HUNYISE, 2);
							}
						} else {
							fd.add(mjHuJudger.getName(),
									mapHuFan.get(mjHuJudger.getName()));
						}
					}
				}
			}
		}
		if (huType == MajiangAction.FANGQIANGHU) {
			fd.add(MajiangHuJudger.ZIMO, 1);
		}
		List<FanDetail> listFd = new ArrayList<FanDetail>();
		listFd.add(fd);
		return listFd;
	}

	private boolean isHunPengQing(String huDetail) {
		return huDetail.contains(MajiangHuJudger.QINGYISE)
				|| huDetail.contains(MajiangHuJudger.HUNYISE)
				|| huDetail.contains(MajiangHuJudger.DUIDUIHU)
				|| huDetail.contains(MajiangHuJudger.LUANFENG)
				|| huDetail.contains(MajiangHuJudger.QINGFENG);
	}

	@Override
	public MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {

		int huType = listHuResult.get(0).getHuType();
		String huAccount = listHuResult.get(0).getMjPlayer().getAccount();
		NingboMajiangGameInfo gameInfo = (NingboMajiangGameInfo) mjGame
				.getGameInfo();

		MajiangHuResult maxHuResult = null;
		int maxFan = -1;

		for (MajiangHuResult mjHuResult : listHuResult) {

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

	@Override
	protected void evoluate23BaidaSame(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {

		FanDetail fd = listFd.get(0);

		if (fd.includeHuType(MajiangHuJudger.QINGYISE)) { // 是清一色,那就没必要再算了
			return;
		}
		if(fd.includeHuType(MajiangHuJudger.QINGFENG)) { //是清风，没必要在算了
			return ;
		}

		List<MajiangAnPengUnitCards> listPengCards = mjHuCards
				.find3BaidaAnPengUnitCards();
		if (listPengCards != null && listPengCards.size() > 0) {
			fd.add(MajiangHuJudger.WEIFENG, 1 * listPengCards.size());
			if (((MajiangGameInfo) mjGame.getGameInfo()).getQuan() == Math
					.abs(mjGame.getDealer().getPlayerOrder()
							- mjPlayer.getPlayerOrder())) {
				fd.add(MajiangHuJudger.QUANFENG, 1 * listPengCards.size());
			}
		}
	}

}
