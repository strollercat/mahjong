package com.nbcb.core.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class MemoryWinLosePlayerStrategy implements WinLosePlayerStrategy {

	private List<String> needWinPlayer = new ArrayList<String>();

	private List<String> needLosePlayer = new ArrayList<String>();

	public void setNeedWinPlayers(String needWinPlayers) {

		if (StringUtils.isEmpty(needWinPlayers)) {
			return;
		}
		try {
			String[] strArr = needWinPlayers.split(",,");
			for (String str : strArr) {
				this.needWinPlayer.add(str);
			}
		} catch (Exception e) {
			return;
		}

	}

	public void setNeedLosePlayers(String needLosePlayers) {
		if (StringUtils.isEmpty(needLosePlayers)) {
			return;
		}
		try {
			String[] strArr = needLosePlayers.split(",,");
			for (String str : strArr) {
				this.needLosePlayer.add(str);
			}
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public boolean isNeedWinPlayer(String account) {
		// TODO Auto-generated method stub
		if (this.needWinPlayer == null || this.needWinPlayer.size() == 0) {
			return false;
		}
		if (StringUtils.isEmpty(account)) {
			return false;
		}
		for (String str : this.needWinPlayer) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			if (str.equals(account)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isNeedLosePlayer(String account) {
		// TODO Auto-generated method stub
		if (this.needLosePlayer == null || this.needLosePlayer.size() == 0) {
			return false;
		}
		if (StringUtils.isEmpty(account)) {
			return false;
		}
		for (String str : this.needLosePlayer) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			if (str.equals(account)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		MemoryWinLosePlayerStrategy service = new MemoryWinLosePlayerStrategy();
//		service.setNeedWinPlayers("player0,,player1");
//		service.setNeedLosePlayers("player2,,player3");

		System.out.println(service.isNeedWinPlayer("player0"));
		System.out.println(service.isNeedWinPlayer("player1"));
		System.out.println(service.isNeedWinPlayer("player2"));
		System.out.println(service.isNeedWinPlayer("player3"));

		System.out.println(service.isNeedLosePlayer("player0"));
		System.out.println(service.isNeedLosePlayer("player1"));
		System.out.println(service.isNeedLosePlayer("player2"));
		System.out.println(service.isNeedLosePlayer("player3"));
		
		System.out.println(service.isNeedLosePlayer(""));
		System.out.println(service.isNeedLosePlayer(null));


	}

	@Override
	public int needWinRatio(String account) {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	public int needLoseRatio(String account) {
		// TODO Auto-generated method stub
		return 40;
	}

}
