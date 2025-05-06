package com.example.tradingappdemo.service;

import com.example.tradingappdemo.exception.*;
import com.example.tradingappdemo.kraken_api.KrakenWebSocketClient;
import com.example.tradingappdemo.model.*;
import com.example.tradingappdemo.repository.transaction.TransactionRepository;
import com.example.tradingappdemo.repository.user.UserRepository;
import com.example.tradingappdemo.repository.wallet.WalletRepository;
import com.example.tradingappdemo.request.TradeRequest;
import com.example.tradingappdemo.request.UserRequest;
import com.example.tradingappdemo.response.ResponseResult;
import com.example.tradingappdemo.response.UserTransactionResponse;
import com.example.tradingappdemo.response.trade.TradeResponse;
import com.example.tradingappdemo.response.trade.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.tradingappdemo.response.ResponseResult.SUCCESSFUL_BUY;
import static com.example.tradingappdemo.response.ResponseResult.SUCCESSFUL_SALE;
import static com.example.tradingappdemo.response.trade.TradeType.BUY;
import static com.example.tradingappdemo.response.trade.TradeType.SELL;
import static com.example.tradingappdemo.validator.RequestValidator.validateTradeRequest;
import static com.example.tradingappdemo.validator.RequestValidator.validateUserRequest;
import static java.math.BigDecimal.ZERO;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final KrakenWebSocketClient client;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(UserRepository userRepository, KrakenWebSocketClient client, WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.client = client;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<TradeResponse> buy(TradeRequest request) {

        validateTradeRequest(request);
        User user = authenticateUser(request.getUsername(), request.getPassword());

        BigDecimal symbolPrice = getPriceForSymbol(request.getSymbol());
        BigDecimal neededFunds = symbolPrice.multiply(request.getQuantity());
        checkSufficientFunds(user.getFunds(), neededFunds);

        Wallet wallet = walletRepository.findByUserId(user.getId());
        BigDecimal updatedQuantity = addWalletHoldings(wallet, request.getSymbol(), request.getQuantity());

        BigDecimal fundsAfterTrade = user.getFunds().subtract(neededFunds);
        userRepository.updateUserFundsByUserId(user.getId(), fundsAfterTrade);

        recordTransaction(user, request, symbolPrice, TransactionType.BUY);

        TradeResponse response = buildTradeResponse(user, request.getSymbol(), symbolPrice, updatedQuantity, user.getFunds(), fundsAfterTrade, neededFunds, SUCCESSFUL_BUY, BUY);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<TradeResponse> sell(TradeRequest request) {

        validateTradeRequest(request);
        User user = authenticateUser(request.getUsername(), request.getPassword());

        BigDecimal symbolPrice = getPriceForSymbol(request.getSymbol());
        BigDecimal sellFunds = symbolPrice.multiply(request.getQuantity());
        checkSufficientSymbol(user.getId(), request.getSymbol(), request.getQuantity());

        Wallet wallet = walletRepository.findByUserId(user.getId());
        BigDecimal updatedQuantity = subtractWalletHoldings(wallet, request.getSymbol(), request.getQuantity());

        BigDecimal fundsAfterTrade = user.getFunds().add(sellFunds);
        userRepository.updateUserFundsByUserId(user.getId(), fundsAfterTrade);

        recordTransaction(user, request, symbolPrice, TransactionType.SELL);

        TradeResponse response = buildTradeResponse(user, request.getSymbol(), symbolPrice, updatedQuantity, user.getFunds(), fundsAfterTrade, sellFunds, SUCCESSFUL_SALE, SELL);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UserTransactionResponse> getTransactions(UserRequest request) {

        validateUserRequest(request);
        User user = authenticateUser(request.getUsername(), request.getPassword());

        List<Transaction> transactions = transactionRepository.findAllTransactionsByUserId(user.getId());
        return ResponseEntity.ok(UserTransactionResponse.builder().transactions(transactions).responseResult(ResponseResult.SUCCESS).build());
    }

    private BigDecimal subtractWalletHoldings(Wallet wallet, String symbol, BigDecimal quantity) {
        BigDecimal currentQuantity = walletRepository.findQuantityByWalletIdAndSymbol(wallet.getId(), symbol);
        currentQuantity = currentQuantity != null ? currentQuantity : ZERO;

        BigDecimal newQuantity = currentQuantity.subtract(quantity);
        if (newQuantity.compareTo(ZERO) < 0) {
            throw new InsufficientFundsException("not enough amount to sell");
        }
        walletRepository.updateSymbolQuantityForWallet(wallet.getId(), symbol, newQuantity);
        return newQuantity;
    }

    private BigDecimal getPriceForSymbol(String symbol) {
        CryptoPrice cryptoPrice = client.getPrices().stream()
                .filter(cp -> cp.getName().equals(symbol))
                .findFirst()
                .orElseThrow(() -> new PriceUnavailableException("Could not fetch price..."));


        return cryptoPrice.getPrice();
    }


    private User authenticateUser(String username, String password) throws InvalidUserAuthenticationException {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new InvalidUserAuthenticationException("Wrong username and password combination"));
    }

    private void checkSufficientFunds(BigDecimal currentFunds, BigDecimal neededFunds) throws InsufficientFundsException {
        if (neededFunds.compareTo(currentFunds) > 0) {
            throw new InsufficientFundsException("User does not have enough funds for trade");
        }
    }

    private void checkSufficientSymbol(int userId, String symbol, BigDecimal quantityToSell)
            throws InvalidSymbolException, InsufficientFundsException, UserNoWalletException {

        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new UserNoWalletException("User does not have a wallet");
        }

        BigDecimal currentQuantity = walletRepository.findQuantityByWalletIdAndSymbol(wallet.getId(), symbol);

        if (currentQuantity == null || quantityToSell.compareTo(currentQuantity) > 0) {
            throw new InsufficientSymbolException("Not enough crypto to sell: requested " + quantityToSell + ", available " + currentQuantity);
        }
    }

    private BigDecimal addWalletHoldings(Wallet wallet, String symbol, BigDecimal quantity) {
        BigDecimal currentQuantity = walletRepository.findQuantityByWalletIdAndSymbol(wallet.getId(), symbol);
        currentQuantity = currentQuantity != null ? currentQuantity : ZERO;

        BigDecimal newQuantity = currentQuantity.add(quantity);
        walletRepository.updateSymbolQuantityForWallet(wallet.getId(), symbol, newQuantity);
        return newQuantity;
    }

    private void recordTransaction(User user, TradeRequest request, BigDecimal symbolPrice, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
                .userID(user.getId())
                .symbol(request.getSymbol())
                .price(symbolPrice)
                .quantity(request.getQuantity())
                .tradeTime(LocalDateTime.now())
                .transactionType(transactionType)
                .build();

        transactionRepository.save(transaction);
    }

    private TradeResponse buildTradeResponse(User user, String symbol, BigDecimal price, BigDecimal newQuantity, BigDecimal before, BigDecimal after, BigDecimal fundsSpent, ResponseResult responseResult, TradeType tradeType) {
        return TradeResponse.builder()
                .username(user.getUsername())
                .symbol(symbol)
                .price(price)
                .newQuantity(newQuantity)
                .tradeTime(LocalDateTime.now())
                .fundsBeforeTrade(before)
                .fundsAfterTrade(after)
                .fundsSpent(fundsSpent)
                .responseResult(responseResult)
                .tradeType(tradeType)
                .build();
    }




}
