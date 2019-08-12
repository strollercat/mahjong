package com.nbcb.weixinapi.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendPushNews {
	private String accessToken;
	private String touser;
	private String msgtype = "news";
	private Map news = new HashMap();
	private List<WeixinSendNew> articles = new ArrayList();

	public String getAccessToken() {
		return accessToken;
	}

	public Map getNews() {
		return news;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}



	public void addNew(WeixinSendNew sendNew) {
		if(news ==null){
			news = new HashMap();
		}
		
		if(articles == null){
			articles = new ArrayList();
		}
		if(news.get("articles") == null){
			news.put("articles", articles);
		}
		articles.add(sendNew);
	}
	

}
