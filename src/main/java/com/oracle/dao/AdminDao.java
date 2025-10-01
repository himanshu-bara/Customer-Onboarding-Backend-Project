package com.oracle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oracle.model.Admin;

public interface AdminDao extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}
