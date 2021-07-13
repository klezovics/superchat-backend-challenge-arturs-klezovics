package com.klezovich.superchat.service.tokens.contact;

import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import com.klezovich.superchat.util.ContactMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactNameTokenProcessorTest {

    @Test
    void testNameReplacement() {

        var processor = new ContactNameTokenProcessor();
        var request = new ReplaceTokensRequest(ContactMother.valid().build(), "Hey, <!CONTACT_NAME>!!");

        var output = processor.replaceTokenInInput(request);
        assertEquals("Hey, John Dow!!", output);
    }
}