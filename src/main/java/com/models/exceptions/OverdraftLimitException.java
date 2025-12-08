package com.models.exceptions;

public class OverdraftLimitException extends RuntimeException {
    public OverdraftLimitException(String message) {
        super(message);
    }
}

