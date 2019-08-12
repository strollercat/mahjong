package com.nbcb.majiang.helper;

import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public interface MajiangGangHelper {
	
	/**
	 * 计算连续杠的次数，从moback开始算
	 * @param mjGame
	 * @param mjPlayer
	 * @param fromIndex
	 * @param anGang
	 * @param mingGang
	 * @param xianGang
	 * @param hua
	 * @return
	 */
	public int sequenceGangSize(MajiangGame mjGame, MajiangPlayer mjPlayer,
			int fromIndex, boolean anGang, boolean mingGang, boolean xianGang,
			boolean hua);
	
	/**
	 * 计算明杠打的人，从moback开始算
	 * @param mjGame
	 * @param mjPlayer
	 * @param fromIndex
	 * @return
	 */
	public MajiangPlayer isMingGangDa(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int fromIndex);

}
