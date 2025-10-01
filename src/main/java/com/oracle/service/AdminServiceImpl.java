package com.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.oracle.dao.AdminDao;
import com.oracle.model.Admin;

public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public Admin registerAdmin(Admin admin) {
		return adminDao.save(admin);
	}

	@Override
	public Admin loginAdmin(String email, String password) {
		Admin admin = adminDao.findByEmail(email);
		if(admin != null && admin.getPassword().equals(password)) {
			return admin;
		}
		return null;
	}
	
	
}
