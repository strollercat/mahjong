package com.nbcb.majiang.card;

import com.nbcb.common.util.RandomUtil;
import com.nbcb.core.card.Card;

public class MajiangBlackCards extends MajiangCards {
	private MajiangAllCards majiangAllCards;

	public MajiangBlackCards(MajiangAllCards majiangAllCards) {
		this.majiangAllCards = majiangAllCards;
		this.wash();

		// 象山麻将互相包，并且明杠杠开，打倒，一炮多响
//		this.testStart(new int[] { 0, 1, 2, 112, 37, 38, 113, 41, 42, 44, 45, 46,
//				49 }, 50, new int[] { 4, 8, 12, 5, 9, 13, 6, 10, 14, 39, 43,
//				47,48 }, this.getArray13(52), this.getArray13(70), 51);

		// 全部字
		// this.testStart(this.getArray13(108), 123,
		// this.getArray13(0),this.getArray13(20),this.getArray13(40),60);

		// 宁波麻将全部抛搭
		// this.testStart(new int[] { 1, 4, 8, 9, 10, 12, 13, 14, 16, 17, 18,
		// 11,
		// 15 }, 119, new int[] { 2, 5, 20, 21, 22, 24, 25, 26, 28, 29,
		// 30, 31, 32 }, new int[] { 3, 6, 33, 34, 35, 36, 37, 38, 40, 41,
		// 42, 44, 45 }, new int[] { 7, 48, 49, 50, 52, 53, 54, 56, 57,
		// 58, 60, 61, 62 }, 0);

		// 宁波麻将包的情况下，拉缸胡
		// this.testStart(
		// new int[] { 124,126,127,4,8,12,16,20,21,22,23,24,25}, 26,
		// new int[] { 3, 125,50,51,52,53,54,55,56,57,58,59,60},
		// new int[] { 0,1,2,30,31,32,33,34,35,36,37,38,39},
		// this.getArray13(70), 100);

		// 宁波麻将包的情况下，拉缸胡
		// this.testStart(new int[] { 124, 126, 127, 4, 8, 12, 16, 20, 21, 22,
		// 23,
		// 24, 25 }, 26, new int[] { 3, 125, 50, 51, 52, 53, 54, 55, 56,
		// 57, 58, 59, 60 }, new int[] { 0, 1, 2, 30, 31, 32, 33, 34, 35,
		// 36, 37, 38, 39 }, new int
		// []{5,9,13,17,128,129,130,101,102,106,107,6,7}, 100);

		// 红中麻将风险杠一炮两响
		// this.testStart(new int[] { 0, 4, 8, 12, 13, 14, 16, 17, 18, 20, 21,
		// 22,
		// 24 }, 25, new int[] { 1, 2, 3, 7, 11, 15, 19, 23, 26, 27, 28,
		// 29, 30 }, new int[] { 5, 9, 32, 33, 34, 36, 37, 38, 40, 41, 42,
		// 44, 45 }, new int[] { 6, 10, 48, 49, 50, 53, 54, 55, 57, 58,
		// 59, 60, 61 }, 43);

		// 同一圈打了同样的牌
		// this.testStart(this.getArray13(30), 0, new int[] { 1, 18, 19, 20, 21,
		// 22, 23, 24, 25, 26, 27, 28, 29 }, new int[] { 2, 3, 4, 8, 9,
		// 10, 11, 12, 13, 14, 15, 16, 17 }, this.getArray13(108), 129);

		// 杭州麻将连续杠，杠开
		// this.testStart(new int[] { 0, 1, 2, 133, 8, 9, 10, 11, 20, 21, 22,
		// 23,
		// 134 }, 135, new int[] { 3, 50, 51, 52, 53, 54, 55, 56, 57, 58,
		// 59, 60, 61 }, this.getArray13(70), this.getArray13(90), 132);

		// 杭州麻将7对子
		// this.testStart(new int[] { 0, 1, 2, 3, 8, 9, 10, 11, 20, 21, 22,
		// 23,
		// 24 }, 25, this.getArray13(50), this.getArray13(70),
		// this.getArray13(90), 124);

		// this.testStart(new int[] { 0, 1, 2, 4, 8, 9, 10, 12, 20, 21, 132,
		// 133,
		// 134 }, 135, this.getArray13(50), this.getArray13(70),
		// this.getArray13(90), 124);

		// 天台麻将四百搭
		// this.testStart(new int
		// []{0,1,2,4,5,6,8,9,10,132,135,134,133},18,this.getArray13(20),this.getArray13(40),this.getArray13(108),19);

		// 放枪胡一炮两响
		// this.testStart(new int[]{7,11,12,13,14,15,16,17,18,19,20,21,59},3,new
		// int[]{0,1,2,4,5,6,8,9,10,72,73,74,56},new
		// int[]{52,51,108,109,110,112,113,114,116,117,118,120,121},new
		// int[]{36,37,40,41,44,45,53,55,54,57,58,60,135},128);

		// 明杠杠开或者抢杠胡一炮两响
		// this.testStart(new int[]{7,11,12,13,14,15,16,17,18,19,20,21,22},3,new
		// int[]{0,1,2,4,5,6,8,9,10,38,42,46,56},new
		// int[]{80,81,82,83,84,85,86,87,88,89,90,91,135},new
		// int[]{36,37,40,41,44,45,53,55,54,57,58,59,60},128);

		// 三碰
		// this.testStart(new int[] { 2, 6, 10, 11, 125, 126, 127, 128, 129,
		// 130,
		// 131, 132, 133 }, 53, new int[] { 0, 1, 3, 4, 5, 8, 9, 38, 42,
		// 46, 48, 49, 52 }, this.getArray13(80), new int[] { 36, 37, 40,
		// 41, 44, 45, 54, 55, 56, 57, 58, 59, 60 }, 124);

		// ningboMajiang 碰是三百搭
		// this.testStart(new int
		// []{4,5,6,40,41,42,44,45,46,108,109,110,49},48,this.getArray13(50),this.getArray13(70),this.getArray13(90),0);

		// tiantaiMajiang 还搭排胡1
		// this.testStart(
		// new int[] { 0, 1, 2, 4, 5, 6, 8, 9, 10, 32, 33, 135, 134 }, 21,
		// this.getArray13(48), this.getArray13(108),
		// this.getArray13(121), 39);

		// tiantaiMajiang 还搭排胡
		// this.testStart(
		// new int[] { 0, 1, 2, 4, 5, 6, 8, 9, 10, 32, 33, 135, 44 }, 21,
		// this.getArray13(48), this.getArray13(108),
		// this.getArray13(121), 39);

		// 还搭排胡2
		// this.testStart(new int
		// []{0,1,2,4,5,6,8,9,10,32,33,40,44},21,this.getArray13(48),this.getArray13(70),this.getArray13(90),39);

		// 还搭排胡1
		// this.testStart(new int
		// []{0,1,2,4,5,6,8,9,10,32,33,36,40},21,this.getArray13(44),this.getArray13(70),this.getArray13(90),37);

		// 还搭排胡
		// this.testStart(new int
		// []{0,1,2,4,5,6,8,9,10,32,33,40,44},21,this.getArray13(48),this.getArray13(70),this.getArray13(90),41);

		// 抛搭
		// this.testStart(new int
		// []{0,1,2,4,5,6,8,9,10,32,36,40,20},21,this.getArray13(50),this.getArray13(70),this.getArray13(90),22);

		// 碰三下
		// this.testStart(new int
		// []{0,1,4,5,8,9,12,13,16,17,20,21,22},23,this.getArray13(30),this.getArray13(50),new
		// int []{2,3,6,7,10,11,14,15,18,19,70,71,72},100);

		// 碰杠或碰杠
		// this.testStart(new int[] { 6, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
		// 40, 41 }, 42, new int[] { 0, 1, 2, 3, 4, 5,7, 8, 9, 133, 134,
		// 132, 135 }, this.getArray13(60), this.getArray13(80), 10);

		// 碰是三百搭
		// this.testStart(new int[] { 0, 4, 8, 1, 5, 9, 2, 6, 10, 133, 132, 134,
		// 135 }, 12, new int[] { 7, 11, 40, 41, 42, 44, 45, 46, 48, 49,
		// 50, 52, 53 }, this.getArray13(55), this.getArray13(109), 31);

		// this.testStart7();// 大大胡
		// this.testStart(new int[] { 133, 124, 120, 112, 111, 97, 86, 75, 70,
		// 59,
		// 38, 35, 19 }, 0, this.getArray13(1), this.getArray13(20),
		// this.getArray13(98), 143);

		// 7对子
		// this.testStart(new int[] { 0, 1, 4, 5, 8, 9, 12, 13, 20, 21, 36, 40,
		// 142 }, 141, this.getArray13(50), this.getArray13(70),
		// this.getArray13(90), 143);

		// 还搭
		// this.testStart(this.getArray13(50), 49, new int[] { 1, 4, 8, 12, 16,
		// 20, 24, 28, 32, 36, 40, 44, 48 }, this.getArray13(70),
		// this.getArray13(90), 0);

		// 花是白搭
		// this.testStart(new int[] { 142, 141,140, 139, 138, 137, 136, 28, 32,
		// 36, 40, 44,
		// 48 }, 49, this.getArray13(50), this.getArray13(70),
		// this.getArray13(90), 143);
	}

