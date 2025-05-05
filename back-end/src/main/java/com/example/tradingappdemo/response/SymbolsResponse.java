package com.example.tradingappdemo.response;

import com.example.tradingappdemo.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymbolsResponse {
    List<Symbol> symbols;
}
