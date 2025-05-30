package com.example.tradingappdemo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequest {
    private String username;
    private String password;
    private String symbol;
    private BigDecimal quantity;
}
