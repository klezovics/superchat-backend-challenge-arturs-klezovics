package com.klezovich.superchat.service.tokens.btc;

import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import com.klezovich.superchat.service.tokens.TokenProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BtcPriceTokenProcessor implements TokenProcessor {

    private final BtcPriceProvider priceFetcher;

    @Autowired
    public BtcPriceTokenProcessor(BtcPriceProvider priceFetcher) {
        this.priceFetcher = priceFetcher;
    }

    @Override
    public String getTokenValue() {
        return "<!BTC_USD>";
    }

    @Override
    public String replaceTokenInInput(ReplaceTokensRequest request) {
        var price = String.valueOf(priceFetcher.getBtcPriceInUsd());
        return request.getMessageText().replaceAll(getTokenValue(),price);
    }
}
