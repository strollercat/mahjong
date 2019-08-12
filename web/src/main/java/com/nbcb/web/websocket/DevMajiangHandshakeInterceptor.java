package com.nbcb.web.websocket;

import javax.servlet.http.HttpServletRequest;

import com.nbcb.web.service.OnlineUserRegistry;
import com.nbcb.weixinapi.entity.WeixinReplyUser;
import com.nbcb.weixinapi.entity.WeixinUser;

public class DevMajiangHandshakeInterceptor extends MajiangHandshakeInterceptor {

	protected OnlineUserRegistry onlineUserRegistry;

	public void setOnlineUserRegistry(OnlineUserRegistry onlineUserRegistry) {
		this.onlineUserRegistry = onlineUserRegistry;
	}

	protected WeixinUser getWeixinUser(HttpServletRequest request) {
		WeixinReplyUser wru = new WeixinReplyUser();
		String code = request.getParameter("code");
		wru.setOpenid(code);
		wru.setNickname(code + "名字");
		wru.setHeadimgurl(
				"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCVquUSK6R0JrKIy9KX6ckuYRVWnkcL3iciaUEVZhrwXicxaUIic15yvleO1rcLtnvlZ2L0X1uoBXN0lw/0");

		WeixinUser wu = new WeixinUser();
		wu.setOpenid(request.getParameter("code"));
		wu.setName(wu.getOpenid() + "名字");
		wu.setHeadUrl(
				"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCVquUSK6R0JrKIy9KX6ckuYRVWnkcL3iciaUEVZhrwXicxaUIic15yvleO1rcLtnvlZ2L0X1uoBXN0lw/0");
		return wu;
	}
}
