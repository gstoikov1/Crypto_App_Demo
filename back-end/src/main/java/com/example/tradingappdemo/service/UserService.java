package com.example.tradingappdemo.service;

import com.example.tradingappdemo.exception.*;
import com.example.tradingappdemo.model.User;
import com.example.tradingappdemo.model.Wallet;
import com.example.tradingappdemo.repository.transaction.TransactionRepository;
import com.example.tradingappdemo.repository.user.UserRepository;
import com.example.tradingappdemo.repository.wallet.WalletRepository;
import com.example.tradingappdemo.request.UserRequest;
import com.example.tradingappdemo.response.ResponseResult;
import com.example.tradingappdemo.response.UserWalletResponse;
import com.example.tradingappdemo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static com.example.tradingappdemo.validator.RequestValidator.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public UserService(UserRepository userRepository, WalletRepository walletRepository, TransactionService transactionService, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<UserResponse> registerUser(UserRequest request) {

        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();

        validateUsername(username);
        validateEmail(email);
        validatePassword(password);

        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        User savedUser = userRepository.save(user);

        walletRepository.save(Wallet.builder().userID(savedUser.getId()).build());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                UserResponse.builder()
                        .username(username)
                        .email(email)
                        .responseTime(LocalDateTime.now())
                        .responseResult(ResponseResult.SUCCESSFUL_REGISTRATION)
                        .build()
        );
    }


    public ResponseEntity<UserWalletResponse> getWallet(UserRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        validateUsername(username);
        validatePassword(password);

        User user = authenticateUser(username, password);

        Wallet wallet = walletRepository.findByUserId(user.getId());
        BigDecimal totalFunds = user.getFunds();
        Map<String, BigDecimal> quantities = walletRepository.getAllSymbolsByWalletId(wallet.getId());
        return ResponseEntity.ok(UserWalletResponse.builder()
                .responseResult(ResponseResult.SUCCESS)
                .quantityPerSymbol(quantities)
                .totalFunds(totalFunds)
                .build());
    }


    public ResponseEntity<UserResponse> resetUserData(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        validateUsername(username);
        validateUsername(password);

        User user = authenticateUser(username, password);

        String email = user.getEmail();

        int userId = user.getId();
        transactionRepository.deleteAllTransactionsByUserId(userId);
        Wallet userWallet = walletRepository.findByUserId(userId);
        walletRepository.deleteAllSymbolsByWalletId(userWallet.getId());
        userRepository.resetUserFundsToDefaultByUserId(userId);

        return ResponseEntity.ok(UserResponse.builder()
                .username(username)
                .email(email)
                .responseTime(LocalDateTime.now())
                .responseResult(ResponseResult.SUCCESSFUL_RESET)
                .build());
    }

    private User authenticateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new InvalidUserAuthenticationException("Wrong username and password combination"));
    }


}
