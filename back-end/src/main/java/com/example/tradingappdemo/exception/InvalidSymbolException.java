package com.example.tradingappdemo.exception;

public class InvalidSymbolException extends RuntimeException {

    public InvalidSymbolException(String msg) {
        super(msg);
    }

    public InvalidSymbolException(String msg, Throwable err) {
        super(msg, err);
    }
}
