package com.nbcb.weixinapi.entity;

public class WeixinReplyManyPush extends WeixinReplyCommon {
	private String msg_id;
	private String msg_data_id;

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getMsg_data_id() {
		return msg_data_id;
	}

	public void setMsg_data_id(String msg_data_id) {
		this.msg_data_id = msg_data_id;
	}

}
