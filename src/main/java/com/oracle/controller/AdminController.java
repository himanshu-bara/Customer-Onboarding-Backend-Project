package com.oracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.dto.LoginDTO;
import com.oracle.model.Admin;
import com.oracle.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin){
		Admin savedAdmin = adminService.registerAdmin(admin);
		return ResponseEntity.ok(savedAdmin);
	}
	@PostMapping("/login")
	public ResponseEntity<String> loginAdmin(@RequestBody LoginDTO loginDetails) {
        Admin admin = adminService.loginAdmin(loginDetails.getEmail(), loginDetails.getPassword());

        if (admin != null) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
