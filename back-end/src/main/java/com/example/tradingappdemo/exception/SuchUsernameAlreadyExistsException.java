package com.example.tradingappdemo.exception;

public class SuchUsernameAlreadyExistsException extends RuntimeException {
    public SuchUsernameAlreadyExistsException(String msg) {
        super(msg);
    }

    public SuchUsernameAlreadyExistsException(String msg, Throwable err) {
        super(msg, err);
    }

}
