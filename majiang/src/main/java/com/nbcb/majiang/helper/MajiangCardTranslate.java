package com.nbcb.majiang.helper;

import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangCards;

public class MajiangCardTranslate {
	public static String translate(int[] cns) {

		MajiangAllCards macs = new MajiangAllCards();
		macs.start();

		MajiangCards mcs = new MajiangCards();
		for (int i = 0; i < cns.length; i++) {
			mcs.addTailCard(macs.findCardByNumber(cns[i]));
		}
		mcs.sort();

		System.out.println(mcs);
		return mcs.toString();

	}
	public static int translate(String card) {
		MajiangAllCards macs = new MajiangAllCards();
		macs.start();

		for (int i = 0; i < macs.size(); i++) {
			if(card.equals(macs.getCard(i).getType())) {
				return macs.getCard(i).getNumber();
			}
		}
		return -1;
	}

	public static void main(String args[]) {
//		int cns [] = new int []{125,10,13,19,52,59,91,95,96,97,100,102,104};
//		translate(cns);
//		cns = new int []{124,0,3,22,26,53,57,64,65,67};
//		translate(cns);
//		
//		cns = new int []{94,88,11,48};
//		translate(cns);
		
		
		System.out.println(translate("6洞"));
		System.out.println(translate("9万"));
		System.out.println(translate("4万"));
		System.out.println(translate("5万"));
		System.out.println(translate("6条"));
		System.out.println(translate("8万"));
		
	}
}
