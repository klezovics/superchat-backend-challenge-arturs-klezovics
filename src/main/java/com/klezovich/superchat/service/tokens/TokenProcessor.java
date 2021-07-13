package com.klezovich.superchat.service.tokens;

import com.klezovich.superchat.domain.token.ReplaceTokensRequest;

public interface TokenProcessor {

    String getTokenValue();
    String replaceTokenInInput(ReplaceTokensRequest request);
}
