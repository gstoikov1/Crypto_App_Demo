package com.example.tradingappdemo.exception;

public class PasswordInvalidCharactersException extends RuntimeException {

    public PasswordInvalidCharactersException(String msg) {
        super(msg);
    }

    public PasswordInvalidCharactersException(String msg, Throwable err) {
        super(msg, err);
    }
}
