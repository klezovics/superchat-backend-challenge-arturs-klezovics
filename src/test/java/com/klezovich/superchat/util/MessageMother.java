package com.klezovich.superchat.util;

import com.klezovich.superchat.domain.message.Message;
import com.klezovich.superchat.domain.message.MessageStatus;

public class MessageMother {

    public static Message.MessageBuilder valid() {
        return Message.builder()
                .text("Hey, there!")
                .status(MessageStatus.UNSENT);
    }
}
