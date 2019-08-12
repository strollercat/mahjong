package com.nbcb.majiang.rule.judger.hu.type;

public class HuType {

	private boolean shooted;

	private Object detail;

	private String huTypeName;

	public String getHuTypeName() {
		return huTypeName;
	}

	public void setHuTypeName(String huTypeName) {
		this.huTypeName = huTypeName;
	}

	public HuType(boolean shooted, Object detail) {
		this.shooted = shooted;
		this.detail = detail;
	}

	public boolean isShooted() {
		return shooted;
	}

	public Object getDetail() {
		return detail;
	}

	public String toString() {
		return this.huTypeName + " " + this.shooted + " "
				+ (this.detail == null ? null : this.detail);
	}

}
