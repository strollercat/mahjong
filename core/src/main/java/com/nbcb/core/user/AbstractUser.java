package com.nbcb.core.user;

public abstract class AbstractUser implements User {

	public AbstractUser(String account) {
		this.account = account;
	}

	private String account;

	public String getAccount() {
		// TODO Auto-generated method stub
		return this.account;
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof AbstractUser)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		AbstractUser other = (AbstractUser) o;
		return this.getAccount().equals(other.getAccount());
	}

}
