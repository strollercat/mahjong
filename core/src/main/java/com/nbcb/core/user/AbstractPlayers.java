package com.nbcb.core.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author zbh
 * 这个player的算法有瑕疵,数组号和players的order一样
 *
 */
public abstract class AbstractPlayers implements Players {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractPlayers.class);

	protected List<Player> players = new ArrayList<Player>();

	protected int total;

	public AbstractPlayers(int total) {
		this.total = total;
	}

	@Override
	public int getActivePlayers() {
		// TODO Auto-generated method stub
		return players.size();
	}

	@Override
	public Player addPlayer(Player player) {
		// TODO Auto-generated method stub
		if (this.getActivePlayers() == this.total) {
			return null;
		}
		Player retPlayer = this.getPlayerByAccount(player.getAccount());
		if (retPlayer != null) {
			logger.info("### account[" + retPlayer.getAccount()
					+ "] has already in");
			return null;
		}
		players.add(player);
		this.ajustPlayerOrder();
		return player;
	}

	

	@Override
	public Player nextPlayer(Player player) {
		int order = player.getPlayerOrder();
		if (order == players.size() - 1) {
			return players.get(0);
		}
		return players.get(order + 1);
	}

	@Override
	public String toString() {
		String str = "";
		for (Player player : players) {
			str += player;
			str += "\r\n";
		}
		return str;
	}

	private void ajustPlayerOrder() {
		for (int i = 0; i < this.players.size(); i++) {
			this.players.get(i).setPlayerOrder(i);
		}
	}

	@Override
	public Player leavePlayer(String account) {
		Player player = this.getPlayerByAccount(account);
		if (player != null) {
			this.players.remove(player);
		}
		this.ajustPlayerOrder();
		return player;
	}

	@Override
	public Player getPlayerByAccount(String account) {
		for (Player player : this.players) {
			if (player.getAccount().equals(account)) {
				return player;
			}
		}
		return null;
	}
	
	@Override
	public Player getPlayerByIndex(int i) {
		return players.get(i);
	}
	
	@Override
	public Player getPlayerByOrder(int order){
		if (this.players == null || this.players.size() == 0) {
			return null;
		}
		
		for(Player player:this.players){
			if(player.getPlayerOrder() ==  order){
				return player;
			}
		}
		return null;
	}

	
	@Override
	public String[] playerAccounts() {
		if (this.players == null || this.players.size() == 0) {
			return null;
		}
		String accounts[] = new String[this.players.size()];
		int index = 0;
		for (Player player : this.players) {
			accounts[index++] = player.getAccount();
		}
		return accounts;
	}

}
