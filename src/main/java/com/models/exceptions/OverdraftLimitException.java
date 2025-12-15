package com.models.exceptions;

public class OverdraftLimitException extends Exception {
    public OverdraftLimitException(String message) {
        super(message);
    }
}

