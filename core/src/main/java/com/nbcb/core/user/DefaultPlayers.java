package com.nbcb.core.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个Players用户离开时座位号不需要调整
 * 
 * @author Administrator
 *
 */
public class DefaultPlayers extends AbstractPlayers {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultPlayers.class);

	public DefaultPlayers(int playersNum) {
		super(playersNum);
		// TODO Auto-generated constructor stub
	}

	private int findMinUnusedOrder() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < this.players.size(); i++) {
			map.put(this.players.get(i).getPlayerOrder() + "", new Object());
		}
		for (int i = 0; i < 4; i++) {
			if (map.get(i + "") == null) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Player addPlayer(Player player) {
		// TODO Auto-generated method stub
		logger.info("### account[" + player.getAccount() + "]add");
		if (this.getActivePlayers() == this.total) {
			return null;
		}
		Player retPlayer = this.getPlayerByAccount(player.getAccount());
		if (retPlayer != null) {
			logger.info("### account[" + retPlayer.getAccount()
					+ "] has already in");
			return null;
		}
		int minOrder = this.findMinUnusedOrder();
		player.setPlayerOrder(minOrder);
		players.add(player);
		return player;
	}

	@Override
	public Player nextPlayer(Player player) {
		int order = player.getPlayerOrder();
		if (order == players.size() - 1) {
			order = 0;
		} else {
			order = order + 1;
		}
		return this.getPlayerByOrder(order);
	}

	@Override
	public Player leavePlayer(String account) {
		logger.info("### account[" + account + "]leave");
		Player player = this.getPlayerByAccount(account);
		if (player != null) {
			this.players.remove(player);
		}
		return player;
	}

	@Override
	public void ajustPlayerIndex() {
		// TODO Auto-generated method stub
		List<Player> players1 = new ArrayList<Player>();
		for (int i = 0; i < this.getActivePlayers(); i++) {
			Player player = this.getPlayerByOrder(i);
			players1.add(player);
		}
		this.players = players1;
	}

}
