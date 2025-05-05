package com.example.tradingappdemo.exception;

public class NoSQLConnectionException extends RuntimeException {

    public NoSQLConnectionException(String msg) {
        super(msg);
    }

    public NoSQLConnectionException(String msg, Throwable err) {
        super(msg, err);
    }
}
