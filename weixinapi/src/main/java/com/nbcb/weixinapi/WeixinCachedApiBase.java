package com.nbcb.weixinapi;

import java.util.List;

import com.nbcb.weixinapi.entity.WeixinAccount;
import com.nbcb.weixinapi.exception.WeixinException;


/**
 * 
 * 
 * @author zhengbinhui
 * @since 04.14.2016
 */
public interface WeixinCachedApiBase {
	public String getCachedAccessToken(String appid)throws WeixinException;
	public String getCachedTicket(String appid) throws WeixinException;
	public WeixinAccount getWeixinAccountByAppid(String appid);
	public List<WeixinAccount> getWeixinAccounts();
	public WeixinAccount getWeixinAccountByName(String name);
	public WeixinAccount getWeixinAccountByAccount(String account);
}
