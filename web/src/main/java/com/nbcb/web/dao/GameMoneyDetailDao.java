package com.nbcb.web.dao;

import java.util.Date;
import java.util.List;

import com.nbcb.web.dao.entity.GameMoneyDetail;
import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.TimeRange;

public interface GameMoneyDetailDao {

	public int insertGameMoneyDetail(GameMoneyDetail gameMoneyDetail);

	/**
	 * 根据账号查询所有的钻石明细
	 * 
	 * @param account
	 * @return
	 */
	public List<GameMoneyDetail> selectGameMoneyDetailsByAccount(String account);

	/**
	 * 根据action查询一段时间的钻石数
	 * 
	 * @param action
	 * @param timeRange
	 * @return
	 */
	public int totalMoneyByActionAndTimeRange(String action, TimeRange timeRange);

	/**
	 * 根据账号,action查询一段时间的钻石数
	 * 
	 * @param action
	 * @param timeRange
	 * @return
	 */
	public int totalMoneyByActionAccountAndTimeRange(String account,
			String action, TimeRange timeRange);

	/**
	 * 根据账号、action分页查询钻石明细
	 * 
	 * @param account
	 * @param action
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<GameMoneyDetail> getMoneyDetailsByActionAccountAndPage(
			String account, String action, Date endTime, Page page);

}