	private int[] getArray13(int start) {
		int[] ret = new int[13];
		for (int i = 0; i < 13; i++) {
			ret[i] = start++;
		}
		return ret;
	}

	private boolean isAppear(int i) {
		for (Card c : this.cards) {
			if (c.getNumber() == i) {
				return true;
			}
		}
		return false;
	}

	public void wash() {

		int start = 0;
		int end = majiangAllCards.size();
		int[] randoms = RandomUtil.getRandom(start, end - 1);
		for (int i = 0; i < randoms.length; i++) {
			this.addTailCard(majiangAllCards.getCard(randoms[i]));
		}

	}

	public String toString() {
		return "blackCards: " + super.toString();
	}

	private void testStart(int[] p1, int head, int[] p2, int[] p3, int[] p4,
			int tail) {

		if (p1.length != 13 || p2.length != 13 || p3.length != 13
				|| p4.length != 13) {
			throw new RuntimeException("illegal parameter");
		}
		for (int i = 0; i < 13; i++) {
			this.addTailCard(majiangAllCards.findCardByNumber(p1[i]));
		}
		for (int i = 0; i < 13; i++) {
			this.addTailCard(majiangAllCards.findCardByNumber(p2[i]));
		}
		for (int i = 0; i < 13; i++) {
			this.addTailCard(majiangAllCards.findCardByNumber(p3[i]));
		}
		for (int i = 0; i < 13; i++) {
			this.addTailCard(majiangAllCards.findCardByNumber(p4[i]));
		}

		this.addTailCard(majiangAllCards.findCardByNumber(head));
		for (int i = 0; i < majiangAllCards.size(); i++) {
			if (this.isAppear(majiangAllCards.getCard(i).getNumber())) {
				continue;
			}
			this.addTailCard(majiangAllCards.getCard(i));
		}
		this.removeCard(majiangAllCards.findCardByNumber(tail));
		this.addTailCard(majiangAllCards.findCardByNumber(tail));
	}

}
