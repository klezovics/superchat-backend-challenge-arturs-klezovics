package com.klezovich.superchat.domain.token;

import com.klezovich.superchat.domain.Contact;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplaceTokensRequest {

    private Contact toContact;
    private String messageText;


    public ReplaceTokensRequest clone() {
        return new ReplaceTokensRequest(toContact, messageText);
    }
}
