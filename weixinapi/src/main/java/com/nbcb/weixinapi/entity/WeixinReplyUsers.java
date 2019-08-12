package com.nbcb.weixinapi.entity;

import java.util.List;

public class WeixinReplyUsers extends WeixinReplyCommon {
	private int total;
	private int count;
	private WeixinReplyUsersList data;
	private String next_openid;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public WeixinReplyUsersList getData() {
		return data;
	}

	public void setData(WeixinReplyUsersList data) {
		this.data = data;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

	public static class WeixinReplyUsersList {
		private List<String> openid;

		public List<String> getOpenid() {
			return openid;
		}

		public void setOpenid(List<String> openid) {
			this.openid = openid;
		}

		public String toString() {
			return openid.toString();
		}

	}

	public String toString() {
		return total + " " + count + " " + next_openid + " " + data;
	}
}
