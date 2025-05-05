package com.example.tradingappdemo.repository.wallet;

import com.example.tradingappdemo.model.Wallet;

import java.math.BigDecimal;
import java.util.Map;

public interface WalletRepository {
    Wallet save(Wallet wallet);
    Wallet findByUserId(int id);
    BigDecimal findQuantityByWalletIdAndSymbol(int id, String symbol);
    void updateSymbolQuantityForWallet(int walletId, String symbol, BigDecimal newQuantity);
    Map<String, BigDecimal> getAllSymbolsByWalletId(int walletId);
    void deleteAllSymbolsByWalletId(int walletId);
}
