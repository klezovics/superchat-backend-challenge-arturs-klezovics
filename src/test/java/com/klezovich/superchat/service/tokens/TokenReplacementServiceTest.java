package com.klezovich.superchat.service.tokens;

import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import com.klezovich.superchat.service.tokens.btc.BtcPriceProvider;
import com.klezovich.superchat.service.tokens.btc.BtcPriceTokenProcessor;
import com.klezovich.superchat.service.tokens.contact.ContactNameTokenProcessor;
import com.klezovich.superchat.util.ContactMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenReplacementServiceTest {


    @Test
    void testAllTokensCorrectlyReplaced() {

        var provider = mock(BtcPriceProvider.class);
        var btcProcessor = new BtcPriceTokenProcessor(provider);

        var service = new TokenReplacementService();
        service.setTokenProcessors(List.of(btcProcessor, new ContactNameTokenProcessor()));

        when(provider.getBtcPriceInUsd()).thenReturn(10000);

        var request = ReplaceTokensRequest
                .builder()
                .toContact(ContactMother.valid().name("Joe").build())
                .messageText("Hey, <!CONTACT_NAME>! BTC price is <!BTC_USD>!")
                .build();

        var finalText = service.replaceTokens(request).getMessageText();
        assertEquals(finalText, "Hey, Joe! BTC price is 10000!");
    }
}