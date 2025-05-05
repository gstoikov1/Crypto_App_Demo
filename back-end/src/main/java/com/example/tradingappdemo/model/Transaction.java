package com.example.tradingappdemo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private int id;
    private int userID;
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private LocalDateTime tradeTime;
    private TransactionType transactionType;
}
