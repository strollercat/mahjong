package com.nbcb.majiang.card;

import java.util.HashSet;
import java.util.Set;

import com.nbcb.core.card.Card;
import com.nbcb.core.card.DefaultCards;

public class MajiangCards extends DefaultCards {

	/**
	 * 根据unit查找所有的牌
	 * 
	 * @param unit
	 *            MajiangCard.tiao,dong,wan,dnxb,zfb,hua
	 * @return
	 */
	public MajiangCard[] getCardsByUnit(int unit) {
		// TODO Auto-generated method stub
		Set<MajiangCard> set = new HashSet();
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (mc.isUnit(unit)) {
				set.add(mc);
			}
		}
		if (set.size() == 0) {
			return null;
		}
		MajiangCard[] mcs = new MajiangCard[set.size()];
		int i = 0;
		for (MajiangCard c : set) {
			mcs[i++] = c;
		}
		return mcs;
	}

	/**
	 * 所有白搭的个数
	 * 
	 * @return
	 */
	public int totalBaida() {
		int total = 0;
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (mc.isBaida()) {
				total++;
			}
		}
		return total;
	}

	/**
	 * 返回第一个不是百搭并且也不是字或花的牌
	 * 
	 * @return
	 */
	public MajiangCard firstNonBaidaNonZiCard() {
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (!mc.isBaida() && mc.getType().length() == 2) {
				return mc;
			}
		}
		return null;
	}

	/**
	 * 返回第一个不是百搭的牌
	 * 
	 * @return
	 */
	public MajiangCard firstNonBaidaCard() {
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (!mc.isBaida()) {
				return mc;
			}
		}
		return null;
	}

	/**
	 * 返回所有的白搭
	 * 
	 * @return
	 */
	public MajiangCards baidaCards() {
		MajiangCards mcs = new MajiangCards();
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (mc.isBaida()) {
				mcs.addTailCard(mc);
			}
		}
		return mcs;
	}

	/**
	 * 返回所有的字
	 * 
	 * @return
	 */
	public MajiangCards ziCards() {
		MajiangCards mcs = new MajiangCards();
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (mc.isUnit(MajiangCard.DNXB) || mc.isUnit(MajiangCard.ZFB)) {
				mcs.addTailCard(mc);
			}
		}
		return mcs;
	}

	/**
	 * 返回所有的非字
	 * 
	 * @return
	 */
	public MajiangCards nonZiCards() {
		MajiangCards mcs = new MajiangCards();
		for (Card c : cards) {
			MajiangCard mc = (MajiangCard) c;
			if (mc.isUnit(MajiangCard.DONG) || mc.isUnit(MajiangCard.TIAO)
					|| mc.isUnit(MajiangCard.WAN)) {
				mcs.addTailCard(mc);
			}
		}
		return mcs;
	}

}
