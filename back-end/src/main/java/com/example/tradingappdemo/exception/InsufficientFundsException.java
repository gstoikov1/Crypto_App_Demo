package com.example.tradingappdemo.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String msg) {
        super(msg);
    }

    public InsufficientFundsException(String msg, Throwable err) {
        super(msg, err);
    }
}
