package com.nbcb.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevDefaultResponseFilter extends DefaultResponseFilter {

	@Override
	protected void putUrlAndNameToUser(List<Map> users) {
		for (Map map : users) {
			map.put("url",
					"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCVquUSK6R0JrKIy9KX6ckuYRVWnkcL3iciaUEVZhrwXicxaUIic15yvleO1rcLtnvlZ2L0X1uoBXN0lw/0");
		}
	}

	@Override
	protected void putUrlAndNameToMap(Map map) {
		String url = "http://wx.qlogo.cn/mmopen/ajNVdqHZLLCVquUSK6R0JrKIy9KX6ckuYRVWnkcL3iciaUEVZhrwXicxaUIic15yvleO1rcLtnvlZ2L0X1uoBXN0lw/0";
		map.put("url", url);
	}
	
	@Override
	protected void putFightInfoToUser(List<Map> users) {
		for (Map map : users) {
			this.putFightInfoToMap(map);
		}
	}

	@Override
	protected void putFightInfoToMap(Map map) {
		
		Map fightInfo = new HashMap();
		fightInfo.put("win", 7);
		fightInfo.put("lose", 8);
		fightInfo.put("ping", 10);
		fightInfo.put("score", 1000);
		fightInfo.put("money", 999);
		map.put("fightInfo", fightInfo);
		map.remove("openid");
	}
}
