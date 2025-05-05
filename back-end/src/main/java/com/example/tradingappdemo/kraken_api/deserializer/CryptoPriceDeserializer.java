package com.example.tradingappdemo.kraken_api.deserializer;

import com.example.tradingappdemo.model.CryptoPrice;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class CryptoPriceDeserializer extends StdDeserializer<CryptoPrice> {
    public CryptoPriceDeserializer() {
        this(null);
    }

    public CryptoPriceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CryptoPrice deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String name = node.get("data").get(0).get("symbol").asText();
        BigDecimal price = node.get("data").get(0).get("bid").decimalValue();

        return CryptoPrice.builder().price(price).name(name).build();
    }
}
