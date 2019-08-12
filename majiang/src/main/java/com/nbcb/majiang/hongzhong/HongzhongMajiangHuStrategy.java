package com.nbcb.majiang.hongzhong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangHuCards.HuFinderEnum;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.DefaultMajiangHuStrategy;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.type.FanDetail;
import com.nbcb.majiang.user.MajiangPlayer;

public class HongzhongMajiangHuStrategy extends DefaultMajiangHuStrategy {

	@Override
	protected List<FanDetail> calculateFanDetail(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType, MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		List<FanDetail> list = new ArrayList<FanDetail>();

		FanDetail fd = new FanDetail();
		if (mjHuCards.getHuFinderType() == HuFinderEnum.SIBAIDA) {
			if (huType != MajiangAction.FANGQIANGHU) {
				fd.add(MajiangHuJudger.SIBAIDA, 4);
			}
		}
		list.add(fd);
		return list;
	}

	@Override
	public MajiangHuResult findLegalHuResult(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult) {
		// TODO Auto-generated method stub
		if (listHuResult == null || listHuResult.size() == 0) {
			return null;
		}

		String huAccount = listHuResult.get(0).getMjPlayer().getAccount();
		HongzhongMajiangGame hongzhongGame = (HongzhongMajiangGame) mjGame;
		if(hongzhongGame.daHongzhong(huAccount)){  //打过红中不能胡
			return null;
		}
		

		Collections.sort(listHuResult);
		MajiangHuResult finalResult = listHuResult.get(0);
		return finalResult;
	}

}
