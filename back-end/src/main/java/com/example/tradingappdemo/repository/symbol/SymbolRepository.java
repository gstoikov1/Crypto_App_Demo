package com.example.tradingappdemo.repository.symbol;

import com.example.tradingappdemo.model.Symbol;

import java.util.List;
import java.util.Optional;

public interface SymbolRepository {
    Optional<Symbol> findBySymbol(String symbol);
    List<Symbol> findAllSymbols();
}
