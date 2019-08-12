package com.nbcb.majiang.rule.judger.hu;

import java.util.ArrayList;
import java.util.List;

public class MajiangHuResults {

	private List<MajiangHuResult> listHuResult = new ArrayList<MajiangHuResult>();

	public void addMajiangHuResult(MajiangHuResult mjHuResult) {
		listHuResult.add(mjHuResult);
	}

	public int size() {
		return listHuResult.size();
	}

	public MajiangHuResult getMajiangHuResult(int i) {
		return listHuResult.get(i);
	}

	@Override
	public String toString() {
		return listHuResult.toString();
	}
}
