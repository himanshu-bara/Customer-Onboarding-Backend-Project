package com.oracle.customer.service;

import com.oracle.customer.dto.AccountDTO;

public interface AccountService {
    AccountDTO createAccount(AccountDTO dto);
    AccountDTO getAccountByCustomerId(String customerId);
}
