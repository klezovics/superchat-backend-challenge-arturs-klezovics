package com.klezovich.superchat.service.tokens;

import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import com.klezovich.superchat.domain.token.ReplaceTokensResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenReplacementService {

    private final Map<String, TokenProcessor> tokenProcessorMap = new HashMap<>();

    public ReplaceTokensResponse replaceTokens(ReplaceTokensRequest request) {
        request = request.clone();
        var tmpText = request.getMessageText();

        for(var processor : tokenProcessorMap.values()) {
            tmpText = processor.replaceTokenInInput(request);
            request.setMessageText(tmpText);
        }

        return ReplaceTokensResponse.builder().messageText(tmpText).build();
    }

    @Autowired
    public void setTokenProcessors(List<TokenProcessor> tokenProcessors) {
        for(var processor : tokenProcessors) {
            tokenProcessorMap.put(processor.getTokenValue(),processor);
        }
    }
}
