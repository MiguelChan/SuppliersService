package com.mgl.suppliersservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * .
 */
@ResponseStatus(
    code = HttpStatus.BAD_REQUEST,
    value = HttpStatus.BAD_REQUEST,
    reason = "InvalidInputException"
)
public class InvalidInputException extends RuntimeException {

    /**
     * .
     *
     * @param message .
     */
    public InvalidInputException(String message) {
        super(message);
    }

    /**
     * .
     *
     * @param exception .
     * @param message .
     */
    public InvalidInputException(Exception exception, String message) {
        super(message, exception);
    }

}
