package com.oracle.customer.dao;

import java.util.List;

import com.oracle.customer.model.Customer;

public interface CustomerDao {
    Customer save(Customer customer);
    Customer findById(String customerId);
    List<Customer> findAll();
    Customer update(Customer customer);
    void delete(String customerId);
}
