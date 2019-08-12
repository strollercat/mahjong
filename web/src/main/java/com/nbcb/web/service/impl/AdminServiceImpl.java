package com.nbcb.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.nbcb.common.service.AdminService;

public class AdminServiceImpl implements AdminService {

	private List<String> listAdmins = null;

	private String superAdmin;

	public void setAdministrators(String administrators) {
		try {
			String admins[] = administrators.split(",,");
			listAdmins = new ArrayList<String>();
			for (int i = 0; i < admins.length; i++) {
				listAdmins.add(admins[i]);
			}
		} catch (Exception e) {
			return;
		}

	}

	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}

	@Override
	public boolean isAdministrator(String account) {
		// TODO Auto-generated method stub
		if (this.listAdmins == null || this.listAdmins.size() == 0) {
			return false;
		}
		for (String acc : listAdmins) {
			if (acc.equals(account)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSuperAdmin(String account) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(this.superAdmin)) {
			return false;
		}
		if (StringUtils.isEmpty(account)) {
			return false;
		}
		return this.superAdmin.equals(account);
	}

	@Override
	public String getSuperAdminAccount() {
		// TODO Auto-generated method stub
		return this.superAdmin;
	}

}
