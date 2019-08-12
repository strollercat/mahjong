package com.nbcb.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nbcb.web.dao.entity.Page;
import com.nbcb.web.dao.entity.RoomDaoEntity;

public interface RoomRecordService {

	public List</* 每一个房间的所有动作 */List</* 每一局游戏的各个动作 */Map>> queryRecordDataByRoomid(
			String account, int roomId);

	/**
	 * 查询已经打过局最近一个房间
	 * 
	 * @param account
	 * @return
	 */
	public RoomDaoEntity checkLastRoomResult(String account);

	public List<RoomDaoEntity> checkRoomResultsByTimeAndPageAndAccount(
			String account, Date endTime, Page page);
}
