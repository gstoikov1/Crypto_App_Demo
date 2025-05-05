package com.example.tradingappdemo.exception;

public class UsernameTooShortException extends RuntimeException {

    public UsernameTooShortException(String msg) {
        super(msg);
    }

    public UsernameTooShortException(String msg, Throwable err) {
        super(msg, err);
    }

}
