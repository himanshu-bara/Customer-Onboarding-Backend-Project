package com.oracle.customer.service;

import java.util.List;

import com.oracle.customer.model.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer findById(String customerId);
    List<Customer> findAll();
    Customer updateCustomer(Customer customer);
    void deleteCustomer(String customerId);
}
