package com.example.tradingappdemo.exception;

public class PasswordTooShortException extends RuntimeException {

    public PasswordTooShortException(String msg) {
        super(msg);
    }

    public PasswordTooShortException(String msg, Throwable err) {
        super(msg, err);
    }

}
