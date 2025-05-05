package com.example.tradingappdemo.exception;

public class EmailNotInValidFormatException extends RuntimeException {

    public EmailNotInValidFormatException(String msg) {
        super(msg);
    }

    public EmailNotInValidFormatException(String msg, Throwable err) {
        super(msg, err);
    }

}
