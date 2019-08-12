package com.nbcb.weixinapi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;

import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

public class WeixinUserDaoImpl implements WeixinUserDao {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public WeixinReplyUser selectUserByOpenid(WeixinReplyUser weixinReplyUser) {
		// TODO Auto-generated method stub
		return (WeixinReplyUser) this.sqlSessionTemplate.selectOne(
				"weixinUser.selectUserByOpenid", weixinReplyUser);
	}

	@Override
	public int updateUserSubscribeByOpenid(WeixinReplyUser weixinReplyUser) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update(
				"weixinUser.updateUserSubscribeByOpenid", weixinReplyUser);
	}

	@Override
	public int updateUserByOpenid(WeixinReplyUser weixinReplyUser) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("weixinUser.updateUserByOpenid",
				weixinReplyUser);

	}

	@Override
	public int insertUser(WeixinReplyUser weixinReplyUser) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate
				.insert("weixinUser.insertUser", weixinReplyUser);
	}

	

}
