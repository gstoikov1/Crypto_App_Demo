package com.example.tradingappdemo.service;

import com.example.tradingappdemo.repository.symbol.SymbolRepository;
import com.example.tradingappdemo.response.SymbolsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SymbolService {
   private final SymbolRepository symbolRepository;

    @Autowired
    public SymbolService(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public ResponseEntity<SymbolsResponse> getAllSymbols() {
        SymbolsResponse symbolsResponse = SymbolsResponse.builder().symbols(symbolRepository.findAllSymbols()).build();
        return ResponseEntity.ok(symbolsResponse);
    }
}
