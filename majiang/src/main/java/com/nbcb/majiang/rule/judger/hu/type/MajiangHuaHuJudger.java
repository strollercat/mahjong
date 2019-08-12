package com.nbcb.majiang.rule.judger.hu.type;

import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.MajiangHuJudger;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangHuaHuJudger implements MajiangHuJudger {

	public static class HuaDetail {
		int sequence;
		int zhengHua;
		int yeHua;

		@Override
		public String toString() {
			return this.zhengHua + " " + this.yeHua + " " + this.sequence;
		}

		public HuaDetail(int sequence, int zhengHua, int yeHua) {
			this.sequence = sequence;
			this.zhengHua = zhengHua;
			this.yeHua = yeHua;
		}

		public int getSequence() {
			return sequence;
		}

		public int getZhengHua() {
			return zhengHua;
		}

		public int getYeHua() {
			return yeHua;
		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MajiangHuJudger.HUA;
	}

	@Override
	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards) {
		// TODO Auto-generated method stub
		return HuaJudgeUtil.judge(mjGame, mjPlayer,
				mjHuCards.findHuaUnitCards());
	}
}
