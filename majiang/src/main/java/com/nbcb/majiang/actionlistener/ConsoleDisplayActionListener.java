package com.nbcb.majiang.actionlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbcb.core.action.Action;
import com.nbcb.core.action.ActionListener;
import com.nbcb.core.game.Game;

public class ConsoleDisplayActionListener implements ActionListener {
	private static final Logger logger = LoggerFactory
			.getLogger(ConsoleDisplayActionListener.class);
	@Override
	public void listen(Game game, Action action) {
		// TODO Auto-generated method stub
		logger.info(game.toString());
	}

}
