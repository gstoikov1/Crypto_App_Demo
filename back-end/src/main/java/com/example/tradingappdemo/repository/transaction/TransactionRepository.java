package com.example.tradingappdemo.repository.transaction;

import com.example.tradingappdemo.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findAllTransactionsByUserId(int userId);
    void deleteAllTransactionsByUserId(int userId);
}
