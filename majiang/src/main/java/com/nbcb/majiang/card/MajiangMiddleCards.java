package com.nbcb.majiang.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbcb.core.server.Formatter;

public class MajiangMiddleCards extends MajiangCards implements Formatter {

	private List<MajiangUnitCards> listMajiangUnitCards = new ArrayList<MajiangUnitCards>();

	private MajiangHuaUnitCards majiangHuaUnitCards = null;

	public MajiangHuaUnitCards getMajiangHuaUnitCards() {
		return majiangHuaUnitCards;
	}

	public MajiangUnitCards getLatestNonHuaMajiangUnitCards() {
		if (listMajiangUnitCards.size() == 0) {
			return null;
		}
		int size = listMajiangUnitCards.size();
		for (int i = size - 1; i >= 0; i--) {
			MajiangUnitCards ucs = listMajiangUnitCards.get(i);
			if (!(ucs instanceof MajiangHuaUnitCards)) {
				return ucs;
			}
		}
		return null;
	}

	public MajiangUnitCards getLatestMajiangUnitCards() {
		if (listMajiangUnitCards.size() == 0) {
			return null;
		}
		int size = listMajiangUnitCards.size();
		return listMajiangUnitCards.get(size - 1);
	}

	public void addMajiangUnitCards(MajiangUnitCards majiangUnitCards) {
		listMajiangUnitCards.add(majiangUnitCards);
		this.addTailCards(majiangUnitCards.toArray());
	}

	public void removeMajiangUnitCards(MajiangUnitCards majiangUnitCards) {
		listMajiangUnitCards.remove(majiangUnitCards);
		for (int i = 0; i < majiangUnitCards.size(); i++) {
			this.removeCard(majiangUnitCards.getCard(i));
		}
	}

	public void addHuaCard(MajiangCard mc) {
		if (majiangHuaUnitCards == null) {
			majiangHuaUnitCards = new MajiangHuaUnitCards();
			listMajiangUnitCards.add(majiangHuaUnitCards);
		}
		majiangHuaUnitCards.addHuaCard(mc);
		super.addTailCard(mc);
	}

	public List<MajiangPengUnitCards> getPengUnitCards() {
		List<MajiangPengUnitCards> list = new ArrayList<MajiangPengUnitCards>();
		for (MajiangUnitCards mucs : listMajiangUnitCards) {
			if (mucs instanceof MajiangPengUnitCards) {
				list.add((MajiangPengUnitCards) mucs);
			}
		}
		return list.size() == 0 ? null : list;
	}

	public List<MajiangUnitCards> getListUnitCards() {
		return this.listMajiangUnitCards;
	}

	@Override
	public void clear() {
		super.clear();
		majiangHuaUnitCards = null;
		listMajiangUnitCards.clear();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (listMajiangUnitCards != null && listMajiangUnitCards.size() != 0) {
			for (MajiangUnitCards mucs : listMajiangUnitCards) {
				sb.append(mucs);
			}
		}
		return "total[" + this.size() + "] " + sb.toString();
	}

	@Override
	public Map format() {
		// TODO Auto-generated method stub
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("m", list);

		if (this.listMajiangUnitCards.size() > 0) {
			for (MajiangUnitCards mucs : this.listMajiangUnitCards) {
				list.add(mucs.format());
			}
		}
		return map;
	}

}
