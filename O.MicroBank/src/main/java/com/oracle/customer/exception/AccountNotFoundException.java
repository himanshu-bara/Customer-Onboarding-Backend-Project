package com.oracle.customer.exception;

@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String customerId) {
        super("Account not found for customer ID: " + customerId);
    }
}
