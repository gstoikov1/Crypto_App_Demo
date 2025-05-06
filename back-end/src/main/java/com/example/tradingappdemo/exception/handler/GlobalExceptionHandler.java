package com.example.tradingappdemo.exception.handler;

import com.example.tradingappdemo.exception.*;
import com.example.tradingappdemo.response.ResponseResult;
import com.example.tradingappdemo.response.UserResponse;
import com.example.tradingappdemo.response.trade.TradeResponse;
import com.example.tradingappdemo.exception.EmailNotInValidFormatException;
import com.example.tradingappdemo.exception.EmailTooLongException;
import com.example.tradingappdemo.exception.PasswordInvalidCharactersException;
import com.example.tradingappdemo.exception.PasswordTooLongException;
import com.example.tradingappdemo.exception.UsernameInvalidCharactersException;
import com.example.tradingappdemo.exception.UsernameTooLongException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserAuthenticationException.class)
    public ResponseEntity<TradeResponse> handleInvalidUserAuthentication(InvalidUserAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(TradeResponse.builder().responseResult(ResponseResult.BAD_AUTHENTICATION).build());
    }

    @ExceptionHandler(SuchUsernameAlreadyExistsException.class)
    public ResponseEntity<UserResponse> handleUsernameAlreadyExists(SuchUsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.USERNAME_TAKEN)
                        .responseTime(LocalDateTime.now())
                        .build());
    }


    @ExceptionHandler(UsernameTooShortException.class)
    public ResponseEntity<UserResponse> handleUsernameTooShort(UsernameTooShortException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.USERNAME_TOO_SHORT)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UsernameInvalidCharactersException.class)
    public ResponseEntity<UserResponse> handleUsernameInvalidCharacters(UsernameInvalidCharactersException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.USERNAME_INVALID_CHARACTERS)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UsernameTooLongException.class)
    public ResponseEntity<UserResponse> handleUsernameTooLong(UsernameTooLongException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.USERNAME_TOO_LONG)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(SuchEmailAlreadyExistsException.class)
    public ResponseEntity<UserResponse> handleEmailAlreadyExists(SuchEmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.EMAIL_TAKEN)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<UserResponse> handleEmailTooShort(InvalidEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.EMAIL_INVALID)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(EmailNotInValidFormatException.class)
    public ResponseEntity<UserResponse> handleInvalidEmailFormat(EmailNotInValidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.EMAIL_INVALID)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(EmailTooLongException.class)
    public ResponseEntity<UserResponse> handleEmailTooLong(EmailTooLongException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.EMAIL_TOO_LONG)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(PasswordInvalidCharactersException.class)
    public ResponseEntity<UserResponse> handlePasswordInvalidCharacters(PasswordInvalidCharactersException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.PASSWORD_INVALID_CHARACTERS)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(PasswordTooShortException.class)
    public ResponseEntity<UserResponse> handlePasswordTooShort(PasswordTooShortException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.PASSWORD_TOO_SHORT)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(PasswordTooLongException.class)
    public ResponseEntity<UserResponse> handlePasswordTooLong(PasswordTooLongException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.PASSWORD_TOO_LONG)
                        .responseTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(QuantityNotAPositiveValueException.class)
    public ResponseEntity<TradeResponse> handleNegativeQuantity(QuantityNotAPositiveValueException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TradeResponse.builder()
                        .responseResult(ResponseResult.NEGATIVE_AMOUNT)
                        .build());
    }

    @ExceptionHandler(InvalidSymbolException.class)
    public ResponseEntity<TradeResponse> handleInvalidSymbol(InvalidSymbolException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TradeResponse.builder()
                        .responseResult(ResponseResult.INVALID_SYMBOL)
                        .build());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<TradeResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TradeResponse.builder()
                        .responseResult(ResponseResult.INSUFFICIENT_FUNDS)
                        .build());
    }

    @ExceptionHandler(InsufficientSymbolException.class)
    public ResponseEntity<UserResponse> handleInsufficientSymbol(InsufficientSymbolException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(UserResponse.builder()
                        .responseResult(ResponseResult.INSUFFICIENT_SYMBOL)
                        .responseTime(LocalDateTime.now())
                        .build());
    }


    @ExceptionHandler(PriceUnavailableException.class)
    public ResponseEntity<TradeResponse> handlePriceUnavailable(PriceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TradeResponse.builder()
                        .responseResult(ResponseResult.PRICE_UNAVAILABLE)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }

}

