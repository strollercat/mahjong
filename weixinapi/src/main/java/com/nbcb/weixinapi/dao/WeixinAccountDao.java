package com.nbcb.weixinapi.dao;

import java.util.List;

import com.nbcb.weixinapi.entity.WeixinAccount;

public interface WeixinAccountDao {

	public List<WeixinAccount> findAllWeixinAccount();

	public WeixinAccount selectWeixinAccountByAppidForUpdate(String appid);

	public int updateTicketByAppid(WeixinAccount weixinAccount);

	public int updateAccessTokenByAppid(WeixinAccount weixinAccount);
}
