package com.example.tradingappdemo.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String msg) {
        super(msg);
    }

    public InvalidEmailException(String msg, Throwable err) {
        super(msg, err);
    }

}
