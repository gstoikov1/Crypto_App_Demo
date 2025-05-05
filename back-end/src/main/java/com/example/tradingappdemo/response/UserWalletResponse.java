package com.example.tradingappdemo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletResponse {
    private ResponseResult responseResult;
    private Map<String, BigDecimal> quantityPerSymbol;
    private BigDecimal totalFunds;
}
