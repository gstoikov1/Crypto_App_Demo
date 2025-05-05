package com.example.tradingappdemo.exception;

public class UsernameTooLongException extends RuntimeException {

    public UsernameTooLongException(String msg) {
        super(msg);
    }

    public UsernameTooLongException(String msg, Throwable err) {
        super(msg, err);
    }
}
