package com.oracle.service;

import com.oracle.model.Customer;

public interface CustomerService {
	Customer registerCustomer(Customer customer);
	Customer loginCustomer(String email, String password);
}
