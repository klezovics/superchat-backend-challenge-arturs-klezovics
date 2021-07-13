package com.klezovich.superchat.service.tokens.btc;

import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BtcPriceTokenProcessorTest {

    @Test
    void testCanReplacePrice() {
        var priceFetcher = mock(BtcPriceProvider.class);
        var tokenProcessor = new BtcPriceTokenProcessor(priceFetcher);

        when(priceFetcher.getBtcPriceInUsd()).thenReturn(10000);

        var request = new ReplaceTokensRequest(new Contact(),"Hey, price is <!BTC_USD>! <!BTC_USD>!!");
        var output = tokenProcessor.replaceTokenInInput(request);
        assertEquals(output, "Hey, price is 10000! 10000!!");
    }
}