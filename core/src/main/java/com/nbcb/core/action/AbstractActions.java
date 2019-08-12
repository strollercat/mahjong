package com.nbcb.core.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.nbcb.core.card.Cards;

public abstract class AbstractActions implements Actions {

	protected List<Action> actions = new ArrayList<Action>();

	@Override
	public int size() {
		return actions.size();
	}

	@Override
	public Action getTailAction() {
		if (actions.size() == 0) {
			return null;
		}
		return actions.get(actions.size() - 1);
	}

	@Override
	public Action getHeadAction() {
		if (actions.size() == 0) {
			return null;
		}
		return actions.get(0);
	}

	@Override
	public Action getAction(int i) {
		if (actions.size() == 0) {
			return null;
		}
		return actions.get(i);
	}

	@Override
	public Action[] getActionsByType(int type) {
		if (actions.size() == 0) {
			return null;
		}
		List<Action> list = new ArrayList<Action>();
		for (Action a : actions) {
			if (a.getType() == type) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Action[] retActions = new Action[list.size()];
		return list.toArray(retActions);
	}

	@Override
	public void removeActionByType(int type) {
		if (actions.size() == 0) {
			return;
		}
		List<Action> list = new ArrayList<Action>();
		for (Action a : actions) {
			if (a.getType() == type) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return;
		}
		actions.removeAll(list);
	}

	@Override
	public void clear() {
		actions.clear();
	}

	@Override
	public void addActions(Actions actions) {
		if (actions == null || actions.size() == 0) {
			return;
		}
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.getAction(i);
			this.actions.add(a);
		}
	}

	@Override
	public void addAction(Action action) {
		actions.add(action);
	}

	@Override
	public String toString() {
		if (actions.size() == 0) {
			return null;
		}
		return actions.toString();
	}

	@Override
	public Action[] getActionsByPlayer(String account) {
		if (actions.size() == 0) {
			return null;
		}
		List<Action> list = new ArrayList<Action>();
		for (Action a : actions) {
			if (a.getPlayer() == null) {
				continue;
			}
			if (a.getPlayer().getAccount().equals(account)) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Action[] retActions = new Action[list.size()];
		return list.toArray(retActions);
	}

	@Override
	public Action[] getActionsByTypeAndPlayer(String account, int type) {
		if (actions.size() == 0) {
			return null;
		}
		List<Action> list = new ArrayList<Action>();
		for (Action a : actions) {
			if (a.getPlayer().getAccount().equals(account)
					&& a.getType() == type) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Action[] retActions = new Action[list.size()];
		return list.toArray(retActions);
	}

	@Override
	public Action[] getActionsByUserAction(boolean userAction) {
		if (actions.size() == 0) {
			return null;
		}
		List<Action> list = new ArrayList<Action>();
		for (Action a : actions) {
			if (a.isUserAction() == userAction) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Action[] retActions = new Action[list.size()];
		return list.toArray(retActions);
	}

	@Override
	public void removeActionByTypeAndPlayer(String account, int type) {
		if (actions.size() == 0) {
			return;
		}
		List<Action> list = new ArrayList<Action>();
		for (Action a : actions) {
			if (a.getPlayer() == null) {
				continue;
			}
			if (a.getPlayer().getAccount().equals(account)
					&& a.getType() == type) {
				list.add(a);
			}
		}
		if (list.size() == 0) {
			return;
		}
		this.actions.removeAll(list);
	}

	@Override
	public Action getLastActionByType(int type) {
		if (this.actions.size() == 0) {
			return null;
		}
		for (int i = this.actions.size() - 1; i >= 0; i--) {
			Action a = this.actions.get(i);
			if (a.getType() == type) {
				return a;
			}
		}
		return null;
	}

	@Override
	public Action getLastActionByIndex(int index) {
		if (index > this.actions.size()) {
			return null;
		}
		return this.actions.get(this.actions.size() - index);
	}

	@Override
	public Action getLastActionByPlayerAndType(String account, int type) {
		if (this.actions.size() == 0) {
			return null;
		}
		for (int i = this.actions.size() - 1; i >= 0; i--) {
			Action a = this.actions.get(i);
			if (a.getPlayer() == null) {
				continue;
			}
			if (a.getType() == type
					&& account.equals(a.getPlayer().getAccount())) {
				return a;
			}
		}
		return null;
	}

	@Override
	public String[] getDifferentAccounts() {
		if (this.actions.size() == 0) {
			return null;
		}
		String str = "";
		for (Action action : actions) {
			if (StringUtils.isEmpty(action.getPlayer().getAccount())) {
				continue;
			}
			String account = action.getPlayer().getAccount();
			if (str.contains(account)) {
				continue;
			}
			if (StringUtils.isEmpty(str)) {
				str += account;
			} else {
				str += (",," + account);
			}
		}
		return str.split(",,");
	}
}
