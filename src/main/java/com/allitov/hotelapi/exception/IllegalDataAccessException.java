package com.allitov.hotelapi.exception;

/**
 * Exception that can be thrown during illegal data access.
 * @author allitov
 */
public class IllegalDataAccessException extends RuntimeException {

    public IllegalDataAccessException(String message) {
        super(message);
    }
}
