package com.example.tradingappdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {

    private String symbol;
    private String name;
    private String iconURL;
}
