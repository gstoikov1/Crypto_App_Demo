package com.example.tradingappdemo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveCryptoPriceResponse {
    private String symbol;
    private BigDecimal livePrice;
    private String iconUrl;

}
