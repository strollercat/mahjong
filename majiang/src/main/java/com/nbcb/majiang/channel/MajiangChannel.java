package com.nbcb.majiang.channel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.nbcb.core.action.Actions;
import com.nbcb.core.card.Cards;
import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.room.RoomInfo;
import com.nbcb.core.rule.Rule;
import com.nbcb.core.server.AbstractChannel;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangActions;
import com.nbcb.majiang.rule.MajiangRule;
import com.nbcb.majiang.user.MajiangPlayer;

public class MajiangChannel extends AbstractChannel implements
		ApplicationContextAware {

	protected ApplicationContext applicationContext;

	private MajiangRule majiangRule;

	public void setMajiangRule(MajiangRule majiangRule) {
		this.majiangRule = majiangRule;
	}

	

	@Override
	public Player createPlayer(String account) {
		// TODO Auto-generated method stub
		return new MajiangPlayer(account);
	}

	@Override
	public Actions createActions() {
		// TODO Auto-generated method stub
		return new MajiangActions();
	}

	@Override
	public Game createGame(RoomInfo roomInfo, Game lastGame,Room room) {
		// TODO Auto-generated method stub

		return null;
	}

	

	@Override
	public Rule getRule(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		return this.majiangRule;
	}

	@Override
	public Cards createAllCards(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		return (Cards) this.applicationContext.getBean("majiangAllCardsOfFull");
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = arg0;
	}

}
