package com.nbcb.weixinapi.entity;
import java.util.ArrayList;
import java.util.List;



/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendReplyNews {
	
	private String fromUserName;

	private String toUserName;

	

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	

	public List<WeixinSendNew> getArticles() {
		return articles;
	}

	private List<WeixinSendNew> articles = new ArrayList();

	public void addNew(WeixinSendNew weixinNew) {
		articles.add(weixinNew);
	}
	



}
