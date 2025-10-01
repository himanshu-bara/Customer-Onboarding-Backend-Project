package com.oracle.customer.exception;

@SuppressWarnings("serial")
public class IdentityMismatchException extends RuntimeException {
    public IdentityMismatchException(String message) {
        super(message);
    }
}
