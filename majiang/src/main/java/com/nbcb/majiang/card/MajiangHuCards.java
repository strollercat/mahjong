package com.nbcb.majiang.card;

import java.util.ArrayList;
import java.util.List;

public class MajiangHuCards extends MajiangCards {

	public static enum HuFinderEnum {
		THREETWO, ALLZI, SEVENDUIZI, DADAHU, SIBAIDA
	}

	private HuFinderEnum huFinderType = HuFinderEnum.THREETWO;

	private List<MajiangUnitCards> listMjUnitCards = new ArrayList<MajiangUnitCards>();

	private MajiangCard mjHuCard;

	public HuFinderEnum getHuFinderType() {
		return huFinderType;
	}

	public void setHuFinderType(HuFinderEnum huFinderType) {
		this.huFinderType = huFinderType;
	}

	public List<MajiangUnitCards> getListMjUnitCards() {
		return listMjUnitCards;
	}

	public void setListMjUnitCards(List<MajiangUnitCards> listMjUnitCards) {
		this.listMjUnitCards.addAll(listMjUnitCards);
		for (MajiangUnitCards mucs : listMjUnitCards) {
			this.addTailCards(mucs.toArray());
		}
	}

	public MajiangCard getMjHuCard() {
		return mjHuCard;
	}

	public void setMjHuCard(MajiangCard mjHuCard) {
		this.mjHuCard = mjHuCard;
	}

	public void addMajiangUnitCards(MajiangUnitCards mjUnitCards) {
		this.listMjUnitCards.add(mjUnitCards);
		this.addTailCards(mjUnitCards.toArray());
	}

	public List<MajiangAnPengUnitCards> find3BaidaAnPengUnitCards() {
		if (listMjUnitCards.size() == 0) {
			return null;
		}
		List<MajiangAnPengUnitCards> listRet = new ArrayList<MajiangAnPengUnitCards>();
		for (MajiangUnitCards mucs : this.listMjUnitCards) {
			if (mucs instanceof MajiangAnPengUnitCards
					&& mucs.totalBaida() == 3) {
				listRet.add((MajiangAnPengUnitCards) mucs);
			}
		}
		return listRet;
	}

	public MajiangUnitCards findHuCardUnitCards() {
		if (listMjUnitCards.size() == 0) {
			return null;
		}
		for (MajiangUnitCards mucs : this.listMjUnitCards) {
			if (mucs.containsCard(mjHuCard)) {
				return mucs;
			}
		}
		return null;
	}

	public MajiangHuaUnitCards findHuaUnitCards() {
		if (listMjUnitCards.size() == 0) {
			return null;
		}
		for (MajiangUnitCards mucs : this.listMjUnitCards) {
			if (mucs instanceof MajiangHuaUnitCards) {
				return (MajiangHuaUnitCards) mucs;
			}
		}
		return null;
	}

	public MajiangDuiziUnitCards findDuiziUnitCards() {
		if (listMjUnitCards.size() == 0) {
			return null;
		}
		for (MajiangUnitCards mucs : this.listMjUnitCards) {
			if (mucs instanceof MajiangDuiziUnitCards) {
				return (MajiangDuiziUnitCards) mucs;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.huFinderType + " "
				+ (listMjUnitCards == null ? null : listMjUnitCards.toString());
	}

}
