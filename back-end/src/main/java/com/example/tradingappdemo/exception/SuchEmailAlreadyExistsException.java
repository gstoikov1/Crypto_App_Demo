package com.example.tradingappdemo.exception;

public class SuchEmailAlreadyExistsException extends RuntimeException {

    public SuchEmailAlreadyExistsException(String msg) {
        super(msg);
    }

    public SuchEmailAlreadyExistsException(String msg, Throwable err) {
        super(msg, err);
    }
}
