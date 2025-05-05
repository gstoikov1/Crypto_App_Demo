package com.example.tradingappdemo.kraken_api;

import com.example.tradingappdemo.kraken_api.deserializer.CryptoPriceDeserializer;
import com.example.tradingappdemo.model.CryptoPrice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.PostConstruct;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KrakenWebSocketClient extends WebSocketClient {
    private static final String KRAKEN_API_URI = "wss://ws.kraken.com/v2";
    private final ConcurrentHashMap<Integer, CryptoPrice> map = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        connect();
    }

    public KrakenWebSocketClient() throws URISyntaxException {
        super(new URI(KRAKEN_API_URI));
        objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(CryptoPrice.class, new CryptoPriceDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    public Set<CryptoPrice> getPrices() {
       return new HashSet<>(map.values());
    }

    @Override
    public void onOpen(ServerHandshake handshake) {

        System.out.println("Connected to Kraken WebSocket!");
        String subscribeMessage = """
                {
                                   "method": "subscribe",
                                   "params": {
                                     "channel": "ticker",
                                     "symbol": [
                                       "BTC/USD",
                                       "ETH/USD",
                                       "SOL/USD",
                                       "XRP/USD",
                                       "ADA/USD",
                                       "DOGE/USD",
                                       "AVAX/USD",
                                       "DOT/USD",
                                       "LTC/USD",
                                       "TRX/USD",
                                       "MATIC/USD",
                                       "LINK/USD",
                                       "BCH/USD",
                                       "ATOM/USD",
                                       "UNI/USD",
                                       "XLM/USD",
                                       "ICP/USD",
                                       "ETC/USD",
                                       "FIL/USD",
                                       "APE/USD"
                                     ]
                                   }
                                 }
                """;
        send(subscribeMessage);
    }

    @Override
    public void onMessage(String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            if (rootNode.isObject()
                    && rootNode.get("channel") != null
                    && "ticker".equals(rootNode.get("channel").asText())
                    && rootNode.has("data")) {
                CryptoPrice readValue = objectMapper.readValue(message, CryptoPrice.class);
                map.put(readValue.hashCode(), readValue);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse message: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed. Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
    }

}
