package com.oracle.customer.exception;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        super("Customer with ID " + customerId + " not found");
    }
}
