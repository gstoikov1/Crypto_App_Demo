package com.example.tradingappdemo.exception;

public class PasswordTooLongException extends RuntimeException {

    public PasswordTooLongException(String msg) {
        super(msg);
    }

    public PasswordTooLongException(String msg, Throwable err) {
        super(msg, err);
    }
}
