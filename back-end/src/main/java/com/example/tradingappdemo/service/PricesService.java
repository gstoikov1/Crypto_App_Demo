package com.example.tradingappdemo.service;

import com.example.tradingappdemo.kraken_api.KrakenWebSocketClient;
import com.example.tradingappdemo.model.CryptoPrice;
import com.example.tradingappdemo.response.PricesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

@Service
public class PricesService {

    private final KrakenWebSocketClient client;

    @Autowired
    public PricesService(KrakenWebSocketClient client) {
        this.client = client;
    }

    public ResponseEntity<PricesResponse> getPrices() {
        Set<CryptoPrice> prices = client.getPrices();
        HashMap<String, BigDecimal> map = new HashMap<>();
        for (CryptoPrice cp : prices) {
            map.put(cp.getName(), cp.getPrice());
        }
        return ResponseEntity.ok(PricesResponse.builder().prices(map).build());
    }
}
