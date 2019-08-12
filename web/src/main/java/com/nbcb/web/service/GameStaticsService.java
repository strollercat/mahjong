package com.nbcb.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;
import com.nbcb.web.dao.entity.TimeRange;

public interface GameStaticsService {

	/**
	 * 获取一定时间段该代理人创建的房间数和消耗的钻石数
	 * 
	 * @param recommend
	 *            推荐人ID
	 * @param timeRange
	 *            时间范围
	 * @return
	 */
	public Map getTotalRoomsAndMoneyConsumedByRecommmendAndTimeRange(int recommend,
			TimeRange timeRange);

	/**
	 * 获取所有的开房数
	 * 
	 * @param timeRange
	 * @return
	 */
	public int getTotalRoomsByTimeRange(TimeRange timeRange);

	/**
	 * 获取所有的钻石消耗数
	 * 
	 * @param timeRange
	 * @return
	 */
	public int getTotalMoneyConsumedByTimeRange(TimeRange timeRange);

	/**
	 * 获取所有的充值总数
	 * 
	 * @param timeRange
	 * @return
	 */
	public int getTotalPayByTimeRange(TimeRange timeRange);
	
	
	
	
	/**
	 * 分页查询代理商推广的房间明细
	 * @param recommend
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<RoomDaoEntity> selectRoomsByRecommendByTimeAndPage(int recommend,Date endTime,Page page);
	
	
	/**
	 * 分页查询所有房间明细
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<RoomDaoEntity> selectRoomsByTimeAndPage(Date endTime,Page page);
}
