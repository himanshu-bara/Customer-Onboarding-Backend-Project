package com.oracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.dto.LoginDTO;
import com.oracle.model.Customer;
import com.oracle.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
		Customer saved = customerService.registerCustomer(customer);
		return ResponseEntity.ok(saved);
	}
	@PostMapping("/login")
	public ResponseEntity<String> loginCustomer(@RequestBody LoginDTO loginDetails) {
	    Customer customer = customerService.loginCustomer(loginDetails.getEmail(), loginDetails.getPassword());

	    if (customer != null) {
	        return ResponseEntity.ok("Customer login successful");
	    } else {
	        return ResponseEntity.status(401).body("Invalid email or password");
	    }
	}

}
