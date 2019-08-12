package com.nbcb.weixinapi.entity;

import java.util.Date;
import java.util.List;

/**
 * @author zhengbinhui
 * @since 04.14.2016
 */
public class WeixinReplyUser extends WeixinReplyCommon {
	private String appid;

	private String subscribe;
	private String openid;
	private String nickname;
	private String sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private long subscribe_time;
	private Date subscribe_time1;
	private String unionid;
	private String remark;
	private String groupid;
	private List<String> tagid_list;
	private String subscribe_scene;
	private int qr_scene;
	private String qr_scene_str;

	public String getSubscribe_scene() {
		return subscribe_scene;
	}

	public void setSubscribe_scene(String subscribe_scene) {
		this.subscribe_scene = subscribe_scene;
	}

	public int getQr_scene() {
		return qr_scene;
	}

	public void setQr_scene(int qr_scene) {
		this.qr_scene = qr_scene;
	}

	public String getQr_scene_str() {
		return qr_scene_str;
	}

	public void setQr_scene_str(String qr_scene_str) {
		this.qr_scene_str = qr_scene_str;
	}

	public List<String> getTagid_list() {
		return tagid_list;
	}

	public void setTagid_list(List<String> tagid_list) {
		this.tagid_list = tagid_list;
	}

	public Date getSubscribe_time1() {
		return this.subscribe_time1;
	}

	public void setSubscribe_time1(Date subscribe_time1) {
		this.subscribe_time1 = subscribe_time1;
		this.subscribe_time = subscribe_time1.getTime() / 1000;
	}

	private List<String> privilege;

	public List<String> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}

	

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(long subscribe_time) {
		this.subscribe_time = subscribe_time;
		this.subscribe_time1 = new Date(subscribe_time * 1000);
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	@Override
	public String toString() {
		return subscribe
				+ " "
				+ openid
				+ " "
				+ nickname
				+ " "
				+ sex
				+ " "
				+ language
				+ " "
				+ city
				+ " "
				+ province
				+ " "
				+ country
				+ " "
				+ headimgurl
				+ " "
				+ (subscribe_time1 == null ? null : subscribe_time1
						.toGMTString()) + " " + unionid + " " + remark + " "
				+ groupid + " " + privilege;

	}

}
