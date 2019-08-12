package com.nbcb.core.helper;

public interface WinLosePlayerStrategy {

	public boolean isNeedWinPlayer(String account);

	public boolean isNeedLosePlayer(String account);

	public int needWinRatio(String account);

	public int needLoseRatio(String account);

}
