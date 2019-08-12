package com.nbcb.weixinapi.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.weixinapi.dao.WeixinAccountDao;
import com.nbcb.weixinapi.entity.WeixinAccount;

public class WeixinAccountDaoImpl implements WeixinAccountDao {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<WeixinAccount> findAllWeixinAccount() {
		// TODO Auto-generated method stub
		List<WeixinAccount> list = sqlSessionTemplate.selectList("weixinAccount.findAll");
		return list;
	}

	@Override
	public WeixinAccount selectWeixinAccountByAppidForUpdate(String appid) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("weixinAccount.selectWeixinAccountByAppidForUpdate", appid);
	}

	@Override
	public int updateTicketByAppid(WeixinAccount weixinAccount) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("weixinAccount.updateTicketByAppid", weixinAccount);
	}

	@Override
	public int updateAccessTokenByAppid(WeixinAccount weixinAccount) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("weixinAccount.updateAccessTokenByAppid", weixinAccount);
	}

}
