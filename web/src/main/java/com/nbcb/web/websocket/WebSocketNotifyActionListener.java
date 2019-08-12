package com.nbcb.web.websocket;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.ActionListener;
import com.nbcb.core.game.Game;
import com.nbcb.core.io.UserOutputApi;

public class WebSocketNotifyActionListener implements ActionListener {

	private UserOutputApi userOutputApi;

	public void setUserOutputApi(UserOutputApi userOutputApi) {
		this.userOutputApi = userOutputApi;
	}

	@Override
	public void listen(Game game, Action action) {
		// TODO Auto-generated method stub
		userOutputApi.listen(game, action);
	}
}
