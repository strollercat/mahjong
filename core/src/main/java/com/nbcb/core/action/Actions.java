package com.nbcb.core.action;


public interface Actions {

	public int size();

	public Action getTailAction();

	public Action getHeadAction();

	public Action getAction(int i);
	
	public Action[] getActionsByType(int type);

	public Action[] getActionsByPlayer(String account);
	
	public Action [] getActionsByUserAction(boolean userAction);
	
	public Action[] getActionsByTypeAndPlayer(String account,int type);

	public void removeActionByType(int type);
	
	public void removeActionByTypeAndPlayer(String account ,int type);

	public void clear();

	public void addActions(Actions actions);

	public void addAction(Action action);
	
	public Action getLastActionByPlayerAndType(String account,int type);
	
	public Action getLastActionByType(int type);
	
	/**
	 * index 从1开始
	 * @param index index=1是最后的一个action
 	 * @return
	 */
	public Action getLastActionByIndex(int index); 
	
	public String [] getDifferentAccounts();
	


}
