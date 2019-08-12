package com.nbcb.weixinapi.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeixinSendManyPushByUsers {
	public static final String MPNEWSMSGTYPE = "mpnews";
	public static final String TEXTMSGTYPE = "text";
	public static final String VOICEMSGTYPE = "voice";
	public static final String IMAGESMSGTYPE = "image";
	
	private String accessToken;

	private List<String> touser = new ArrayList();

	private Map<String, Object> mpnews = new HashMap();

	private Map<String, Object> text = new HashMap();

	private Map<String, Object> voice = new HashMap();

	private Map<String, Object> image = new HashMap();

	private String msgtype;
	
	
	
	
	public void setTouser(List<String> touser) {
		this.touser = touser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}



	public void setText(String content) {
		text.put("content", content);
	}

	public void setMediaId(String mediaId) {
		if (MPNEWSMSGTYPE.equals(msgtype)) {
			mpnews.put("media_id", mediaId);
		} else if (VOICEMSGTYPE.equals(msgtype)) {
			voice.put("media_id", mediaId);
		} else if (IMAGESMSGTYPE.equals(msgtype)) {
			image.put("media_id", mediaId);
		} else {
			throw new RuntimeException("please set msgtype first");
		}
	}

	

	public List<String> getTouser() {
		return touser;
	}

	public Map<String, Object> getMpnews() {
		return mpnews;
	}

	public Map<String, Object> getText() {
		return text;
	}

	public Map<String, Object> getVoice() {
		return voice;
	}

	public Map<String, Object> getImage() {
		return image;
	}

	public String getMsgtype() {
		return msgtype;
	}
	

}
