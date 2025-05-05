package com.example.tradingappdemo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricesResponse {
    private HashMap<String, BigDecimal> prices;
}
