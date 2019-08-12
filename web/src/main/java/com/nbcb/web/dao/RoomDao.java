package com.nbcb.web.dao;

import java.util.Date;
import java.util.List;

import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.dao.entity.TimeRange;

public interface RoomDao {

	public RoomDaoEntity selectRoomById(int id);

	public int insertRoom(RoomDaoEntity room);

	public int updateRoomUsers(RoomDaoEntity room);

	public int updateRoomCostMoney(RoomDaoEntity room);

	public RoomDaoEntity selectLastRoomByRoomId(String roomId);

	public List<RoomDaoEntity> selectUnfinishRoomByCreateAccount(String account);

	public List<RoomDaoEntity> selectUnfinishRoom();

	/**
	 * 查询一段时间已玩过的总房间数
	 * 
	 * @param timeRange
	 * @return
	 */
	public int totalRoomsOfTimes(TimeRange timeRange);

	/**
	 * 查询一段时间已玩过的代理商总房间数
	 * 
	 * @param timeRange
	 * @param ju
	 * @param recommend
	 * @return
	 */
	public int totalRoomsByTimesAndJuAndRecommend(TimeRange timeRange, int ju,
			int recommend);

	/**
	 * 查询一段时间已玩过的代理商总消耗钻石数
	 * 
	 * @param timeRange
	 * @param recommend
	 * @return
	 */
	public int totalMoneyConsumedByTimesAndRecommend(TimeRange timeRange,
			int recommend);

	/**
	 * 分页查询代理商房间明细
	 * 
	 * @param recommend
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<RoomDaoEntity> selectRoomsByRecommendByTimeAndPage(
			int recommend, Date endTime, Page page);

	/**
	 * 分页查询所有房间明细
	 * 
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<RoomDaoEntity> selectRoomsByTimeAndPage(Date endTime, Page page);

	/**
	 * 分页查询指定用户创建的房间明细
	 * 
	 * @param recommend
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<RoomDaoEntity> selectRoomsByCreateAccountByTimeAndPage(
			String createAccount, Date endTime, Page page);
}
