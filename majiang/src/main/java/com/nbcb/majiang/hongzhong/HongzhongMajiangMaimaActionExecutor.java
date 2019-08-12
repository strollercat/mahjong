package com.nbcb.majiang.hongzhong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangBlackCards;
import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.executor.MajiangActionExecutor;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResult;
import com.nbcb.majiang.rule.judger.hu.MajiangHuResults;

public class HongzhongMajiangMaimaActionExecutor extends MajiangActionExecutor {

	@Override
	public void execInner(MajiangGame mjGame, MajiangAction mjAction) {
		// TODO Auto-generated method stub
		MajiangHuResults mjHuResults = mjGame.getMajiangHuResults();

		MajiangCards maimaCards = this.maimaCards(mjGame);
		int zhongmaNumber = this.zhongmaNumber(maimaCards);

		for (int i = 0; i < mjHuResults.size(); i++) {
			mjHuResults.getMajiangHuResult(i).setAttribute(
					MajiangHuResult.ZHONGMANUMBER, zhongmaNumber);
		}
		mjAction.setAttribute(MajiangAction.MAIMACARDS,
				this.getMaimaCards(maimaCards));
	}

	private List<Map<String, Object>> getMaimaCards(MajiangCards maimaCards) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < maimaCards.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			
			MajiangCard mc = (MajiangCard) maimaCards.getCard(i);
			map.put("cn", mc.getNumber());
			if (this.isZhongma(mc)) {
				map.put("zhong", true);
			} else {
				map.put("zhong", false);
			}
		}
		return list;
	}

	protected boolean isZhongma(MajiangCard mc) {
		if (mc.isBaida()) {
			return true;
		}

		if (mc.getType().length() == 1) {
			return false;
		}
		int number = mc.getFirstNumber();

		return number == 1 || number == 5 || number == 9;
	}

	private int zhongmaNumber(MajiangCards mcs) {
		int total = 0;
		for (int i = 0; i < mcs.size(); i++) {
			MajiangCard mc = (MajiangCard) mcs.getCard(i);
			if (this.isZhongma(mc)) {
				total += 1;
			}
		}
		return total;
	}

	private MajiangCards maimaCards(MajiangGame mjGame) {
		int maimaNumber = this.maimaNumber(mjGame);
		MajiangCards mcs = new MajiangCards();
		MajiangBlackCards blackCards = mjGame.getMajiangBlackCards();
		for (int i = 1; i <= maimaNumber; i++) {
			mcs.addTailCard(blackCards.getCard(blackCards.size() - i));
		}
		return mcs;
	}

	protected int maimaNumber(MajiangGame mjGame) {
		if (mjGame.getMajiangHuResults().size() >= 2) {
			return 4;
		}
		MajiangHuResult huResult = mjGame.getMajiangHuResults()
				.getMajiangHuResult(0);
		if (huResult.getMjHuCards().baidaCards().size() > 0) {
			return 4;
		} else {
			return 6;
		}
	}

}
