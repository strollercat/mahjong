package com.nbcb.common.service;

public interface AdminService {
	
	public boolean isAdministrator(String account);
	
	public boolean isSuperAdmin(String account);
	
	public String getSuperAdminAccount();
}
