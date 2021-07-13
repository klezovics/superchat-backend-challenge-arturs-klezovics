package com.klezovich.superchat.domain.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReplaceTokensResponse {

    private String messageText;
}
