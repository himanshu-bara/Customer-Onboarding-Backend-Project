package com.oracle.service;

import com.oracle.model.Admin;

public interface AdminService {
	Admin registerAdmin(Admin admin);
	Admin loginAdmin(String email, String password);
}
