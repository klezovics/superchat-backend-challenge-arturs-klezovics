package com.klezovich.superchat.service.tokens.contact;

import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import com.klezovich.superchat.service.tokens.TokenProcessor;
import org.springframework.stereotype.Component;

@Component
public class ContactNameTokenProcessor implements TokenProcessor {

    @Override
    public String getTokenValue() {
        return "<!CONTACT_NAME>";
    }

    @Override
    //TODO Instead of passing on this request have the name be replaced by something else
    public String replaceTokenInInput(ReplaceTokensRequest request) {
        return request.getMessageText().replaceAll(getTokenValue(),request.getToContact().getName());
    }
}
