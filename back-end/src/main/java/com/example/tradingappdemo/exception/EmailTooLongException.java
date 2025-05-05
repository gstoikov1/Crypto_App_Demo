package com.example.tradingappdemo.exception;

public class EmailTooLongException extends RuntimeException {

    public EmailTooLongException(String msg) {
        super(msg);
    }

    public EmailTooLongException(String msg, Throwable err) {
        super(msg, err);
    }
}
