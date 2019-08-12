package com.nbcb.majiang.helper;

import com.nbcb.majiang.user.MajiangPlayer;

public interface TanShuCalculator {
	
	/**
	 * 计算滩数
	 * @param actionPlayer
	 * @param daPlayer
	 * @return
	 */
	public int calculatorTanShu(MajiangPlayer actionPlayer,MajiangPlayer daPlayer);
	
}
