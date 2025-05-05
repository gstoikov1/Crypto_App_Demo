package com.example.tradingappdemo.response.trade;

import com.example.tradingappdemo.response.ResponseResult;
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
public class TradeResponse {
    private String username;
    private String symbol;
    private BigDecimal price;
    private BigDecimal newQuantity;
    private LocalDateTime tradeTime;
    private BigDecimal fundsBeforeTrade;
    private BigDecimal fundsAfterTrade;
    private ResponseResult responseResult;
    private BigDecimal fundsSpent;
    private TradeType tradeType;
}
