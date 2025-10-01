package com.oracle.customer.exception;

@SuppressWarnings("serial")
public class KycAlreadyExistsException extends RuntimeException {
    public KycAlreadyExistsException(String message) {
    	 super(message);
    }
}
