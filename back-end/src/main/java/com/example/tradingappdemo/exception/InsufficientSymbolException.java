package com.example.tradingappdemo.exception;

public class InsufficientSymbolException extends RuntimeException {

    public InsufficientSymbolException(String msg) {
        super(msg);
    }

    public InsufficientSymbolException(String msg, Throwable err) {
        super(msg, err);
    }
}
