package com.example.tradingappdemo.controller;

import com.example.tradingappdemo.repository.symbol.SymbolRepository;
import com.example.tradingappdemo.request.TradeRequest;
import com.example.tradingappdemo.request.UserRequest;
import com.example.tradingappdemo.response.*;
import com.example.tradingappdemo.response.trade.TradeResponse;
import com.example.tradingappdemo.service.PricesService;
import com.example.tradingappdemo.service.SymbolService;
import com.example.tradingappdemo.service.TransactionService;
import com.example.tradingappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PricesService pricesService;
    private final TransactionService transactionService;

    @Autowired
    public UserController(UserService userService, PricesService pricesService, TransactionService transactionService, SymbolRepository symbolRepository, SymbolService symbolService) {
        this.userService = userService;
        this.pricesService = pricesService;
        this.transactionService = transactionService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/prices")
    public ResponseEntity<PricesResponse> getPrices() {
        return pricesService.getPrices();
    }

    @PostMapping("/buy")
    public ResponseEntity<TradeResponse> buy(@RequestBody TradeRequest request) {
        return transactionService.buy(request);
    }

    @PostMapping("/sell")
    public ResponseEntity<TradeResponse> sell(@RequestBody TradeRequest request) {
        return transactionService.sell(request);
    }

    @PostMapping("/wallet")
    public ResponseEntity<UserWalletResponse> getWallet(@RequestBody UserRequest request) {
        return userService.getWallet(request);
    }

    @PostMapping("/transactions")
    public ResponseEntity<UserTransactionResponse> getTransactions(@RequestBody UserRequest request) {
        return transactionService.getTransactions(request);
    }
    @PostMapping("/user/reset")
    public ResponseEntity<UserResponse> resetUserData(@RequestBody UserRequest request) {
        return userService.resetUserData(request);
    }
}
