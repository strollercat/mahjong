package com.nbcb.web.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.nbcb.common.service.TimerService;
import com.nbcb.web.service.WeixinUserService;
import com.nbcb.weixinapi.dao.WeixinUserDao;
import com.nbcb.weixinapi.entity.WeixinReplyUser;

public class MemoryWeixinUserService implements WeixinUserService, TimerService {

	private static final Logger logger = LoggerFactory
			.getLogger(MemoryWeixinUserService.class);

	class ReplyUser {
		private WeixinReplyUser wru;
		private long createTime = 0;
		private long visitTime = 0;

		public WeixinReplyUser getWru() {
			return wru;
		}

		public void setWru(WeixinReplyUser wru) {
			this.wru = wru;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public long getVisitTime() {
			return visitTime;
		}

		public void setVisitTime(long visitTime) {
			this.visitTime = visitTime;
		}

	}

	private Map<String, ReplyUser> mapUser = new ConcurrentHashMap<String, ReplyUser>();

	private WeixinUserDao weixinUserDao;

	private String appid;

	private int totolSize;

	private int timeOut;

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public void setTotolSize(int totolSize) {
		this.totolSize = totolSize;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setWeixinUserDao(WeixinUserDao weixinUserDao) {
		this.weixinUserDao = weixinUserDao;
	}

	@Override
	public WeixinReplyUser getWeixinUserByOpenid(String openid) {
		// TODO Auto-generated method stub
		ReplyUser ru = mapUser.get(openid);
		if (ru != null) {
			ru.setVisitTime(System.currentTimeMillis());
			return ru.getWru();
		}
		WeixinReplyUser wru = new WeixinReplyUser();
		wru.setAppid(appid);
		wru.setOpenid(openid);
		wru = weixinUserDao.selectUserByOpenid(wru);
		if (wru == null) {
			return null;
		}
		ru = new ReplyUser();
		ru.setWru(wru);
		ru.setCreateTime(System.currentTimeMillis());
		ru.setVisitTime(System.currentTimeMillis());
		mapUser.put(wru.getOpenid(), ru);
		return wru;
	}

	@Override
	public void putWeixinUser(WeixinReplyUser weixinReplyUser) {
		// TODO Auto-generated method stub
		ReplyUser ru = new ReplyUser();
		ru.setWru(weixinReplyUser);
		ru.setCreateTime(System.currentTimeMillis());
		ru.setVisitTime(System.currentTimeMillis());
		mapUser.put(weixinReplyUser.getOpenid(), ru);
	}

	@Override
	public void service() {
		// TODO Auto-generated method stub
		if (mapUser.size() < this.totolSize) {
			return;
		}
		Set<String> keySet = mapUser.keySet();
		ReplyUser ru;
		for (String key : keySet) {
			ru = mapUser.get(key);
			if (ru == null) {
				continue;
			}
			if (ru.getVisitTime() != 0
					&& System.currentTimeMillis() - ru.getVisitTime() > this.timeOut) {
				mapUser.remove(key);
			}
		}
	}

	@Override
	public String getOriginIp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isEmpty(ip)) {
			return "";
		}
		try {
			String[] ips = ip.split(",");
			ip = StringUtils.trimWhitespace(ips[0]);
		} catch (Exception e) {
			logger.error("### error", e);
			return "";
		}

		if (ip.equals("127.0.0.1")) {
			return "";
		}
		return ip;

	}
}
