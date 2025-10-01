package com.oracle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oracle.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
}
