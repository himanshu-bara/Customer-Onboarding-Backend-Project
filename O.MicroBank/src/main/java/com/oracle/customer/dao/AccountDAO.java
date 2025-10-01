package com.oracle.customer.dao;

import com.oracle.customer.model.Account;

public interface AccountDAO {
    Account saveAccount(Account account);
    Account findByCustomerId(String customerId);
}
