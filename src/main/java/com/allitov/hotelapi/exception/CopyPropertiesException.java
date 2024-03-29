package com.allitov.hotelapi.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown to indicate that a method had problems during properties copying.
 * @author allitov
 * @version 1.0
 */
@NoArgsConstructor
public class CopyPropertiesException extends RuntimeException {

    public CopyPropertiesException(String message) {
        super(message);
    }
}
