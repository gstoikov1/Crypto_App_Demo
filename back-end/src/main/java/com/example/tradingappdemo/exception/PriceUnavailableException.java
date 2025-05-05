package com.example.tradingappdemo.exception;

public class PriceUnavailableException extends RuntimeException {
    public PriceUnavailableException(String msg) {
        super(msg);
    }

    public PriceUnavailableException(String msg, Throwable err) {
        super(msg, err);
    }
}
