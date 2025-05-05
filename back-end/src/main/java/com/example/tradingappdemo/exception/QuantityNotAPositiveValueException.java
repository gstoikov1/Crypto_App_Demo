package com.example.tradingappdemo.exception;

public class QuantityNotAPositiveValueException extends RuntimeException {

    public QuantityNotAPositiveValueException(String msg) {
        super(msg);
    }

    public QuantityNotAPositiveValueException(String msg, Throwable err) {
        super(msg, err);
    }
}
