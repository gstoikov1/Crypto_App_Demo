package com.example.tradingappdemo.validator;

import com.example.tradingappdemo.exception.*;
import com.example.tradingappdemo.request.TradeRequest;
import com.example.tradingappdemo.request.UserRequest;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public final class RequestValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{3,}$";

    private static final int USERNAME_MAX_LENGTH = 50;
    private static final int PASSWORD_MAX_LENGTH = 255;
    private static final int EMAIL_MAX_LENGTH = 100;

    public static void validateTradeRequest(TradeRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        BigDecimal quantity = request.getQuantity();

        validateUsername(username);
        validatePassword(password);
        validateQuantity(quantity);
    }

    public static void validateUserRequest(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        validateUsername(username);
        validatePassword(password);
    }


    public static void validateUsername(String username) {

        if (username == null || username.length() < 3) {
            throw new UsernameTooShortException("Username has to be at least 3 characters long");
        }

        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new UsernameTooLongException("Username cannot be longer than " + USERNAME_MAX_LENGTH + " characters");
        }

        if (!username.matches(USERNAME_REGEX)) {
            throw new UsernameInvalidCharactersException("Username contains invalid characters");
        }

    }

    public static void validateEmail(String email) {
        if (email == null || email.length() < 3) {
            throw new InvalidEmailException("Email has to be at least 3 characters long");
        }

        if (email.length() > EMAIL_MAX_LENGTH) {
            throw new EmailTooLongException("Email cannot be longer than " + EMAIL_MAX_LENGTH + " characters");
        }

        if (!email.matches(EMAIL_REGEX)) {
            throw new EmailNotInValidFormatException("Email is in invalid format");
        }


    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 3) {
            throw new PasswordTooShortException("Password has to be at least 3 characters long");
        }

        if (password.length() > PASSWORD_MAX_LENGTH) {
            throw new PasswordTooLongException("Password cannot be longer than " + PASSWORD_MAX_LENGTH + " characters");
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new PasswordInvalidCharactersException("Password contains invalid characters");
        }

    }

    public static void validateQuantity(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(ZERO) <= 0) {
            throw new QuantityNotAPositiveValueException("Quantity has to be a positive value");
        }
    }


    private RequestValidator() {

    }

}
