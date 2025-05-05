package com.example.tradingappdemo.exception;

public class InvalidUserAuthenticationException extends RuntimeException {

    public InvalidUserAuthenticationException(String msg) {
        super(msg);
    }

    public InvalidUserAuthenticationException(String msg, Throwable err) {
        super(msg, err);
    }
}
