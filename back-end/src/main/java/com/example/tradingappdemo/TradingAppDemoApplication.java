package com.example.tradingappdemo;

import com.example.tradingappdemo.kraken_api.KrakenWebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;

@SpringBootApplication
public class TradingAppDemoApplication {

    public static void main(String[] args) throws URISyntaxException {

        SpringApplication.run(TradingAppDemoApplication.class, args);

    }
}
