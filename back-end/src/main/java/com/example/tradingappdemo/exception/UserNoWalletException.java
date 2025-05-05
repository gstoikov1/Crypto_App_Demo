package com.example.tradingappdemo.exception;

public class UserNoWalletException extends RuntimeException {

    public UserNoWalletException(String msg) {
        super(msg);
    }

    public UserNoWalletException(String msg, Throwable err) {
        super(msg, err);
    }
}
