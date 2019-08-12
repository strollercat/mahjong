package com.nbcb.majiang.card;

import java.util.Arrays;
import java.util.Map;

import com.nbcb.core.card.Card;
import com.nbcb.core.server.Formatter;

public abstract class MajiangUnitCards extends MajiangCards implements
		Comparable<MajiangUnitCards>, Formatter {

	abstract public String getName();

	public int compareTo(MajiangUnitCards o) {
		// TODO Auto-generated method stub
		if(this.getName().equals(o.getName())){
			int cs1=0,cs2=0;
			for(int i = 0;i<o.size();i++){
				cs1 += this.getCard(i).getNumber();
				cs2 += o.getCard(i).getNumber();
			}
			if(cs1 > cs2){
				return 1;
			}else if(cs1 < cs2){
				return -1;
			}else{
				return 0;
			}
		}else{
			return this.getName().compareTo(o.getName());
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Card c : cards) {
			sb.append(c);
		}
		return "(" + this.getName() + sb.toString() + ")";
	}

	public int[] orderedCardNumber(int[] numbers, int cn) {
		Arrays.sort(numbers);
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] == cn) {
				numbers[i] = numbers[0];
				numbers[0] = cn;
				return numbers;
			}
		}
		return numbers;
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		return null;
	}
}
