package com.nbcb.majiang.rule.judger.hu;

import java.util.List;

import com.nbcb.majiang.card.MajiangCard;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.card.MajiangUnitCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.user.MajiangPlayer;

public interface MajiangHuStrategy {

	
	/**
	 * 找到合法的可以胡的牌型
	 * @param mjGame
	 * @param listMiddleMjUnitCards 吃碰港出的牌
	 * @param mjCards 里面的牌
	 * @param mjCard  胡的牌
	 * @return
	 */
	public List<MajiangHuCards> findLegalHuCards(MajiangGame mjGame,
			List<MajiangUnitCards> listMiddleMjUnitCards, MajiangCards mjCards,
			MajiangCard mjCard);

	/**
	 * 计算每种牌型的番数
	 * @param mjGame
	 * @param mjPlayer 胡的人
	 * @param huType
	 * @param listMjHuCards
	 * @return
	 */
	public List<MajiangHuResult> calHuResultFan(MajiangGame mjGame,
			MajiangPlayer mjPlayer, int huType,
			List<MajiangHuCards> listMjHuCards);

	
	/**
	 * 计算最大的并且是合法的可以胡的番数
	 * @param mjGame
	 * @param listHuResult
	 * @return
	 */
	public MajiangHuResult findLegalHuResult(MajiangGame mjGame,
			List<MajiangHuResult> listHuResult);

}
