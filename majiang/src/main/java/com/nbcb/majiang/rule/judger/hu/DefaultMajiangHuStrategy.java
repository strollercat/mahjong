package com.nbcb.majiang.rule.judger.hu;

import java.util.List;

import com.nbcb.core.io.MessageListener;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.helper.MajiangGangHelper;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.user.MajiangPlayer;

public class DefaultMajiangHuStrategy extends AbstractMajiangHuStrategy {

	protected List<MajiangHuJudger> listMjHuJudger;

	protected MessageListener messageListener;

	protected MajiangGangHelper majiangGangHelper;

	public void setMajiangGangHelper(MajiangGangHelper majiangGangHelper) {
		this.majiangGangHelper = majiangGangHelper;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void setListMjHuJudger(List<MajiangHuJudger> listMjHuJudger) {
		this.listMjHuJudger = listMjHuJudger;
	}

	protected <T extends MajiangHuJudger> T getMajiangHuJudger(Class<T> clazz) {
		if (listMjHuJudger == null) {
			return null;
		}
		for (MajiangHuJudger huJudger : listMjHuJudger) {
			if (huJudger.getClass() == clazz) {
				return (T) huJudger;
			}
		}
		return null;
	}

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void evoluateKazhangPaihuHuanda(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void evoluate23BaidaSame(MajiangGame mjGame,
			MajiangPlayer mjPlayer, MajiangHuCards mjHuCards, int huType,
			List<FanDetail> listFd) {
		// TODO Auto-generated method stub

	}

	@Override
	protected FanDetail decideFinalFanDetail(List<FanDetail> listFd) {
		// TODO Auto-generated method stub
		return listFd.get(0);
	}

	@Override
	protected MajiangHuResult findLegalHuResultInner(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {
		return listHuResult.get(0);
	}

	protected boolean isPaoda(List<MajiangHuResult> listHuResult) {
		for (MajiangHuResult huResult : listHuResult) {
			if (huResult.isPaoda()) {
				return true;
			}
		}
		return false;
	}

}
