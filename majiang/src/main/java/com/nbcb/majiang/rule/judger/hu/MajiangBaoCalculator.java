package com.nbcb.majiang.rule.judger.hu;

import java.util.List;

import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public interface MajiangBaoCalculator {
	public List<MajiangPlayer> calculatorMajiangBaos(MajiangGame mjGame,MajiangHuResult mjHuResult,MajiangPlayer mjPlayer);
}
