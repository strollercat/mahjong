package com.nbcb.weixinapi.entity;


/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinSendMedia {
	public final static  String IMAGETYPE = "image";
	public final static  String VOICETYPE = "voice";
	public final static  String VIDEOTYPE = "video";
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAbsoluteFileName() {
		return absoluteFileName;
	}
	public void setAbsoluteFileName(String absoluteFileName) {
		this.absoluteFileName = absoluteFileName;
	}
	private String accessToken;
	private String type;
	private String absoluteFileName;
	
	
	
	
	public static final String TypeImage="image";
	public static final String TypeVoice="voice";
	public static final String TypeVideo="video";
	public static final String TypeThumb="thumb";
}
