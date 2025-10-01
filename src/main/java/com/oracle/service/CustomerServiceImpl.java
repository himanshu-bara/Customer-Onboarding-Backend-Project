package com.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.oracle.dao.CustomerDao;
import com.oracle.model.Customer;

public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public Customer registerCustomer(Customer customer) {
		return customerDao.save(customer);
	}
	
	@Override
	public Customer loginCustomer(String email, String password) {
	    Customer customer = customerDao.findByEmail(email);
	    if (customer != null) {
	        return customer;
	    }
	    return null;
	}

}
