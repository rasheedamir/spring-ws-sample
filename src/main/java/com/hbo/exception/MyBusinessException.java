package com.hbo.exception;

public class MyBusinessException extends RuntimeException {

    public MyBusinessException(String message) {
        super(message);
    }
}