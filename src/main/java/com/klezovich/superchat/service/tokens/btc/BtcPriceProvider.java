package com.klezovich.superchat.service.tokens.btc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class BtcPriceProvider {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BITCOIN_PRICE_API_URI = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @SneakyThrows
    public int getBtcPriceInUsd() {
        var response = restTemplate.getForObject(BITCOIN_PRICE_API_URI, String.class);

        var rootNode = mapper.readTree(response);
        var price = rootNode.path("bpi").path("USD").get("rate").asText().replace(",", "");

        return new BigDecimal(price).intValue();
    }
}
