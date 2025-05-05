package com.example.tradingappdemo.exception;

public class UsernameInvalidCharactersException extends RuntimeException {

    public UsernameInvalidCharactersException(String msg) {
        super(msg);
    }

    public UsernameInvalidCharactersException(String msg, Throwable err) {
        super(msg, err);
    }
}
